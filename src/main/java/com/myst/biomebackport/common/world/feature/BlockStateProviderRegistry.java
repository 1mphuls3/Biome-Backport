package com.myst.biomebackport.common.world.feature;

import com.mojang.serialization.Codec;
import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.world.feature.PinkPetalsBlockstateProvider;
import com.myst.biomebackport.core.mixin.BlockStateProviderAccess;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateProviderRegistry {
    private static final DeferredRegister<BlockStateProviderType<?>> PROVIDER = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, BiomeBackport.MODID);
    public static final RegistryObject<BlockStateProviderType<PinkPetalsBlockstateProvider>> PINK_PETALS_PROVIDER = register("pink_petals_provider",
            PinkPetalsBlockstateProvider.CODEC);
    private static <P extends BlockStateProvider> RegistryObject<BlockStateProviderType<P>> register(String id, Codec<P> codec) {
        return PROVIDER.register(id, () -> BlockStateProviderAccess.biomeBackport_create(codec));
    }

    public static void register(IEventBus eventBus) {
        PROVIDER.register(eventBus);
    }
}
