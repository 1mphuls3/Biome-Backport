package com.myst.biomebackport.core.registry;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.CherryTreeGrower;
import com.myst.biomebackport.common.block.*;
import com.myst.biomebackport.common.sound.ModSoundTypes;
import com.myst.biomebackport.common.sound.SoundRegistry;
import com.myst.biomebackport.core.block.ModLogBlock;
import com.myst.biomebackport.core.block.ModWoodBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import java.util.function.ToIntFunction;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BiomeBackport.MODID);
    private static ToIntFunction<BlockState> litBlockEmission(int emmision) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? emmision : 0;
    }

    public static final RegistryObject<Block> PINK_PETALS = BLOCKS.register("pink_petals",
            () -> new PinkPetalsBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).noOcclusion()));
    public static final RegistryObject<Block> CHERRY_SAPLING = BLOCKS.register("cherry_sapling",
            () -> new SaplingBlock(new CherryTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).noOcclusion()));
    public static final RegistryObject<Block> CHERRY_LEAVES = BLOCKS.register("cherry_leaves",
            () -> new CherryLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(ModSoundTypes.CHERRY_LEAVES).noOcclusion()));
    public static final RegistryObject<Block> CHERRY_LOG = BLOCKS.register("cherry_log",
            () -> new ModLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(ModSoundTypes.CHERRY_WOOD), BlockRegistry.STRIPPED_CHERRY_LOG));
    public static final RegistryObject<Block> CHERRY_WOOD = BLOCKS.register("cherry_wood",
            () -> new ModWoodBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(ModSoundTypes.CHERRY_WOOD), BlockRegistry.STRIPPED_CHERRY_WOOD));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_LOG = BLOCKS.register("stripped_cherry_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(ModSoundTypes.CHERRY_WOOD)));
    public static final RegistryObject<Block> STRIPPED_CHERRY_WOOD = BLOCKS.register("stripped_cherry_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(ModSoundTypes.CHERRY_WOOD)));
    public static final RegistryObject<Block> CHERRY_PLANKS = BLOCKS.register("cherry_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(ModSoundTypes.CHERRY_WOOD)));
    public static final RegistryObject<Block> CHERRY_STAIRS = BLOCKS.register("cherry_stairs",
            () -> new ModStairBlock(() -> CHERRY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHERRY_PLANKS.get())));
    public static final RegistryObject<Block> CHERRY_SLAB = BLOCKS.register("cherry_slab",
            () -> new ModSlabBlock(() -> CHERRY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHERRY_PLANKS.get())));
    public static final RegistryObject<Block> CHERRY_DOOR = BLOCKS.register("cherry_door",
            () -> new CustomSoundDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(ModSoundTypes.CHERRY_WOOD).noOcclusion(), SoundRegistry.CHERRY_DOOR.get()));
    public static final RegistryObject<Block> CHERRY_TRAPDOOR = BLOCKS.register("cherry_trapdoor",
            () -> new CustomSoundTrapdoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(ModSoundTypes.CHERRY_WOOD).noOcclusion(), SoundRegistry.CHERRY_TRAPDOOR.get()));
    public static final RegistryObject<Block> CHERRY_FENCE = BLOCKS.register("cherry_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(ModSoundTypes.CHERRY_WOOD)));
    public static final RegistryObject<Block> CHERRY_FENCE_GATE = BLOCKS.register("cherry_fence_gate",
            () -> new CustomSoundFencegateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(ModSoundTypes.CHERRY_WOOD), SoundRegistry.CHERRY_FENCE_GATE.get()));
    public static final RegistryObject<Block> CHERRY_PRESSURE_PLATE = BLOCKS.register("cherry_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).sound(ModSoundTypes.CHERRY_WOOD)));
    public static final RegistryObject<Block> CHERRY_BUTTON = BLOCKS.register("cherry_button",
            () -> new CustomSoundButtonBlock(true, BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(ModSoundTypes.CHERRY_WOOD), SoundRegistry.CHERRY_BUTTON.get()));
    public static final RegistryObject<Block> CHERRY_WALL_SIGN = BLOCKS.register("cherry_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).sound(ModSoundTypes.CHERRY_WOOD).noOcclusion(), ModWoodTypes.CHERRY));
    public static final RegistryObject<Block> CHERRY_SIGN = BLOCKS.register("cherry_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).sound(ModSoundTypes.CHERRY_WOOD).noOcclusion(), ModWoodTypes.CHERRY));
    public static final RegistryObject<Block> CHERRY_HANGING_SIGN = BLOCKS.register("cherry_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(CHERRY_PLANKS.get()).sound(ModSoundTypes.CHERRY_HANGING_SIGN).noOcclusion(), ModWoodTypes.CHERRY));
    public static final RegistryObject<Block> CHERRY_HANGING_SIGN_BAR = BLOCKS.register("cherry_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(CHERRY_PLANKS.get()).sound(ModSoundTypes.CHERRY_WOOD).noOcclusion(), ModWoodTypes.CHERRY));
    public static final RegistryObject<Block> BAMBOO_BLOCK = BLOCKS.register("block_of_bamboo",
            () -> new ModLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(ModSoundTypes.BAMBOO_WOOD), BlockRegistry.STRIPPED_BAMBOO_BLOCK));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_BAMBOO_BLOCK = BLOCKS.register("block_of_stripped_bamboo",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(ModSoundTypes.BAMBOO_WOOD)));
    public static final RegistryObject<Block> BAMBOO_PLANKS = BLOCKS.register("bamboo_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(ModSoundTypes.BAMBOO_WOOD)));
    public static final RegistryObject<Block> BAMBOO_MOSAIC = BLOCKS.register("bamboo_mosaic",
            () -> new Block(BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> BAMBOO_STAIRS = BLOCKS.register("bamboo_planks_stairs",
            () -> new ModStairBlock(() -> BAMBOO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> BAMBOO_MOSAIC_STAIRS = BLOCKS.register("bamboo_mosaic_stairs",
            () -> new ModStairBlock(() -> BAMBOO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> BAMBOO_SLAB = BLOCKS.register("bamboo_planks_slab",
            () -> new ModSlabBlock(() -> BAMBOO_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> BAMBOO_MOSAIC_SLAB = BLOCKS.register("bamboo_mosaic_slab",
            () -> new ModSlabBlock(() -> BAMBOO_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> BAMBOO_FENCE = BLOCKS.register("bamboo_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(ModSoundTypes.BAMBOO_WOOD)));
    public static final RegistryObject<Block> BAMBOO_FENCE_GATE = BLOCKS.register("bamboo_fence_gate",
            () -> new CustomSoundFencegateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(ModSoundTypes.BAMBOO_WOOD), SoundRegistry.BAMBOO_FENCE.get()));
    public static final RegistryObject<Block> BAMBOO_DOOR = BLOCKS.register("bamboo_door",
            () -> new CustomSoundDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(ModSoundTypes.BAMBOO_WOOD).noOcclusion(), SoundRegistry.BAMBOO_DOOR.get()));
    public static final RegistryObject<Block> BAMBOO_TRAPDOOR = BLOCKS.register("bamboo_trapdoor",
            () -> new CustomSoundTrapdoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(ModSoundTypes.BAMBOO_WOOD).noOcclusion(), SoundRegistry.BAMBOO_TRAPDOOR.get()));
    public static final RegistryObject<Block> BAMBOO_PRESSURE_PLATE = BLOCKS.register("bamboo_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).sound(ModSoundTypes.BAMBOO_WOOD)));
    public static final RegistryObject<Block> BAMBOO_BUTTON = BLOCKS.register("bamboo_button",
            () -> new CustomSoundButtonBlock(true, BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(ModSoundTypes.BAMBOO_WOOD), SoundRegistry.BAMBOO_BUTTON.get()));
    public static final RegistryObject<Block> BAMBOO_WALL_SIGN = BLOCKS.register("bamboo_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).sound(ModSoundTypes.BAMBOO_WOOD).noOcclusion(), ModWoodTypes.BAMBOO));
    public static final RegistryObject<Block> BAMBOO_SIGN = BLOCKS.register("bamboo_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).sound(ModSoundTypes.BAMBOO_WOOD).noOcclusion(), ModWoodTypes.BAMBOO));
    public static final RegistryObject<Block> BAMBOO_HANGING_SIGN = BLOCKS.register("bamboo_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get()).sound(ModSoundTypes.BAMBOO_WOOD).noOcclusion(), ModWoodTypes.BAMBOO));
    public static final RegistryObject<Block> BAMBOO_HANGING_SIGN_BAR = BLOCKS.register("bamboo_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(BAMBOO_PLANKS.get()).sound(ModSoundTypes.BAMBOO_HANGING_SIGN).noOcclusion(), ModWoodTypes.BAMBOO));
    public static final RegistryObject<Block> CHISELED_BOOKSHELF = BLOCKS.register("chiseled_bookshelf",
            () -> new ChiseledBookshelfBlock(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).sound(ModSoundTypes.CHISELED_BOOKSHELF)));
    public static final RegistryObject<Block> DECORATED_POT = BLOCKS.register("decorated_pot",
            () -> new DecoratedPotBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE)
                    .sound(ModSoundTypes.DECORATED_POT).strength(0.5F).noOcclusion()));
    public static final RegistryObject<Block> OAK_HANGING_SIGN = BLOCKS.register("oak_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.OAK));
    public static final RegistryObject<Block> OAK_HANGING_SIGN_BAR = BLOCKS.register("oak_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.OAK));
    public static final RegistryObject<Block> SPRUCE_HANGING_SIGN = BLOCKS.register("spruce_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.SPRUCE));
    public static final RegistryObject<Block> SPRUCE_HANGING_SIGN_BAR = BLOCKS.register("spruce_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.SPRUCE));
    public static final RegistryObject<Block> BIRCH_HANGING_SIGN = BLOCKS.register("birch_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.BIRCH));
    public static final RegistryObject<Block> BIRCH_HANGING_SIGN_BAR = BLOCKS.register("birch_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.BIRCH));
    public static final RegistryObject<Block> JUNGLE_HANGING_SIGN = BLOCKS.register("jungle_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.JUNGLE_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.JUNGLE));
    public static final RegistryObject<Block> JUNGLE_HANGING_SIGN_BAR = BLOCKS.register("jungle_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.JUNGLE_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.JUNGLE));
    public static final RegistryObject<Block> ACACIA_HANGING_SIGN = BLOCKS.register("acacia_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.ACACIA));
    public static final RegistryObject<Block> ACACIA_HANGING_SIGN_BAR = BLOCKS.register("acacia_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.ACACIA));
    public static final RegistryObject<Block> DARK_OAK_HANGING_SIGN = BLOCKS.register("dark_oak_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.DARK_OAK));
    public static final RegistryObject<Block> DARK_OAK_HANGING_SIGN_BAR = BLOCKS.register("dark_oak_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.DARK_OAK));
    public static final RegistryObject<Block> MANGROVE_HANGING_SIGN = BLOCKS.register("mangrove_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.MANGROVE_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.MANGROVE));
    public static final RegistryObject<Block> MANGROVE_HANGING_SIGN_BAR = BLOCKS.register("mangrove_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.MANGROVE_PLANKS).sound(ModSoundTypes.HANGING_SIGN).noOcclusion(), WoodType.MANGROVE));
    public static final RegistryObject<Block> CRIMSON_HANGING_SIGN = BLOCKS.register("crimson_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(ModSoundTypes.NETHER_HANGING_SIGN).noOcclusion(), WoodType.CRIMSON));
    public static final RegistryObject<Block> CRIMSON_HANGING_SIGN_BAR = BLOCKS.register("crimson_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(ModSoundTypes.NETHER_HANGING_SIGN).noOcclusion(), WoodType.CRIMSON));
    public static final RegistryObject<Block> WARPED_HANGING_SIGN = BLOCKS.register("warped_hanging_sign",
            () -> new HangingSignBlockCeiling(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS).sound(ModSoundTypes.NETHER_HANGING_SIGN).noOcclusion(), WoodType.WARPED));
    public static final RegistryObject<Block> WARPED_HANGING_SIGN_BAR = BLOCKS.register("warped_wall_hanging_sign",
            () -> new HangingSignBlockWall(BlockBehaviour.Properties.copy(Blocks.WARPED_PLANKS).sound(ModSoundTypes.NETHER_HANGING_SIGN).noOcclusion(), WoodType.WARPED));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}