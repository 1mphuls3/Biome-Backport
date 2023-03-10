package com.myst.biomebackport.common.blockentity;

import com.google.common.base.Suppliers;
import com.myst.biomebackport.client.DecoratedPotContainerMenu;
import com.myst.biomebackport.core.blockentity.ContainerBlockEntity;
import com.myst.biomebackport.core.blockentity.ExtendedItemStackHandler;
import com.myst.biomebackport.core.helper.BlockHelper;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
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

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    protected void updateBlockState(BlockState state, boolean b) {
        //
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.biomebackport.pot");
    }

    @Override
    protected void playOpenSound(BlockState state) {
        double d0 = (double) this.worldPosition.getX() + 0.5D;
        double d1 = (double) this.worldPosition.getY() + 1;
        double d2 = (double) this.worldPosition.getZ() + 0.5D;
        /*this.level.playSound(null, d0, d1, d2, ModSounds.SACK_OPEN.get(), SoundSource.BLOCKS, 1,
                this.level.random.nextFloat() * 0.1F + 0.95F);*/
    }

    @Override
    protected void playCloseSound(BlockState state) {
        double d0 = (double) this.worldPosition.getX() + 0.5D;
        double d1 = (double) this.worldPosition.getY() + 1;
        double d2 = (double) this.worldPosition.getZ() + 0.5D;
        /*this.level.playSound(null, d0, d1, d2, ModSounds.SACK_OPEN.get(), SoundSource.BLOCKS, 1,
                this.level.random.nextFloat() * 0.1F + 0.8F);*/
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
}