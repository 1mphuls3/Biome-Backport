package com.myst.biomebackport.common.world.biome;

import com.myst.biomebackport.BiomeBackport;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeInitializer {
    public static final ResourceKey<Biome> CHERRY_GROVE = register("cherry_grove");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BiomeBackport.MODID, name));
    }
}
