package com.myst.biomebackport.client.particle;

import com.myst.biomebackport.BiomeBackport;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = BiomeBackport.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BiomeBackport.MODID);

    public static final RegistryObject<SimpleParticleType> CHERRY_TYPE = PARTICLE_TYPES.register("cherry_leaves_particle",
            () -> new SimpleParticleType(true));

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.register(ParticleRegistry.CHERRY_TYPE.get(), ModDripParticle.CherryFallParticleProvider::new);
    }

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
