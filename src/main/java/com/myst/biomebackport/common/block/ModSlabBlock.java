package com.myst.biomebackport.common.block;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ModSlabBlock extends SlabBlock {
    public final BlockState baseState;
    public ModSlabBlock(Supplier<BlockState> state, Properties properties) {
        super(properties);
        baseState = state.get();
    }
}
