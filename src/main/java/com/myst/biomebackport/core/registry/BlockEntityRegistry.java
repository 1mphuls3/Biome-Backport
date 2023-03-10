package com.myst.biomebackport.core.registry;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.block.HangingSignBlock;
import com.myst.biomebackport.common.block.ModWallSignBlock;
import com.myst.biomebackport.common.blockentity.ChiseledBookshelfBlockEntity;
import com.myst.biomebackport.common.blockentity.DecoratedPotBlockEntity;
import com.myst.biomebackport.common.blockentity.HangingSignBlockEntity;
import com.myst.biomebackport.common.blockentity.ModSignBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, BiomeBackport.MODID);

    public static Block[] getAllBlocks(Class<?>... blockClasses) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static final RegistryObject<BlockEntityType<ChiseledBookshelfBlockEntity>> CHISELED_BOOKSHELF = BLOCKENTITIES.register("chiseled_bookshelf",
            () -> BlockEntityType.Builder.of(ChiseledBookshelfBlockEntity::new, BlockRegistry.CHISELED_BOOKSHELF.get()).build(null));
    public static final RegistryObject<BlockEntityType<DecoratedPotBlockEntity>> DECORATED_POT = BLOCKENTITIES.register("decorated_pot",
            () -> BlockEntityType.Builder.of(DecoratedPotBlockEntity::new, BlockRegistry.DECORATED_POT.get()).build(null));
    public static final RegistryObject<BlockEntityType<HangingSignBlockEntity>> HANGING_SIGN = BLOCKENTITIES.register("hanging_sign",
            () -> BlockEntityType.Builder.of(HangingSignBlockEntity::new, getAllBlocks(HangingSignBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN = BLOCKENTITIES.register("sign",
            () -> BlockEntityType.Builder.of(ModSignBlockEntity::new,
                    BlockRegistry.CHERRY_WALL_SIGN.get(), BlockRegistry.CHERRY_SIGN.get(),
                    BlockRegistry.BAMBOO_WALL_SIGN.get(), BlockRegistry.BAMBOO_SIGN.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCKENTITIES.register(eventBus);
    }
}
