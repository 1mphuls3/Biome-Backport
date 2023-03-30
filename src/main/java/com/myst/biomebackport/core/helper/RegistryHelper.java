package com.myst.biomebackport.core.helper;

import com.google.common.collect.ArrayListMultimap;
import com.myst.biomebackport.BiomeBackport;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegistryHelper {
    public static ResourceLocation getRegistryName(Item i) {
        return ForgeRegistries.ITEMS.getKey(i);
    }

    public static ResourceLocation getRegistryName(Block b) {
        return ForgeRegistries.BLOCKS.getKey(b);
    }

    public static ResourceLocation getRegistryName(EntityType<?> i) {
        return ForgeRegistries.ENTITY_TYPES.getKey(i);
    }

    public static ResourceLocation getRegistryName(Enchantment e) {
        return ForgeRegistries.ENCHANTMENTS.getKey(e);
    }

    public static ResourceLocation getRegistryName(ParticleType<?> type) {
        return ForgeRegistries.PARTICLE_TYPES.getKey(type);
    }

    private static final Map<String, ModData> modData = new HashMap<>();

    private static final Map<Object, ResourceLocation> internalNames = new HashMap<>();

    private static ModData getCurrentModData() {
        return getModData(ModLoadingContext.get().getActiveNamespace());
    }

    private static ModData getModData(String modid) {
        ModData data = modData.get(modid);
        if(data == null) {
            data = new ModData();
            modData.put(modid, data);

            FMLJavaModLoadingContext.get().getModEventBus().register(RegistryHelper.class);
        }

        return data;
    }

    public static <T> ResourceLocation getRegistryName(T obj, Registry<T> registry) {
        if(internalNames.containsKey(obj))
            return getInternalName(obj);

        return registry.getKey(obj);
    }

    public static void setInternalName(Object obj, ResourceLocation name) {
        internalNames.put(obj, name);
    }

    public static ResourceLocation getInternalName(Object obj) {
        return internalNames.get(obj);
    }

    @SubscribeEvent
    public static void onRegistryEvent(RegisterEvent event) {
        getCurrentModData().register(event.getRegistryKey(), event.getForgeRegistry());
    }
    public static <T> void register(T obj, String resloc, ResourceKey<Registry<T>> registry) {
        if(obj == null)
            throw new IllegalArgumentException("Can't register null object.");

        setInternalName(obj, GameData.checkPrefix(resloc, false));
        getCurrentModData().defers.put(registry.location(), () -> obj);
    }

    public static <T> void register(T obj, ResourceKey<Registry<T>> registry) {
        if(obj == null)
            throw new IllegalArgumentException("Can't register null object.");
        if(getInternalName(obj) == null)
            throw new IllegalArgumentException("Can't register object without registry name.");

        getCurrentModData().defers.put(registry.location(), () -> obj);
    }

    public static void registerItem(Item item, String resloc) {
        register(item, resloc, Registry.ITEM_REGISTRY);
    }
    private static class ModData {

        private Map<ResourceLocation, CreativeModeTab> groups = new LinkedHashMap<>();

        private ArrayListMultimap<ResourceLocation, Supplier<Object>> defers = ArrayListMultimap.create();

        @SuppressWarnings({ "rawtypes", "unchecked" })
        private <T>  void register(ResourceKey<? extends Registry<?>> key, IForgeRegistry<T> registry) {
            ResourceLocation registryRes = key.location();

            if(defers.containsKey(registryRes)) {
                if(registry == null) {
                    BiomeBackport.LOGGER.error(registryRes + " does not have a forge registry");
                    return;
                }

                Collection<Supplier<Object>> ourEntries = defers.get(registryRes);
                for(Supplier<Object> supplier : ourEntries) {
                    T entry = (T) supplier.get();
                    ResourceLocation name = getInternalName(entry);
                    registry.register(name, entry);
                    BiomeBackport.LOGGER.debug("Registering to " + registryRes + " - " + name);
                }

                defers.removeAll(registryRes);
            }
        }
    }
}
