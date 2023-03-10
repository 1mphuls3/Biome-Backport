package com.myst.biomebackport.core.data;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.block.HangingSignBlock;
import com.myst.biomebackport.core.registry.BlockRegistry;
import com.myst.biomebackport.core.registry.TagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.myst.biomebackport.core.helper.RegistryHelper.getRegistryName;
import static com.myst.biomebackport.core.registry.ItemRegistry.ITEMS;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, BiomeBackport.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ItemTags.SLABS).add(getModItems(b -> getRegistryName(b).getPath().contains("slab")));
        tag(ItemTags.STAIRS).add(getModItems(b -> getRegistryName(b).getPath().contains("stair")));;
        tag(ItemTags.FENCES).add(getModItems(b -> getRegistryName(b).getPath().contains("fence")));
        tag(ItemTags.LEAVES).add(getModItems(b -> getRegistryName(b).getPath().contains("leaves")));
        tag(ItemTags.DOORS).add(getModItems(b -> getRegistryName(b).getPath().contains("door")));
        tag(ItemTags.TRAPDOORS).add(getModItems(b -> getRegistryName(b).getPath().contains("trapdoor")));
        tag(ItemTags.BUTTONS).add(getModItems(b ->  getRegistryName(b).getPath().contains("slab")));
        tag(ItemTags.SAPLINGS).add(getModItems(b -> getRegistryName(b).getPath().contains("sapling")));

        tag(ItemTags.LOGS).add(getModItems(b -> getRegistryName(b).getPath().contains("_log") || getRegistryName(b).getPath().contains("_wood")));
        tag(ItemTags.PLANKS).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_planks")));
        tag(ItemTags.WOODEN_BUTTONS).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_button")));
        tag(ItemTags.WOODEN_FENCES).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_fence")));
        tag(ItemTags.WOODEN_DOORS).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_door")));
        tag(ItemTags.WOODEN_STAIRS).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_stairs") && (getRegistryName(b).getPath().contains("planks"))));
        tag(ItemTags.WOODEN_SLABS).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_slab") && getRegistryName(b).getPath().contains("planks")));
        tag(ItemTags.WOODEN_TRAPDOORS).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_trapdoor")));
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(getModItems(b -> getRegistryName(b).getPath().endsWith("_pressure_plate")));

        tag(TagRegistry.Items.CHERRY_LOGS).add(getModItems(b -> getRegistryName(b).getPath().contains("_log")));
    }

    @Override
    public String getName() {
        return "BiomeBackport Item Tags";
    }

    @Nonnull
    private Item[] getModItems(Predicate<Item> predicate) {
        List<Item> ret = new ArrayList<>(Collections.emptyList());
        ITEMS.getEntries().stream().filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Item[0]);
    }
}
