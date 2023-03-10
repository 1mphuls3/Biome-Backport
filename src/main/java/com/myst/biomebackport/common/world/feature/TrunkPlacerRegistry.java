package com.myst.biomebackport.common.world.feature;

import com.mojang.serialization.Codec;
import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.core.mixin.BlockStateProviderAccess;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class TrunkPlacerRegistry {
    private static final DeferredRegister<TrunkPlacerType<?>> PROVIDER = DeferredRegister.create(Registry.TRUNK_PLACER_TYPE_REGISTRY, BiomeBackport.MODID);
    public static final RegistryObject<TrunkPlacerType<CherryTrunkPlacer>> CHERRY_TRUNK_PLACER_TYPE = register("cherry_trunk_placer",
            CherryTrunkPlacer.CODEC);
    private static <P extends TrunkPlacer> RegistryObject<TrunkPlacerType<P>> register(String name, Codec<P> codec) {
        return PROVIDER.register(name, () -> new TrunkPlacerType<>(codec));
    }

    public static void register(IEventBus eventBus) {
        PROVIDER.register(eventBus);
    }
}
