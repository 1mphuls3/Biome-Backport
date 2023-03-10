package com.myst.biomebackport.core.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
public class BlockHelper {
    public static void updateState(BlockState state, Level level, BlockPos pos) {
        level.sendBlockUpdated(pos, state, state, 2);
        level.blockEntityChanged(pos);
    }

    public static void updateStateAndNeighbor(BlockState state, Level level, BlockPos pos) {
        updateState(state, level, pos);
        state.updateNeighbourShapes(level, pos, 2);
        level.updateNeighbourForOutputSignal(pos, state.getBlock());
    }

    public static void updateStateAndNeighbor(Level level, BlockPos pos) {
        updateStateAndNeighbor(level.getBlockState(pos), level, pos);
    }
}
