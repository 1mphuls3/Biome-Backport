package com.myst.biomebackport.common.world.feature;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.core.registry.BlockRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BiomeBackport.MODID);
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> PINK_PETALS = createConfiguredFeature("pink_petals",
            () -> Feature.SIMPLE_RANDOM_SELECTOR, () -> new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                            new PinkPetalsBlockstateProvider(BlockRegistry.PINK_PETALS.get())))))));
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>>
    createConfiguredFeature(String id, Supplier<? extends F> feature, Supplier<? extends FC> config) {
        return CONFIGURED_FEATURES.<ConfiguredFeature<FC, ?>>register(id, () -> new ConfiguredFeature<>(feature.get(), config.get())).getHolder().get();
    }

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
