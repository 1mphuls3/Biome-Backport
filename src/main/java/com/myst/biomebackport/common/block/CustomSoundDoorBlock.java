package com.myst.biomebackport.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class CustomSoundDoorBlock extends DoorBlock {
    private final SoundEvent sound;
    public CustomSoundDoorBlock(Properties properties, SoundEvent sound) {
        super(properties);
        this.sound = sound;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (this.material == Material.METAL) {
            return InteractionResult.PASS;
        } else {
            state = state.cycle(OPEN);
            level.setBlock(pos, state, 10);
            level.playSound(player, pos, sound, SoundSource.BLOCKS, 1F, 1F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean pOpen) {
        if (state.is(this) && state.getValue(OPEN) != pOpen) {
            level.setBlock(pos, state.setValue(OPEN, Boolean.valueOf(pOpen)), 10);
            level.playSound(entity instanceof Player player ? player : null, pos, sound, SoundSource.BLOCKS, 1F, 1F);
        }
    }
}
