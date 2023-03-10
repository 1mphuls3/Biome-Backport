package com.myst.biomebackport.common.world.biome;

import com.myst.biomebackport.common.world.feature.CherryTreeFeature;
import com.myst.biomebackport.common.world.feature.PlacedFeaturesRegistry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.awt.*;

public class BiomeDecorators {

    private static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor,
                               int grassColor, int foliageColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder) {
        return (new Biome.BiomeBuilder())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .fogColor(new Color(12638463).getRGB())
                        .skyColor(BiomeColors.CHERRY_GROVE_SKY)
                        .foliageColorOverride(foliageColor)
                        .grassColorOverride(grassColor)
                        .build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    public static Biome cherryGrove() {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeFeatures = new BiomeGenerationSettings.Builder();

        spawnSettings.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 2, 4));

        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 2));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 3));

        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));

        spawnSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));

        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeFeatures);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeFeatures);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeFeatures);
        BiomeDefaultFeatures.addDefaultSprings(biomeFeatures);

        BiomeDefaultFeatures.addDefaultOres(biomeFeatures);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeFeatures);

        BiomeDefaultFeatures.addDefaultFlowers(biomeFeatures);

        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeaturesRegistry.CHERRY_GROVE_GRASS);
        biomeFeatures.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeaturesRegistry.PINK_PETALS);

        CherryTreeFeature.addTrees(biomeFeatures);

        return biome(Biome.Precipitation.RAIN, 0.5F, 0.8F, BiomeColors.CHERRY_GROVE_WATER, BiomeColors.CHERRY_GROVE_WATER_FOG,
                BiomeColors.CHERRY_GROVE_GRASS, BiomeColors.CHERRY_GROVE_FOLIAGE, spawnSettings, biomeFeatures);
    }
}
