package com.myst.biomebackport.common.world.feature;

import com.google.common.collect.ImmutableList;
import com.myst.biomebackport.core.registry.BlockRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.Collections;
import java.util.List;

public class CherryTreeFeature {
    private static final BeehiveDecorator BEEHIVE_005 = new BeehiveDecorator(0.05F);
    private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier placement) {
        return ImmutableList.<PlacementModifier>builder()
                .add(placement)
                .add(InSquarePlacement.spread())
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(PlacementUtils.filteredByBlockSurvival(Blocks.GRASS_BLOCK))
                .add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
                .add(PlacementUtils.HEIGHTMAP_TOP_SOLID)
                .add(BiomeFilter.biome());
    }

    private static List<PlacementModifier> treePlacement(PlacementModifier placement) {
        return treePlacementBase(placement).build();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createTree() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(BlockRegistry.CHERRY_LOG.get()),
                new CherryTrunkPlacer(3, 1, 0), BlockStateProvider.simple(BlockRegistry.CHERRY_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), 5),
                new TwoLayersFeatureSize(0, 0, 0))).decorators(Collections.singletonList(BEEHIVE_005)).ignoreVines(); //limit, lowersize, uppersize
    }

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE = FeatureUtils.register("cherry_tree", Feature.TREE, createTree().build()) ;
    static final Holder<PlacedFeature> TREES = PlacementUtils.register("cherry_tree_feature",
            CherryTreeFeature.CHERRY_TREE, treePlacement(PlacementUtils.countExtra(2, 0.1f, 1)));
    public static void addTrees(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CherryTreeFeature.TREES);
    }
}
