package com.myst.biomebackport.common.item;

import com.myst.biomebackport.common.block.DecoratedPotBlock;
import com.myst.biomebackport.common.blockentity.DecoratedPotBlockEntity;
import com.myst.biomebackport.common.sound.SoundRegistry;
import com.myst.biomebackport.core.registry.ItemRegistry;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ChiselItem extends Item {
    public ChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction side = context.getClickedFace();
        InteractionHand hand = context.getHand();

        if(level.getBlockState(pos).getBlock() instanceof DecoratedPotBlock) {
            player.displayClientMessage(Component.literal("active"), true);//TODO remove
            if (level.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
                pot.onUse(player, side);
            }
            if(side.getAxis() != Direction.Axis.Y) {
                level.playSound(player, pos, SoundRegistry.CHISEL_USE.get(), SoundSource.BLOCKS, 1.0F, 0.5F);
                player.getItemInHand(hand).hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(hand));

                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }
}
