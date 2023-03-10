package com.myst.biomebackport.common.world.feature;

import com.mojang.serialization.Codec;
import com.myst.biomebackport.BiomeBackport;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FoliagePlacerRegistry {
    private static final DeferredRegister<FoliagePlacerType<?>> PROVIDER = DeferredRegister.create(Registry.FOLIAGE_PLACER_TYPE_REGISTRY, BiomeBackport.MODID);
    public static final RegistryObject<FoliagePlacerType<CherryFoliagePlacer>> CHERRY_FOLIAGE_PLACER_TYPE = register("cherry_foliage_placer",
            CherryFoliagePlacer.CODEC);
    private static <P extends FoliagePlacer> RegistryObject<FoliagePlacerType<P>> register(String name, Codec<P> codec) {
        return PROVIDER.register(name, () -> new FoliagePlacerType<>(codec));
    }

    public static void register(IEventBus eventBus) {
        PROVIDER.register(eventBus);
    }
}
