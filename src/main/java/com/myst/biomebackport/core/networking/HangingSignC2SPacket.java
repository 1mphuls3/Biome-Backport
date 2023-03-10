package com.myst.biomebackport.core.networking;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.blockentity.HangingSignBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.TextFilter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HangingSignC2SPacket {
    private final BlockPos pos;
    private final String[] lines;
    public HangingSignC2SPacket(BlockPos pPos, String line0, String line1, String line2, String line3) {
        this.pos = pPos;
        this.lines = new String[]{line0, line1, line2, line3};
    }

    public HangingSignC2SPacket(FriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.lines = new String[4];

        for(int i = 0; i < 4; ++i) {
            this.lines[i] = buffer.readUtf(384);
        }
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);

        for(int i = 0; i < 4; ++i) {
            buffer.writeUtf(this.lines[i]);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            this.handleSignUpdate(this, player, supplier);
        });
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public String[] getLines() {
        return this.lines;
    }

    public void handleSignUpdate(HangingSignC2SPacket packet, ServerPlayer player, Supplier<NetworkEvent.Context> supplier) {
        List<String> list = Stream.of(packet.getLines()).map(ChatFormatting::stripFormatting).collect(Collectors.toList());
        this.filterTextPacket(list,  TextFilter::processMessageBundle, player).thenAcceptAsync((p_215245_) -> {
            this.updateSignText(packet, player, p_215245_);
        }, supplier.get().getSender().getServer());
    }

    private void updateSignText(HangingSignC2SPacket pPacket, ServerPlayer player, List<FilteredText> texts) {
        player.resetLastActionTime();
        ServerLevel serverlevel = player.getLevel();
        BlockPos blockpos = pPacket.getPos();
        if (serverlevel.hasChunkAt(blockpos)) {
            BlockState blockstate = serverlevel.getBlockState(blockpos);
            BlockEntity blockentity = serverlevel.getBlockEntity(blockpos);
            if (!(blockentity instanceof HangingSignBlockEntity sign)) {
                return;
            }

            if (!sign.isEditable() || !player.getUUID().equals(sign.getPlayerWhoMayEdit())) {
                BiomeBackport.LOGGER.warn("Player {} just tried to change non-editable sign", player.getName().getString());
                return;
            }

            for(int i = 0; i < texts.size(); ++i) {
                FilteredText filteredtext = texts.get(i);
                if (player.isTextFilteringEnabled()) {
                    sign.setMessage(i, Component.literal(filteredtext.filteredOrEmpty()));
                } else {
                    sign.setMessage(i, Component.literal(filteredtext.raw()), Component.literal(filteredtext.filteredOrEmpty()));
                }
            }

            sign.setChanged();
            serverlevel.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
        }

    }
    private <T, R> CompletableFuture<R> filterTextPacket(T t, BiFunction<TextFilter, T, CompletableFuture<R>> function, ServerPlayer player) {
        return function.apply(player.getTextFilter(), t).thenApply((r) -> r);
    }
}
