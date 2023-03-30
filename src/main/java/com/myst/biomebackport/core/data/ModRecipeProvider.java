package com.myst.biomebackport.core.data;

import com.myst.biomebackport.core.registry.BlockRegistry;
import com.myst.biomebackport.core.registry.ItemRegistry;
import com.myst.biomebackport.core.registry.TagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.function.Consumer;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        shapeless(ItemRegistry.CHERRY_PLANKS.get(), 4).requires(TagRegistry.Items.CHERRY_LOGS)
                .unlockedBy("has_input", has(BlockRegistry.CHERRY_LOG.get()))
                .save(consumer);
        shaped2x2(consumer, ItemRegistry.CHERRY_WOOD.get(), ItemRegistry.CHERRY_LOG.get(), 3);
        shaped2x2(consumer, ItemRegistry.STRIPPED_CHERRY_WOOD.get(), ItemRegistry.STRIPPED_CHERRY_LOG.get(), 3);
        shapedStairs(consumer, ItemRegistry.CHERRY_PLANKS_STAIRS.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedSlab(consumer, ItemRegistry.CHERRY_PLANKS_SLAB.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedFence(consumer, ItemRegistry.CHERRY_FENCE.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedFenceGate(consumer, ItemRegistry.CHERRY_FENCE_GATE.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedDoor(consumer, ItemRegistry.CHERRY_DOOR.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedTrapdoor(consumer, ItemRegistry.CHERRY_TRAPDOOR.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapeless(ItemRegistry.CHERRY_BUTTON.get(), 1).requires(ItemRegistry.CHERRY_PLANKS.get())
                .unlockedBy("has_input", has(BlockRegistry.CHERRY_PLANKS.get()))
                .save(consumer);
        shapedPressurePlate(consumer, ItemRegistry.CHERRY_PRESSURE_PLATE.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedSign(consumer, ItemRegistry.CHERRY_SIGN.get(), ItemRegistry.CHERRY_PLANKS.get());
        shapedHangingSign(consumer, ItemRegistry.CHERRY_HANGING_SIGN.get(), ItemRegistry.STRIPPED_CHERRY_LOG.get());
        /*shapedBoat(consumer, ItemRegistry.CHERRY_BOAT.get(), ItemRegistry.CHERRY_PLANKS.get());
        chestBoat(consumer, ItemRegistry.CHERRY_BOAT_CHEST.get(), ItemRegistry.CHERRY_BOAT.get());*/

        shaped3x3(consumer, ItemRegistry.BAMBOO_BLOCK.get(), Items.BAMBOO, 1);

        shapeless(ItemRegistry.BAMBOO_PLANKS.get(), 2).requires(ItemRegistry.BAMBOO_BLOCK.get())
                .unlockedBy("has_input", has(BlockRegistry.BAMBOO_BLOCK.get()))
                .save(consumer);
        shapedStairs(consumer, ItemRegistry.BAMBOO_STAIRS.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedSlab(consumer, ItemRegistry.BAMBOO_SLAB.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedFence(consumer, ItemRegistry.BAMBOO_FENCE.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedFenceGate(consumer, ItemRegistry.BAMBOO_FENCE_GATE.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedDoor(consumer, ItemRegistry.BAMBOO_DOOR.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedTrapdoor(consumer, ItemRegistry.BAMBOO_TRAPDOOR.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapeless(ItemRegistry.BAMBOO_BUTTON.get(), 1).requires(ItemRegistry.BAMBOO_PLANKS.get())
                .unlockedBy("has_input", has(BlockRegistry.BAMBOO_PLANKS.get()))
                .save(consumer);
        shapedPressurePlate(consumer, ItemRegistry.BAMBOO_PRESSURE_PLATE.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedSign(consumer, ItemRegistry.BAMBOO_SIGN.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedHangingSign(consumer, ItemRegistry.BAMBOO_HANGING_SIGN.get(), ItemRegistry.STRIPPED_BAMBOO_BLOCK.get());
        /*shapedBoat(consumer, ItemRegistry.BAMBOO_RAFT.get(), ItemRegistry.BAMBOO_PLANKS.get());
        chestBoat(consumer, ItemRegistry.BAMBOO_CHEST_RAFT.get(), ItemRegistry.BAMBOO_RAFT.get());*/

        shaped1x2(consumer, ItemRegistry.BAMBOO_MOSAIC.get(), ItemRegistry.BAMBOO_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.BAMBOO_MOSAIC_STAIRS.get(), ItemRegistry.BAMBOO_MOSAIC.get());
        shapedSlab(consumer, ItemRegistry.BAMBOO_MOSAIC_SLAB.get(), ItemRegistry.BAMBOO_MOSAIC.get());

        shaped(ItemRegistry.CHISELED_BOOKSHELF.get(), 1).define('W', ItemTags.PLANKS).define('S', ItemTags.WOODEN_SLABS)
                .pattern("WWW").pattern("SSS").pattern("WWW")
                .unlockedBy("has_input", has(ItemTags.PLANKS))
                .save(consumer);

        shaped(ItemRegistry.DECORATED_POT.get(), 1).define('B', Items.BRICK)
                .pattern(" B ").pattern("B B").pattern("BBB")
                .unlockedBy("has_input", has(Items.BRICK))
                .save(consumer);
        shaped(ItemRegistry.CHISEL.get(), 1).define('C', Items.COPPER_INGOT).define('W', Tags.Items.RODS_WOODEN)
                .pattern(" C").pattern("W ")
                .unlockedBy("has_input", has(Items.COPPER_INGOT))
                .save(consumer);

        shapedHangingSign(consumer, ItemRegistry.OAK_HANGING_SIGN.get(), Items.STRIPPED_OAK_LOG);
        shapedHangingSign(consumer, ItemRegistry.SPRUCE_HANGING_SIGN.get(), Items.STRIPPED_SPRUCE_LOG);
        shapedHangingSign(consumer, ItemRegistry.BIRCH_HANGING_SIGN.get(), Items.STRIPPED_BIRCH_LOG);
        shapedHangingSign(consumer, ItemRegistry.JUNGLE_HANGING_SIGN.get(), Items.STRIPPED_JUNGLE_LOG);
        shapedHangingSign(consumer, ItemRegistry.ACACIA_HANGING_SIGN.get(), Items.STRIPPED_ACACIA_LOG);
        shapedHangingSign(consumer, ItemRegistry.DARK_OAK_HANGING_SIGN.get(), Items.STRIPPED_DARK_OAK_LOG);
        shapedHangingSign(consumer, ItemRegistry.MANGROVE_HANGING_SIGN.get(), Items.STRIPPED_MANGROVE_LOG);
        shapedHangingSign(consumer, ItemRegistry.CRIMSON_HANGING_SIGN.get(), Items.STRIPPED_CRIMSON_STEM);
        shapedHangingSign(consumer, ItemRegistry.WARPED_HANGING_SIGN.get(), Items.STRIPPED_WARPED_STEM);
    }
    private static void shaped1x2(Consumer<FinishedRecipe> recipeConsumer, ItemLike out, ItemLike input) {
        shaped(out, 4).define('#', input).pattern("#").pattern("#").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }
    private static void shaped2x2(Consumer<FinishedRecipe> recipeConsumer, ItemLike out, ItemLike input, int count) {
        shaped(out, count).define('#', input).pattern("##").pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }
    private static void shaped3x3(Consumer<FinishedRecipe> recipeConsumer, ItemLike out, ItemLike input, int count) {
        shaped(out, count).define('#', input).pattern("###").pattern("###").pattern("###")
                .unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedBoat(Consumer<FinishedRecipe> recipeConsumer, ItemLike boat, ItemLike input) {
        shaped(boat, 1).define('W', input)
                .pattern("   ").pattern("W W").pattern("WWW").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }
    private static void chestBoat(Consumer<FinishedRecipe> recipeConsumer, ItemLike chestBoat, ItemLike chest) {
        ShapelessRecipeBuilder.shapeless(chestBoat).requires(Blocks.CHEST).requires(chestBoat).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS)).save(recipeConsumer);
    }

    private static void shapedDoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, ItemLike input) {
        shaped(door, 3).define('#', input).pattern("##").pattern("##").pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, ItemLike input) {
        shaped(door, 2).define('#', input).pattern("   ").pattern("###").pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, ItemLike input) {
        shaped(fence, 3).define('#', Tags.Items.RODS_WOODEN).define('W', input).pattern("W#W").pattern("W#W").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedFenceGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike fenceGate, ItemLike input) {
        shaped(fenceGate).define('#', Tags.Items.RODS_WOODEN).define('W', input).pattern("#W#").pattern("#W#").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, ItemLike input) {
        shaped(pressurePlate).define('#', input).pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
        shaped(slab, 6).define('#', input).pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
        shaped(stairs, 4).define('#', input).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }
    private static void shapedSign(Consumer<FinishedRecipe> recipeConsumer, ItemLike sign, ItemLike input) {
        shaped(sign, 3).define('#', Tags.Items.RODS_WOODEN).define('W', input)
                .pattern("WWW").pattern("WWW").pattern(" # ").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedHangingSign(Consumer<FinishedRecipe> recipeConsumer, ItemLike sign, ItemLike input) {
        shaped(sign, 3).define('#', Items.CHAIN).define('W', input)
                .pattern("# #").pattern("WWW").pattern("WWW").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }
}
