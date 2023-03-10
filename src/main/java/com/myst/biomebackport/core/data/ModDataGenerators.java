package com.myst.biomebackport.core.data;

import com.myst.biomebackport.BiomeBackport;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiomeBackport.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModBlockTagsProvider(generator, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemTagsProvider(generator, new ModBlockTagsProvider(generator, existingFileHelper), existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBiomeTagsProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(generator));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(generator));
        generator.addProvider(event.includeServer(), new ModItemModelProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBlockStatesProvider(generator, existingFileHelper));
        generator.addProvider(event.includeClient(), new EnUsProvider(generator, BiomeBackport.MODID));
    }
}
