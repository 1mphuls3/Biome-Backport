package com.myst.biomebackport.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CustomSoundFencegateBlock extends FenceGateBlock {
    private final SoundEvent sound;
    public CustomSoundFencegateBlock(Properties properties, SoundEvent sound) {
        super(properties);
        this.sound = sound;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(OPEN)) {
            state = state.setValue(OPEN, Boolean.valueOf(false));
            level.setBlock(pos, state, 10);
        } else {
            Direction direction = player.getDirection();
            if (state.getValue(FACING) == direction.getOpposite()) {
                state = state.setValue(FACING, direction);
            }

            state = state.setValue(OPEN, Boolean.valueOf(true));
            level.setBlock(pos, state, 10);
        }

        boolean flag = state.getValue(OPEN);
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1F, 1F);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
