package com.myst.biomebackport.core.data;

import com.myst.biomebackport.common.block.HangingSignBlock;
import com.myst.biomebackport.common.block.HangingSignBlockWall;
import com.myst.biomebackport.common.sound.SoundRegistry;
import com.myst.biomebackport.core.registry.BlockRegistry;
import com.myst.biomebackport.core.registry.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ItemRegistry.ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SoundRegistry.SOUNDS.getEntries());
        add("itemGroup.biomebackport", "Biome Backport");

        blocks.forEach(b -> {
            if(!(b.get() instanceof WallSignBlock || b.get() instanceof HangingSignBlockWall)) {
                String name = b.get().getDescriptionId().replaceFirst("block.biomebackport.", "");
                name = name.replace('_', ' ');
                name = capitalizeWords(name);
                add(b.get().getDescriptionId(), name);
            }
        });
        items.forEach(i -> {
            if(!(i.get() instanceof BlockItem)) {
                String name = i.get().getDescriptionId().replaceFirst("item.biomebackport.", "");
                name = capitalizeWords(name);
                add(i.get().getDescriptionId(), name);
            }
        });
        add("entity.biomebackport.cherry_boat", "Boat");
        add("entity.biomebackport.bamboo_raft", "Raft");
        add("entity.biomebackport.cherry_chest_boat", "Boat with Chest");
        add("entity.biomebackport.bamboo_chest_raft", "Raft with Chest");

        add("subtitles.biomebackport.decorated_pot_shatter", "Pot shatters");
        add("subtitles." + SoundRegistry.CHISELED_BOOKSHELF_INSERT.get().getLocation().toLanguageKey(), "Book placed");
        add("subtitles." + SoundRegistry.CHISELED_BOOKSHELF_INSERT_ENCHANTED.get().getLocation().toLanguageKey(), "Enchanted Book placed");
        add("subtitles." + SoundRegistry.CHISELED_BOOKSHELF_PICKUP.get().getLocation().toLanguageKey(), "Book taken");
        add("subtitles." + SoundRegistry.CHISELED_BOOKSHELF_PICKUP_ENCHANTED.get().getLocation().toLanguageKey(), "Enchanted Book taken");
        add("subtitles.item.chisel_use", "Chisel used");
    }

    public String capitalizeWords(String string) {
        string = string.replace('_', ' ');
        String[] array = string.split(" ");
        String ret = "";
        for(String str : array) {
            if(str != "of") {
                String cap = str.substring(0, 1).toUpperCase();
                str = cap + str.substring(1);
                ret = ret + "" + str + " ";
            }
        }
        return ret.substring(0, ret.length()-1);
    }
}