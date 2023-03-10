package com.myst.biomebackport.common.world.feature;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;

import java.util.function.BiConsumer;

public class CherryFoliagePlacer extends FoliagePlacer {
    public static final Codec<CherryFoliagePlacer> CODEC = RecordCodecBuilder.create((p_68518_) -> {
        return blobParts(p_68518_).apply(p_68518_, CherryFoliagePlacer::new);
    });
    protected final int height;
    protected static <P extends CherryFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, Integer> blobParts(RecordCodecBuilder.Instance<P> p_68414_) {
        return foliagePlacerParts(p_68414_).and(Codec.intRange(0, 16).fieldOf("height").forGetter((p_68412_) -> {
            return p_68412_.height;
        }));
    }

    public CherryFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return FoliagePlacerRegistry.CHERRY_FOLIAGE_PLACER_TYPE.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, TreeConfiguration config, int freeTreeHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        BlockPos pos = attachment.pos();
        Iterable<BlockPos> positions = BlockPos.betweenClosed(pos.offset(-1, 2, -1), pos.offset(1, 2, 1));
        Iterable<BlockPos> positions2 = BlockPos.betweenClosed(pos.offset(-2, 1, -2), pos.offset(2, 1, 2));
        Iterable<BlockPos> positions3 = BlockPos.betweenClosed(pos.offset(-3, -1, -3), pos.offset(3, 0, 3));

        for (BlockPos position : positions) {
            if(isCorner(position, pos.above(2), 1)) {
                if(random.nextFloat() > 0.5F) {
                    tryPlaceLeaf(level, setter, random, config, position);
                }
            } else {
                tryPlaceLeaf(level, setter, random, config, position);
            }
        }
        for (BlockPos position : positions2) {
            if(isCorner(position, pos.above(1), 2)) {
                if(random.nextFloat() > 0.5F) {
                    tryPlaceLeaf(level, setter, random, config, position);
                }
            } else {
                tryPlaceLeaf(level, setter, random, config, position);
            }
        }
        for (BlockPos position : positions3) {
            if(!(isCorner(position, pos.above(-1), 3) && isCorner(position, pos.above(0), 3))) {
                if(position.getY() == pos.getY() - 1 && (isEdge(position, pos.below(), 3))) {
                    if(random.nextFloat() > 0.5F) {
                        tryPlaceLeaf(level, setter, random, config, position);
                    }
                    if(random.nextFloat() < 0.1F) {
                        placeDroop(level, setter, random, config, position.below(), random.nextInt(1, 3));
                    }
                } else {
                    tryPlaceLeaf(level, setter, random, config, position);
                }
            }
        }
        for (BlockPos position : positions2) {
            tryPlaceLeaf(level, setter, random, config, position.below(3));
            if(random.nextFloat() < 0.1F) {
                placeDroop(level, setter, random, config, position.below(4), random.nextInt(1, 3));
            }
        }
    }

    private void placeDroop(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, TreeConfiguration config, BlockPos pos, int length) {
        if(level.isStateAtPosition(pos.above(), state -> { return state.getBlock() == Blocks.AIR; })) {
            tryPlaceLeaf(level, setter, random, config, pos.above());
        }
        for (int i = 0; i < length; i++) {
            tryPlaceLeaf(level, setter, random, config, pos.below(i));
        }
    }

    private boolean isCorner(BlockPos pos, BlockPos center, int distance) {
        boolean negativeX = pos.getX() == center.getX()-distance;
        boolean positiveX = pos.getX() == center.getX()+distance;
        boolean negativeZ = pos.getZ() == center.getZ()-distance;
        boolean positiveZ = pos.getZ() == center.getZ()+distance;

        boolean nw = negativeX && negativeZ;
        boolean ne = positiveX && negativeZ;
        boolean sw = negativeX && positiveZ;
        boolean se = positiveX && positiveZ;
        return nw || ne || sw || se;
    }

    private boolean isEdge(BlockPos pos, BlockPos center, int distance) {
        boolean negativeX = pos.getX() == center.getX()-distance;
        boolean positiveX = pos.getX() == center.getX()+distance;
        boolean negativeZ = pos.getZ() == center.getZ()-distance;
        boolean positiveZ = pos.getZ() == center.getZ()+distance;

        return negativeX || positiveX || negativeZ || positiveZ;
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return this.height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int x, int y, int z, int range, boolean isLarge) {
        return false;
    }
}
