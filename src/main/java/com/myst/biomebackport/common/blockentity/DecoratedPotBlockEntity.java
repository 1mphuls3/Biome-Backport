package com.myst.biomebackport.common.blockentity;

import com.google.common.base.Suppliers;
import com.myst.biomebackport.client.DecoratedPotContainerMenu;
import com.myst.biomebackport.core.blockentity.ContainerBlockEntity;
import com.myst.biomebackport.core.config.ServerConfig;
import com.myst.biomebackport.core.helper.IntHelper;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DecoratedPotBlockEntity extends ContainerBlockEntity {
    public DecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.DECORATED_POT.get(), pos, state, 9);
    }
    public int NORTH;
    public int EAST;
    public int WEST;
    public int SOUTH;

    public void onUse(Player player, Direction side) {
        int step = player.isShiftKeyDown() ? -1 : 1;

        if(side == Direction.NORTH) {
            NORTH = IntHelper.wrapAround(NORTH + step, 0, 20);
        } else if(side == Direction.EAST) {
            EAST = IntHelper.wrapAround(EAST + step, 0, 20);
        } else if(side == Direction.WEST) {
            WEST = IntHelper.wrapAround(WEST + step, 0, 20);
        } else if(side == Direction.SOUTH) {
            SOUTH = IntHelper.wrapAround(SOUTH + step, 0, 20);
        }
    }

    @Override
    public int getContainerSize() {
        return ServerConfig.SLOTS.get();
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.biomebackport.pot");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player) {
        return new DecoratedPotContainerMenu(id, player, this);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return isAllowedInShulker(stack);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    private static final Supplier<ShulkerBoxBlockEntity> SHULKER_TILE =
            Suppliers.memoize(() -> new ShulkerBoxBlockEntity(BlockPos.ZERO, Blocks.SHULKER_BOX.defaultBlockState()));

    public static boolean isAllowedInShulker(ItemStack stack) {
        return SHULKER_TILE.get().canPlaceItemThroughFace(0, stack, null);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("north", NORTH);
        tag.putInt("east", EAST);
        tag.putInt("west", WEST);
        tag.putInt("south", SOUTH);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        NORTH = tag.getInt("north");
        EAST = tag.getInt("east");
        WEST = tag.getInt("west");
        SOUTH = tag.getInt("south");
        super.load(tag);
    }

    @Override
    protected void updateBlockState(BlockState state, boolean b) {
        //
    }

    @Override
    protected void playOpenSound(BlockState state) {
        //
    }

    @Override
    protected void playCloseSound(BlockState state) {
        //
    }
}