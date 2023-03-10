package com.myst.biomebackport.common.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class CherryTrunkPlacer extends TrunkPlacer {
    public static final Codec<CherryTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70189_) -> {
        return trunkPlacerParts(p_70189_).apply(p_70189_, CherryTrunkPlacer::new);
    });
    public CherryTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TrunkPlacerRegistry.CHERRY_TRUNK_PLACER_TYPE.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, setter, random, pos.below(), config);

        Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        boolean hasMiddleBranch = random.nextFloat() > 0.6F;
        boolean hasTwoBranches = !hasMiddleBranch ? random.nextBoolean() : true;
        int middleBranchHeight = hasMiddleBranch ? 3 : 0;

        int numExtraBranches = !hasMiddleBranch ? 1 : random.nextInt();
        int numExtraBranches2 = !hasMiddleBranch ? 1 : random.nextInt();

        int height = numExtraBranches > 0 ? 1 : random.nextInt(1, 4);
        int length = height == 3 ? 3 : random.nextInt(2, 4);
        int height2 = numExtraBranches2 > 0 ? 1 : random.nextInt(1, 4);
        int length2 = height == 3 ? 3 : random.nextInt(2, 4);

        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();

        for (int i = 0; i < freeTreeHeight; i++) {
            BlockPos next = pos.above(i);
            this.placeLog(level, setter, random, next, config);
        }

        if(hasMiddleBranch) {
            for (int i = 1; i < middleBranchHeight; i++) {
                BlockPos next = pos.above(freeTreeHeight+i);
                this.placeLog(level, setter, random, next, config);
            }
            placeLog(level, setter, random, pos.above(freeTreeHeight + middleBranchHeight), config);
            list.add(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight + middleBranchHeight + 1), 0, false));
        }

        BlockPos branch = createBranch(length, height, level, setter,
                random, pos.above(freeTreeHeight), config, dir);

        if (numExtraBranches != 0) {
            branch = createBranch(random.nextInt(1, 3), 1, level, setter, random, branch, config, dir);
        }

        placeLog(level, setter, random, branch.above(), config);
        list.add(new FoliagePlacer.FoliageAttachment(branch.above(2), 0, false));

        if(hasTwoBranches) {
            BlockPos extraBranch = createBranch(length2, height2, level,
                setter, random, pos.above(freeTreeHeight-1), config, dir.getOpposite());
            if (numExtraBranches2 != 0) {
                extraBranch = createBranch(random.nextInt(1, 3), 1, level, setter, random, extraBranch, config, dir.getOpposite());
            }

            placeLog(level, setter, random, extraBranch.above(), config);
            list.add(new FoliagePlacer.FoliageAttachment(extraBranch.above(2), 0, false));
        }

        return list;
    }

    public BlockPos createBranch(int length, int height, LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random,
                              BlockPos pos, TreeConfiguration config, Direction dir) {
        BlockPos end = pos;

        for (int i = 0; i <= length; i++) {
            BlockPos nextPos = pos.offset(dir.getStepX()*i, 0, dir.getStepZ()*i);
            this.placeLog(level, setter, random, nextPos, config, (state) -> state.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(pos, nextPos)));
            end = nextPos;
        }

        for (int i = 0; i < height; i++) {
            end = end.offset(0, 1, 0);
            this.placeLog(level, setter, random, end, config);
        }

        return end;
    }

    private Direction.Axis getLogAxis(BlockPos pPos, BlockPos pOtherPos) {
        Direction.Axis direction$axis = Direction.Axis.Y;
        int i = Math.abs(pOtherPos.getX() - pPos.getX());
        int j = Math.abs(pOtherPos.getZ() - pPos.getZ());
        int k = Math.max(i, j);
        if (k > 0) {
            if (i == k) {
                direction$axis = Direction.Axis.X;
            } else {
                direction$axis = Direction.Axis.Z;
            }
        }

        return direction$axis;
    }
}
