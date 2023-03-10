package com.myst.biomebackport.client;

import com.myst.biomebackport.core.registry.BlockRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;

public class ColorManager {
    public static synchronized void registerBlockColors(BlockColors blockColors) {
        blockColors.register((unknown, lightReader, pos, unknown2) -> lightReader != null && pos != null ? BiomeColors.getAverageGrassColor(lightReader, pos)
                : GrassColor.get(0.5D, 1.0D), BlockRegistry.PINK_PETALS.get());
    }
}
