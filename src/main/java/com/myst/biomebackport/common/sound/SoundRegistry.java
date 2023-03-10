package com.myst.biomebackport.common.sound;

import com.myst.biomebackport.BiomeBackport;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, BiomeBackport.MODID);

    public static final RegistryObject<SoundEvent> BAMBOO_WOOD_BREAK = registerSoundEvent("bamboo_wood_break");
    public static final RegistryObject<SoundEvent> BAMBOO_WOOD_PLACE = registerSoundEvent("bamboo_wood_place");
    public static final RegistryObject<SoundEvent> BAMBOO_WOOD_FALL = registerSoundEvent("bamboo_wood_fall");
    public static final RegistryObject<SoundEvent> BAMBOO_WOOD_HIT = registerSoundEvent("bamboo_wood_hit");
    public static final RegistryObject<SoundEvent> BAMBOO_WOOD_STEP = registerSoundEvent("bamboo_wood_step");
    public static final RegistryObject<SoundEvent> BAMBOO_BUTTON = registerSoundEvent("bamboo_wood_button");
    public static final RegistryObject<SoundEvent> BAMBOO_DOOR = registerSoundEvent("bamboo_wood_door");
    public static final RegistryObject<SoundEvent> BAMBOO_FENCE = registerSoundEvent("bamboo_wood_fence");
    public static final RegistryObject<SoundEvent> BAMBOO_HANGING_SIGN_BREAK = registerSoundEvent("bamboo_wood_hanging_sign_break");
    public static final RegistryObject<SoundEvent> BAMBOO_HANGING_SIGN_FALL = registerSoundEvent("bamboo_wood_hanging_sign_fall");
    public static final RegistryObject<SoundEvent> BAMBOO_HANGING_SIGN_HIT = registerSoundEvent("bamboo_wood_hanging_sign_hit");
    public static final RegistryObject<SoundEvent> BAMBOO_HANGING_SIGN_STEP = registerSoundEvent("bamboo_wood_hanging_sign_step");
    public static final RegistryObject<SoundEvent> BAMBOO_HANGING_SIGN_PLACE = registerSoundEvent("bamboo_wood_hanging_sign_place");
    public static final RegistryObject<SoundEvent> BAMBOO_TRAPDOOR = registerSoundEvent("bamboo_wood_trapdoor");
    public static final RegistryObject<SoundEvent> CHERRY_LEAVES_BREAK = registerSoundEvent("cherry_leaves_break");
    public static final RegistryObject<SoundEvent> CHERRY_LEAVES_PLACE = registerSoundEvent("cherry_leaves_place");
    public static final RegistryObject<SoundEvent> CHERRY_LEAVES_FALL = registerSoundEvent("cherry_leaves_fall");
    public static final RegistryObject<SoundEvent> CHERRY_LEAVES_HIT = registerSoundEvent("cherry_leaves_hit");
    public static final RegistryObject<SoundEvent> CHERRY_LEAVES_STEP = registerSoundEvent("cherry_leaves_step");
    public static final RegistryObject<SoundEvent> CHERRY_WOOD_BREAK = registerSoundEvent("cherry_wood_break");
    public static final RegistryObject<SoundEvent> CHERRY_WOOD_PLACE = registerSoundEvent("cherry_wood_place");
    public static final RegistryObject<SoundEvent> CHERRY_WOOD_FALL = registerSoundEvent("cherry_wood_fall");
    public static final RegistryObject<SoundEvent> CHERRY_WOOD_HIT = registerSoundEvent("cherry_wood_hit");
    public static final RegistryObject<SoundEvent> CHERRY_WOOD_STEP = registerSoundEvent("cherry_wood_step");
    public static final RegistryObject<SoundEvent> CHERRY_BUTTON = registerSoundEvent("cherrywood_button");
    public static final RegistryObject<SoundEvent> CHERRY_DOOR = registerSoundEvent("cherrywood_door");
    public static final RegistryObject<SoundEvent> CHERRY_FENCE_GATE = registerSoundEvent("cherrywood_fence_gate");
    public static final RegistryObject<SoundEvent> CHERRY_HANGING_SIGN_BREAK = registerSoundEvent("cherry_wood_hanging_sign_break");
    public static final RegistryObject<SoundEvent> CHERRY_HANGING_SIGN_FALL = registerSoundEvent("cherry_wood_hanging_sign_fall");
    public static final RegistryObject<SoundEvent> CHERRY_HANGING_SIGN_HIT = registerSoundEvent("cherry_wood_hanging_sign_hit");
    public static final RegistryObject<SoundEvent> CHERRY_HANGING_SIGN_STEP = registerSoundEvent("cherry_wood_hanging_sign_step");
    public static final RegistryObject<SoundEvent> CHERRY_HANGING_SIGN_PLACE = registerSoundEvent("cherry_wood_hanging_sign_place");
    public static final RegistryObject<SoundEvent> CHERRY_TRAPDOOR = registerSoundEvent("cherrywood_trapdoor");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_BREAK = registerSoundEvent("chiseled_bookshelf_break");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_PLACE = registerSoundEvent("chiseled_bookshelf_place");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_FALL = registerSoundEvent("chiseled_bookshelf_fall");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_HIT = registerSoundEvent("chiseled_bookshelf_hit");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_INSERT = registerSoundEvent("chiseled_bookshelf_insert");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_INSERT_ENCHANTED = registerSoundEvent("chiseled_bookshelf_insert_enchanted");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_PICKUP = registerSoundEvent("chiseled_bookshelf_pickup");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_PICKUP_ENCHANTED = registerSoundEvent("chiseled_bookshelf_pickup_enchanted");
    public static final RegistryObject<SoundEvent> CHISELED_BOOKSHELF_STEP = registerSoundEvent("chiseled_bookshelf_step");
    public static final RegistryObject<SoundEvent> CHISEL_USE = registerSoundEvent("chisel_use");
    public static final RegistryObject<SoundEvent> DECORATED_POT_SHATTER = registerSoundEvent("decorated_pot_shatter");
    public static final RegistryObject<SoundEvent> DECORATED_POT_BREAK = registerSoundEvent("decorated_pot_break");
    public static final RegistryObject<SoundEvent> DECORATED_POT_PLACE = registerSoundEvent("decorated_pot_place");
    public static final RegistryObject<SoundEvent> DECORATED_POT_FALL = registerSoundEvent("decorated_pot_fall");
    public static final RegistryObject<SoundEvent> DECORATED_POT_HIT = registerSoundEvent("decorated_pot_hit");
    public static final RegistryObject<SoundEvent> DECORATED_POT_STEP = registerSoundEvent("decorated_pot_step");
    public static final RegistryObject<SoundEvent> HANGING_SIGN_BREAK = registerSoundEvent("hanging_sign_break");
    public static final RegistryObject<SoundEvent> HANGING_SIGN_FALL = registerSoundEvent("hanging_sign_fall");
    public static final RegistryObject<SoundEvent> HANGING_SIGN_HIT = registerSoundEvent("hanging_sign_hit");
    public static final RegistryObject<SoundEvent> HANGING_SIGN_STEP = registerSoundEvent("hanging_sign_step");
    public static final RegistryObject<SoundEvent> HANGING_SIGN_PLACE = registerSoundEvent("hanging_sign_place");
    public static final RegistryObject<SoundEvent> NETHER_WOOD_HANGING_SIGN_BREAK = registerSoundEvent("nether_wood_hanging_sign_break");
    public static final RegistryObject<SoundEvent> NETHER_WOOD_HANGING_SIGN_FALL = registerSoundEvent("nether_wood_hanging_sign_fall");
    public static final RegistryObject<SoundEvent> NETHER_WOOD_HANGING_SIGN_HIT = registerSoundEvent("nether_wood_hanging_sign_hit");
    public static final RegistryObject<SoundEvent> NETHER_WOOD_HANGING_SIGN_STEP = registerSoundEvent("nether_wood_hanging_sign_step");
    public static final RegistryObject<SoundEvent> NETHER_WOOD_HANGING_SIGN_PLACE = registerSoundEvent("nether_wood_hanging_sign_place");
    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(modPath(name)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
