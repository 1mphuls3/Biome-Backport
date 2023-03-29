package com.myst.biomebackport.core.events;

import com.myst.biomebackport.client.ColorManager;
import com.myst.biomebackport.client.HangingSignRenderer;
import com.myst.biomebackport.client.renderer.DecoratedPotRenderer;
import com.myst.biomebackport.common.block.ModWoodTypes;
import com.myst.biomebackport.common.entity.renderer.ModBoatRenderer;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import com.myst.biomebackport.core.registry.EntityRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.myst.biomebackport.BiomeBackport.modPath;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeBackportClientEvents {
    @SubscribeEvent
    public static void onColorHandlerEvent(RegisterColorHandlersEvent.Block event) {
        ColorManager.registerBlockColors(event.getBlockColors());
    }

    @SubscribeEvent
    public static void EntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(EntityRegistry.CHERRY_BOAT.get(), (manager) -> new ModBoatRenderer(manager, "cherry_wood", false));
        EntityRenderers.register(EntityRegistry.CHERRY_BOAT_CHEST.get(), (manager) -> new ModBoatRenderer(manager, "cherry_wood", true));
        EntityRenderers.register(EntityRegistry.BAMBOO_RAFT.get(), (manager) -> new ModBoatRenderer(manager, "bamboo", false));
        EntityRenderers.register(EntityRegistry.BAMBOO_RAFT_CHEST.get(), (manager) -> new ModBoatRenderer(manager, "bamboo", true));

        event.registerBlockEntityRenderer(BlockEntityRegistry.HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.DECORATED_POT.get(), DecoratedPotRenderer::new);
    }

    @SubscribeEvent
    public static void EntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.ACACIA), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(ModWoodTypes.BAMBOO), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.BIRCH), HangingSignRenderer::createSignLayer);;
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.CRIMSON), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(ModWoodTypes.CHERRY), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.DARK_OAK), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.JUNGLE), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.MANGROVE), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.OAK), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.SPRUCE), HangingSignRenderer::createSignLayer);
        event.registerLayerDefinition(HangingSignRenderer.createSignModelName(WoodType.WARPED), HangingSignRenderer::createSignLayer);
    }
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(Sheets.SIGN_SHEET)) {
            event.addSprite(modPath("entity/signs/hanging/acacia"));
            event.addSprite(modPath("entity/signs/hanging/bamboo_wood"));
            event.addSprite(modPath("entity/signs/hanging/birch"));
            event.addSprite(modPath("entity/signs/hanging/cherry_wood"));
            event.addSprite(modPath("entity/signs/hanging/crimson"));
            event.addSprite(modPath("entity/signs/hanging/dark_oak"));
            event.addSprite(modPath("entity/signs/hanging/jungle"));
            event.addSprite(modPath("entity/signs/hanging/mangrove"));
            event.addSprite(modPath("entity/signs/hanging/oak"));
            event.addSprite(modPath("entity/signs/hanging/spruce"));
            event.addSprite(modPath("entity/signs/hanging/warped"));
        }
        String[] types = new String[]{"angler", "archer", "arms_up", "blade", "brewer", "burn", "danger",
                "explorer", "friend", "heart", "heartbreak", "howl", "miner", "mourner", "plenty", "prize",
                "sheaf", "shelter", "skull", "snort"};
        for (String type : types) {
            if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
                event.addSprite(modPath("block/" + type + "_pottery_pattern"));
            }
        }
    }
}
