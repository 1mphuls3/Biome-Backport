package com.myst.biomebackport.common.world.biome;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils.*;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class CherryGroveRegion extends Region {
    public CherryGroveRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<com.mojang.datafixers.util.Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            List<Climate.ParameterPoint> grovePoints = new ParameterUtils.ParameterPointListBuilder()
                    .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                    .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
                    .temperature(Temperature.COOL, Temperature.NEUTRAL, Temperature.WARM)
                    .humidity(Humidity.ARID, Humidity.NEUTRAL, Humidity.WET, Humidity.HUMID)
                    .weirdness(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.HIGH_SLICE_VARIANT_DESCENDING, Weirdness.MID_SLICE_NORMAL_ASCENDING)
                    .depth(Depth.SURFACE, Depth.FLOOR)
                    .build();
            grovePoints.forEach(point -> builder.replaceBiome(point, BiomeInitializer.CHERRY_GROVE));
        });
    }
}
