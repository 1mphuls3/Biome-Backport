package com.myst.biomebackport.common.blockentity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.blockentity.ModBlockEntity;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Function;

public class HangingSignBlockEntity extends ModBlockEntity<ModBlock> {
    public static final int LINES = 4;
    private static final String[] RAW_TEXT_FIELD_NAMES = new String[]{"Text1", "Text2", "Text3", "Text4"};
    private static final String[] FILTERED_TEXT_FIELD_NAMES = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
    private final Component[] messages = new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};
    private final Component[] filteredMessages = new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};
    private boolean isEditable = true;
    @Nullable
    private UUID playerWhoMayEdit;
    @Nullable
    private FormattedCharSequence[] renderMessages;
    private boolean renderMessagedFiltered;
    private DyeColor color = DyeColor.BLACK;
    private boolean hasGlowingText;

    public HangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.HANGING_SIGN.get(), pos, state);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        for(int i = 0; i < 4; ++i) {
            Component component = this.messages[i];
            String s = Component.Serializer.toJson(component);
            tag.putString(RAW_TEXT_FIELD_NAMES[i], s);
            Component component1 = this.filteredMessages[i];
            if (!component1.equals(component)) {
                tag.putString(FILTERED_TEXT_FIELD_NAMES[i], Component.Serializer.toJson(component1));
            }
        }

        tag.putString("Color", this.color.getName());
        tag.putBoolean("GlowingText", this.hasGlowingText);
    }

    public void load(CompoundTag tag) {
        this.isEditable = false;
        super.load(tag);
        this.color = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

        for(int i = 0; i < 4; ++i) {
            String s = tag.getString(RAW_TEXT_FIELD_NAMES[i]);
            Component component = this.loadLine(s);
            this.messages[i] = component;
            String s1 = FILTERED_TEXT_FIELD_NAMES[i];
            if (tag.contains(s1, 8)) {
                this.filteredMessages[i] = this.loadLine(tag.getString(s1));
            } else {
                this.filteredMessages[i] = component;
            }
        }

        this.renderMessages = null;
        this.hasGlowingText = tag.getBoolean("GlowingText");
    }

    private Component loadLine(String pLine) {
        Component component = this.deserializeTextSafe(pLine);
        if (this.level instanceof ServerLevel) {
            try {
                return ComponentUtils.updateForEntity(this.createCommandSourceStack(null), component, null, 0);
            } catch (CommandSyntaxException commandsyntaxexception) {
            }
        }

        return component;
    }

    private Component deserializeTextSafe(String pText) {
        try {
            Component component = Component.Serializer.fromJson(pText);
            if (component != null) {
                return component;
            }
        } catch (Exception exception) {
        }

        return CommonComponents.EMPTY;
    }

    public Component getMessage(int pLine, boolean pFiltered) {
        return this.getMessages(pFiltered)[pLine];
    }

    public void setMessage(int pLine, Component pMessage) {
        this.setMessage(pLine, pMessage, pMessage);
    }

    public void setMessage(int pLine, Component pMessage, Component pFilteredMessage) {
        this.messages[pLine] = pMessage;
        this.filteredMessages[pLine] = pFilteredMessage;
        this.renderMessages = null;
    }

    public FormattedCharSequence[] getRenderMessages(boolean pFiltered, Function<Component, FormattedCharSequence> pMessageTransformer) {
        if (this.renderMessages == null || this.renderMessagedFiltered != pFiltered) {
            this.renderMessagedFiltered = pFiltered;
            this.renderMessages = new FormattedCharSequence[4];

            for(int i = 0; i < 4; ++i) {
                this.renderMessages[i] = pMessageTransformer.apply(this.getMessage(i, pFiltered));
            }
        }

        return this.renderMessages;
    }

    private Component[] getMessages(boolean pFiltered) {
        return pFiltered ? this.filteredMessages : this.messages;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
        if (!isEditable) {
            this.playerWhoMayEdit = null;
        }
    }

    public void setAllowedPlayerEditor(UUID pPlayWhoMayEdit) {
        this.playerWhoMayEdit = pPlayWhoMayEdit;
    }

    @Nullable
    public UUID getPlayerWhoMayEdit() {
        return this.playerWhoMayEdit;
    }

    public boolean executeClickCommands(ServerPlayer pLevel) {
        for(Component component : this.getMessages(pLevel.isTextFilteringEnabled())) {
            Style style = component.getStyle();
            ClickEvent clickevent = style.getClickEvent();
            if (clickevent != null && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                pLevel.getServer().getCommands().performPrefixedCommand(this.createCommandSourceStack(pLevel), clickevent.getValue());
            }
        }
        return true;
    }

    public CommandSourceStack createCommandSourceStack(@Nullable ServerPlayer player) {
        String s = player == null ? "Sign" : player.getName().getString();
        Component component = (Component)(player == null ? Component.literal("Sign") : player.getDisplayName());
        return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(this.worldPosition), Vec2.ZERO, (ServerLevel)this.level, 2, s, component, this.level.getServer(), player);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public boolean setColor(DyeColor color) {
        if (color != this.getColor()) {
            this.color = color;
            this.markUpdated();
            return true;
        } else {
            return false;
        }
    }

    public boolean hasGlowingText() {
        return this.hasGlowingText;
    }

    public boolean setHasGlowingText(boolean pHasGlowingText) {
        if (this.hasGlowingText != pHasGlowingText) {
            this.hasGlowingText = pHasGlowingText;
            this.markUpdated();
            return true;
        } else {
            return false;
        }
    }

    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}