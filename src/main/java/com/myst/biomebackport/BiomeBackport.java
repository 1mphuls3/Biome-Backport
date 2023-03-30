package com.myst.biomebackport;

import com.mojang.logging.LogUtils;
import com.myst.biomebackport.client.particle.ParticleRegistry;
import com.myst.biomebackport.client.screen.DecoratedPotScreen;
import com.myst.biomebackport.common.block.ModWoodTypes;
import com.myst.biomebackport.common.sound.SoundRegistry;
import com.myst.biomebackport.common.world.feature.*;
import com.myst.biomebackport.common.world.biome.BiomeRegistry;
import com.myst.biomebackport.common.world.biome.CherryGroveRegion;
import com.myst.biomebackport.core.config.Config;
import com.myst.biomebackport.core.networking.ModMessages;
import com.myst.biomebackport.core.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import terrablender.api.Regions;

import static com.myst.biomebackport.BiomeBackport.MODID;

@Mod(MODID)
public class BiomeBackport {
    public static final String MODID = "biomebackport";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BiomeBackport() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::commonSetup);

        Config.register();

        BiomeRegistry.register(eventBus);
        BiomeRegistry.registerBiomes();

        MenuRegistry.register(eventBus);
        ParticleRegistry.register(eventBus);

        FoliagePlacerRegistry.register(eventBus);
        TrunkPlacerRegistry.register(eventBus);
        BlockStateProviderRegistry.register(eventBus);
        ConfiguredFeatures.register(eventBus);
        PlacedFeaturesRegistry.register(eventBus);

        EntityRegistry.register(eventBus);
        BlockEntityRegistry.register(eventBus);
        BlockRegistry.register(eventBus);
        ItemRegistry.register(eventBus);
        SoundRegistry.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(MenuRegistry.POT_CONTAINER.get(), DecoratedPotScreen::new);

        WoodType.register(ModWoodTypes.BAMBOO);
        WoodType.register(ModWoodTypes.CHERRY);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Regions.register(new CherryGroveRegion(new ResourceLocation(MODID, "overworld"), 4));

        event.enqueueWork(() -> {
            Sheets.addWoodType(ModWoodTypes.BAMBOO);
            Sheets.addWoodType(ModWoodTypes.CHERRY);
        });
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockRegistry.CHERRY_SAPLING.getId(), BlockRegistry.POTTED_CHERRY_SAPLING);
        });

        ModMessages.register();
    }

    public static ResourceLocation modPath(String path) {
        return new ResourceLocation(MODID, path);
    }
}
