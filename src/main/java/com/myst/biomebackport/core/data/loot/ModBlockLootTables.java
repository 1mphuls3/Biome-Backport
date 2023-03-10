package com.myst.biomebackport.core.data.loot;

import com.myst.biomebackport.common.block.PinkPetalsBlock;
import com.myst.biomebackport.core.registry.BlockRegistry;
import com.myst.biomebackport.core.registry.ItemRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.myst.biomebackport.core.helper.DataHelper.takeAll;

public class ModBlockLootTables extends BlockLoot {
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    @Override
    protected void addTables() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockRegistry.BLOCKS.getEntries());

        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> add(b.get(), createDoorTable(b.get())));
        takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(b -> add(b.get(),
                createLeavesDrops(BlockRegistry.CHERRY_LEAVES.get(), BlockRegistry.CHERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)));
        takeAll(blocks, b -> b.get() instanceof PinkPetalsBlock).forEach(b -> add(b.get(), LootTable.lootTable().withPool(LootPool.lootPool().when(
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PINK_PETALS.get()).setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(PinkPetalsBlock.FLOWER_AMOUNT, 4)))
                    .add(LootItem.lootTableItem(ItemRegistry.PINK_PETALS.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                    .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PINK_PETALS.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PinkPetalsBlock.FLOWER_AMOUNT, 3)))
                            .add(LootItem.lootTableItem(ItemRegistry.PINK_PETALS.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PINK_PETALS.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PinkPetalsBlock.FLOWER_AMOUNT, 2)))
                        .add(LootItem.lootTableItem(ItemRegistry.PINK_PETALS.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PINK_PETALS.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PinkPetalsBlock.FLOWER_AMOUNT, 1)))
                        .add(LootItem.lootTableItem(ItemRegistry.PINK_PETALS.get())).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))));

        blocks.forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public void dropSelf(RegistryObject<Block> block) {
        this.dropOther(block.get(), block.get());
    }
}
