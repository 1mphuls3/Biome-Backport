package com.myst.biomebackport.core.registry;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.entity.ModBoatEntity;
import com.myst.biomebackport.common.entity.ModChestBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.myst.biomebackport.BiomeBackport.modPath;

public final class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.ENTITY_TYPES, BiomeBackport.MODID);

    public static final RegistryObject<EntityType<ModBoatEntity>> CHERRY_BOAT = ENTITY_TYPES.register("cherry_boat",
            () -> EntityType.Builder.<ModBoatEntity>of((type, level) ->
                            new ModBoatEntity(type, level, ItemRegistry.CHERRY_BOAT),
                            MobCategory.MISC).sized(1.375F, 0.5625F)
                    .build(modPath("cherry_boat").toString()));
    public static final RegistryObject<EntityType<ModChestBoatEntity>> CHERRY_BOAT_CHEST = ENTITY_TYPES.register("cherry_chest_boat",
            () -> EntityType.Builder.<ModChestBoatEntity>of((type, level) ->
                                    new ModChestBoatEntity(type, level, ItemRegistry.CHERRY_BOAT_CHEST),
                            MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(modPath("cherry_chest_boat").toString()));

    public static final RegistryObject<EntityType<ModBoatEntity>> BAMBOO_RAFT = ENTITY_TYPES.register("bamboo_raft",
            () -> EntityType.Builder.<ModBoatEntity>of((type, level) ->
                                    new ModBoatEntity(type, level, ItemRegistry.BAMBOO_RAFT),
                            MobCategory.MISC).sized(1.375F, 0.5625F)
                    .build(modPath("bamboo_raft").toString()));
    public static final RegistryObject<EntityType<ModChestBoatEntity>> BAMBOO_RAFT_CHEST = ENTITY_TYPES.register("bamboo_chest_raft",
            () -> EntityType.Builder.<ModChestBoatEntity>of((type, level) ->
                                    new ModChestBoatEntity(type, level, ItemRegistry.BAMBOO_CHEST_RAFT),
                            MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(modPath("bamboo_chest_raft").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
