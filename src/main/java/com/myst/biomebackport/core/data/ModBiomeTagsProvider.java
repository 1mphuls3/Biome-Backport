package com.myst.biomebackport.core.data;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.core.registry.TagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, BiomeBackport.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }

    @Override
    public String getName() {
        return "Biome Backport Biome Tags";
    }
}
