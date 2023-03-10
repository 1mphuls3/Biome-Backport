package com.myst.biomebackport.core.registry;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.client.DecoratedPotContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES.getRegistryName(), BiomeBackport.MODID);
    public static final RegistryObject<MenuType<DecoratedPotContainerMenu>> POT_CONTAINER = CONTAINERS.register("pot_container", () -> IForgeMenuType.create(
            DecoratedPotContainerMenu::new));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}