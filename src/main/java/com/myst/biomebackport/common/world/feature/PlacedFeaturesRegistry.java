package com.myst.biomebackport.common.world.feature;

import com.myst.biomebackport.BiomeBackport;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class PlacedFeaturesRegistry {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BiomeBackport.MODID);

    public static final Holder<PlacedFeature> CHERRY_GROVE_GRASS = createPlacedFeature("patch_grass_forest",
            VegetationFeatures.PATCH_GRASS, () -> worldSurfaceSquaredWithCount(5));

    public static final Holder<PlacedFeature> PINK_PETALS = createPlacedFeature("pink_petals",
            ConfiguredFeatures.PINK_PETALS, () -> worldSurfaceSquaredWithCount(6));
    @SafeVarargs
    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, Supplier<PlacementModifier>... placementModifiers) {
        return createPlacedFeature(id, feature, () -> Arrays.stream(placementModifiers).map(Supplier::get).toList());
    }
    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, Supplier<List<PlacementModifier>> placementModifiers) {
        return PLACED_FEATURES.register(id, () -> new PlacedFeature(Holder.hackyErase(feature), placementModifiers.get())).getHolder().get();
    }
    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeatureDirect(Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        return createPlacedFeatureDirect(feature, List.of(placementModifiers));
    }
    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeatureDirect(Holder<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
        return Holder.direct(new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placementModifiers)));
    }

    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int pCount) {
        return List.of(CountPlacement.of(pCount), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
