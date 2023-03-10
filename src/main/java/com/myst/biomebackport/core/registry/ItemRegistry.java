package com.myst.biomebackport.core.registry;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.item.HangingSignItem;
import com.myst.biomebackport.common.item.ModBoatItem;
import com.myst.biomebackport.common.item.ModChestBoatItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"UnusedDeclaration"})
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            BiomeBackport.MODID);

    public static final RegistryObject<BlockItem> PINK_PETALS = ITEMS.register("pink_petals",
            () -> new BlockItem(BlockRegistry.PINK_PETALS.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CHERRY_SAPLING = ITEMS.register("cherry_sapling",
            () -> new BlockItem(BlockRegistry.CHERRY_SAPLING.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CHERRY_LEAVES = ITEMS.register("cherry_leaves",
            () -> new BlockItem(BlockRegistry.CHERRY_LEAVES.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CHERRY_LOG = ITEMS.register("cherry_log",
            () -> new BlockItem(BlockRegistry.CHERRY_LOG.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_WOOD = ITEMS.register("cherry_wood",
            () -> new BlockItem(BlockRegistry.CHERRY_WOOD.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> STRIPPED_CHERRY_LOG = ITEMS.register("stripped_cherry_log",
            () -> new BlockItem(BlockRegistry.STRIPPED_CHERRY_LOG.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> STRIPPED_CHERRY_WOOD = ITEMS.register("stripped_cherry_wood",
            () -> new BlockItem(BlockRegistry.STRIPPED_CHERRY_WOOD.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> CHERRY_PLANKS = ITEMS.register("cherry_planks",
            () -> new BlockItem(BlockRegistry.CHERRY_PLANKS.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_PLANKS_STAIRS = ITEMS.register("cherry_stairs",
            () -> new BlockItem(BlockRegistry.CHERRY_STAIRS.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_PLANKS_SLAB = ITEMS.register("cherry_slab",
            () -> new BlockItem(BlockRegistry.CHERRY_SLAB.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_FENCE = ITEMS.register("cherry_fence",
            () -> new BlockItem(BlockRegistry.CHERRY_FENCE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_FENCE_GATE = ITEMS.register("cherry_fence_gate",
            () -> new BlockItem(BlockRegistry.CHERRY_FENCE_GATE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_DOOR = ITEMS.register("cherry_door",
            () -> new BlockItem(BlockRegistry.CHERRY_DOOR.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_TRAPDOOR = ITEMS.register("cherry_trapdoor",
            () -> new BlockItem(BlockRegistry.CHERRY_TRAPDOOR.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_PRESSURE_PLATE = ITEMS.register("cherry_pressure_plate",
            () -> new BlockItem(BlockRegistry.CHERRY_PRESSURE_PLATE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_BUTTON = ITEMS.register("cherry_button",
            () -> new BlockItem(BlockRegistry.CHERRY_BUTTON.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CHERRY_SIGN = ITEMS.register("cherry_sign",
            () -> new SignItem(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS), BlockRegistry.CHERRY_SIGN.get(), BlockRegistry.CHERRY_WALL_SIGN.get()));
    public static final RegistryObject<BlockItem> CHERRY_HANGING_SIGN = ITEMS.register("cherry_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.CHERRY_HANGING_SIGN.get(), BlockRegistry.CHERRY_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> CHERRY_BOAT = ITEMS.register("cherry_boat",
            () -> new ModBoatItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1), EntityRegistry.CHERRY_BOAT));
    public static final RegistryObject<Item> CHERRY_BOAT_CHEST = ITEMS.register("cherry_chest_boat",
            () -> new ModChestBoatItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1), EntityRegistry.CHERRY_BOAT_CHEST));
    public static final RegistryObject<BlockItem> BAMBOO_BLOCK = ITEMS.register("block_of_bamboo",
            () -> new BlockItem(BlockRegistry.BAMBOO_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> STRIPPED_BAMBOO_BLOCK = ITEMS.register("block_of_stripped_bamboo",
            () -> new BlockItem(BlockRegistry.STRIPPED_BAMBOO_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BAMBOO_PLANKS = ITEMS.register("bamboo_planks",
            () -> new BlockItem(BlockRegistry.BAMBOO_PLANKS.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_MOSAIC = ITEMS.register("bamboo_mosaic",
            () -> new BlockItem(BlockRegistry.BAMBOO_MOSAIC.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_STAIRS = ITEMS.register("bamboo_planks_stairs",
            () -> new BlockItem(BlockRegistry.BAMBOO_STAIRS.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_MOSAIC_STAIRS = ITEMS.register("bamboo_mosaic_stairs",
            () -> new BlockItem(BlockRegistry.BAMBOO_MOSAIC_STAIRS.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_SLAB = ITEMS.register("bamboo_planks_slab",
            () -> new BlockItem(BlockRegistry.BAMBOO_SLAB.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_MOSAIC_SLAB = ITEMS.register("bamboo_mosaic_slab",
            () -> new BlockItem(BlockRegistry.BAMBOO_MOSAIC_SLAB.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_FENCE = ITEMS.register("bamboo_fence",
            () -> new BlockItem(BlockRegistry.BAMBOO_FENCE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_FENCE_GATE = ITEMS.register("bamboo_fence_gate",
            () -> new BlockItem(BlockRegistry.BAMBOO_FENCE_GATE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_DOOR = ITEMS.register("bamboo_door",
            () -> new BlockItem(BlockRegistry.BAMBOO_DOOR.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_TRAPDOOR = ITEMS.register("bamboo_trapdoor",
            () -> new BlockItem(BlockRegistry.BAMBOO_TRAPDOOR.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_PRESSURE_PLATE = ITEMS.register("bamboo_pressure_plate",
            () -> new BlockItem(BlockRegistry.BAMBOO_PRESSURE_PLATE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_BUTTON = ITEMS.register("bamboo_button",
            () -> new BlockItem(BlockRegistry.BAMBOO_BUTTON.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> BAMBOO_SIGN = ITEMS.register("bamboo_sign",
            () -> new SignItem(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS), BlockRegistry.BAMBOO_SIGN.get(), BlockRegistry.BAMBOO_WALL_SIGN.get()));
    public static final RegistryObject<BlockItem> BAMBOO_HANGING_SIGN = ITEMS.register("bamboo_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.BAMBOO_HANGING_SIGN.get(), BlockRegistry.BAMBOO_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> BAMBOO_RAFT = ITEMS.register("bamboo_raft",
            () -> new ModBoatItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1), EntityRegistry.BAMBOO_RAFT));
    public static final RegistryObject<Item> BAMBOO_CHEST_RAFT = ITEMS.register("bamboo_chest_raft",
            () -> new ModChestBoatItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1), EntityRegistry.BAMBOO_RAFT_CHEST));
    public static final RegistryObject<BlockItem> CHISELED_BOOKSHELF = ITEMS.register("chiseled_bookshelf",
            () -> new BlockItem(BlockRegistry.CHISELED_BOOKSHELF.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DECORATED_POT = ITEMS.register("decorated_pot",
            () -> new BlockItem(BlockRegistry.DECORATED_POT.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(250)));
    public static final RegistryObject<BlockItem> OAK_HANGING_SIGN = ITEMS.register("oak_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.OAK_HANGING_SIGN.get(), BlockRegistry.OAK_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> SPRUCE_HANGING_SIGN = ITEMS.register("spruce_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.SPRUCE_HANGING_SIGN.get(), BlockRegistry.SPRUCE_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> BIRCH_HANGING_SIGN = ITEMS.register("birch_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.BIRCH_HANGING_SIGN.get(), BlockRegistry.BIRCH_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> JUNGLE_HANGING_SIGN = ITEMS.register("jungle_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.JUNGLE_HANGING_SIGN.get(), BlockRegistry.JUNGLE_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> ACACIA_HANGING_SIGN = ITEMS.register("acacia_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.ACACIA_HANGING_SIGN.get(), BlockRegistry.ACACIA_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DARK_OAK_HANGING_SIGN = ITEMS.register("dark_oak_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.DARK_OAK_HANGING_SIGN.get(), BlockRegistry.DARK_OAK_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MANGROVE_HANGING_SIGN = ITEMS.register("mangrove_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.MANGROVE_HANGING_SIGN.get(), BlockRegistry.MANGROVE_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CRIMSON_HANGING_SIGN = ITEMS.register("crimson_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.CRIMSON_HANGING_SIGN.get(), BlockRegistry.CRIMSON_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> WARPED_HANGING_SIGN = ITEMS.register("warped_hanging_sign",
            () -> new HangingSignItem(BlockRegistry.WARPED_HANGING_SIGN.get(), BlockRegistry.WARPED_HANGING_SIGN_BAR.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
