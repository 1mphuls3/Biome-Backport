package com.myst.biomebackport.core.registry;

import com.myst.biomebackport.BiomeBackport;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class TagRegistry {
    public static class Blocks {
        public static TagKey<Block> CHERRY_LOGS = modTag("cherry_logs");

        public static final TagKey<Block> modTag(String name) {
            return BlockTags.create(new ResourceLocation(BiomeBackport.MODID, name));
        }

        public static final TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static TagKey<Item> CHERRY_LOGS = modTag("cherry_logs");

        private static TagKey<Item> modTag(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, modPath(path));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Biomes {
        private static TagKey<Biome> create(String path) {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(path));
        }
    }
}
