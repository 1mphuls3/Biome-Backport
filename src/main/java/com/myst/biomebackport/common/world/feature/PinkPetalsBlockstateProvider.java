package com.myst.biomebackport.common.world.feature;

import com.mojang.serialization.Codec;
import com.myst.biomebackport.common.block.PinkPetalsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class PinkPetalsBlockstateProvider extends BlockStateProvider {
    public static final Codec<PinkPetalsBlockstateProvider> CODEC = BlockState.CODEC.fieldOf("state")
            .xmap(BlockBehaviour.BlockStateBase::getBlock, Block::defaultBlockState).xmap(PinkPetalsBlockstateProvider::new, (provider) -> {
        return provider.block;
    }).codec();
    private final Block block;

    public PinkPetalsBlockstateProvider(Block block) {
        this.block = block;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return BlockStateProviderRegistry.PINK_PETALS_PROVIDER.get();
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos state) {
        Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        return this.block.defaultBlockState().setValue(PinkPetalsBlock.FACING, dir).setValue(PinkPetalsBlock.FLOWER_AMOUNT, random.nextInt(1, 4));
    }
}
