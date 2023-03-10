package com.myst.biomebackport.core.data;

import com.myst.biomebackport.BiomeBackport;
import com.myst.biomebackport.common.block.HangingSignBlock;
import com.myst.biomebackport.common.block.PinkPetalsBlock;
import com.myst.biomebackport.core.registry.BlockRegistry;
import com.myst.biomebackport.core.registry.TagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.myst.biomebackport.core.helper.RegistryHelper.getRegistryName;
import static com.myst.biomebackport.core.registry.BlockRegistry.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, BiomeBackport.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Blocks.ORES).add(getModBlocks(b -> b.getName().contains(Component.nullToEmpty("ore"))));
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        tag(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(BlockTags.LEAVES).add(getModBlocks(b -> b instanceof LeavesBlock));
        tag(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        tag(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        tag(BUTTONS).add(getModBlocks(b -> b instanceof ButtonBlock));
        tag(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof PressurePlateBlock));
        tag(DIRT).add(getModBlocks(b -> b instanceof GrassBlock || b instanceof FarmBlock));
        tag(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));

        tag(LOGS).add(getModBlocks(b -> getRegistryName(b).getPath().contains("_log") || getRegistryName(b).getPath().contains("_wood")));
        tag(LOGS_THAT_BURN).add(getModBlocks(b -> getRegistryName(b).getPath().contains("_log") || getRegistryName(b).getPath().contains("_wood")));
        tag(OVERWORLD_NATURAL_LOGS).add(getModBlocks(b -> getRegistryName(b).getPath().contains("_log") || getRegistryName(b).getPath().contains("_wood")));
        tag(PLANKS).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_planks")));
        tag(WOODEN_BUTTONS).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_button")));
        tag(WOODEN_FENCES).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_fence")));
        tag(WOODEN_DOORS).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_door")));
        tag(WOODEN_STAIRS).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_stairs") && (getRegistryName(b).getPath().contains("planks"))));
        tag(WOODEN_SLABS).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_slab") && getRegistryName(b).getPath().contains("planks")));
        tag(WOODEN_TRAPDOORS).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_trapdoor")));
        tag(WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> getRegistryName(b).getPath().endsWith("_pressure_plate")));

        tag(TagRegistry.Blocks.modTag("wall_hanging_signs")).add(getModBlocks(b -> b instanceof HangingSignBlock));
        tag(TagRegistry.Blocks.modTag("bamboo_blocks")).add(getModBlocks(b -> getRegistryName(b).getPath().contains("block_of")));
        tag(TagRegistry.Blocks.modTag("cherry_logs")).add(getModBlocks(b -> getRegistryName(b).getPath().contains("_log")
                || getRegistryName(b).getPath().contains("_wood")));

        for (RegistryObject<Block> block : BLOCKS.getEntries()) {
            if (!(block.get() == BlockRegistry.PINK_PETALS.get() || block.get() == BlockRegistry.CHERRY_LEAVES.get()
                    || block.get() == BlockRegistry.CHERRY_SAPLING.get() || block.get() == BlockRegistry.DECORATED_POT.get())) {
                tag(MINEABLE_WITH_AXE).add(block.get());
            } else if(!(block.get() == BlockRegistry.CHERRY_SAPLING.get() || block.get() == BlockRegistry.DECORATED_POT.get())) {
                tag(MINEABLE_WITH_HOE).add(block.get());
            } else {
                tag(MINEABLE_WITH_PICKAXE).add(block.get());
            }
        }
    }

    @Override
    public String getName() {
        return "BiomeBackport Block Tags";
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream().filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}
