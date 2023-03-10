package com.myst.biomebackport.common.block;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ModStairBlock extends StairBlock {
    public final BlockState baseState;
    public ModStairBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
        baseState = state.get();
    }
}
