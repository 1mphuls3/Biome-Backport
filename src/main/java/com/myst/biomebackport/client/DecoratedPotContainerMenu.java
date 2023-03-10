package com.myst.biomebackport.client;

import com.myst.biomebackport.common.blockentity.IContainerProvider;
import com.myst.biomebackport.core.config.ServerConfig;
import com.myst.biomebackport.core.registry.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class DecoratedPotContainerMenu extends AbstractContainerMenu implements IContainerProvider {
    public final Container inventory;

    @Override
    public Container getContainer() {
        return inventory;
    }

    public DecoratedPotContainerMenu(int id, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(id, playerInventory);
    }

    public DecoratedPotContainerMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(27));
    }

    public DecoratedPotContainerMenu(int id, Inventory playerInventory, Container inventory) {
        super(MenuRegistry.POT_CONTAINER.get(), id);
        this.inventory = inventory;
        inventory.startOpen(playerInventory.player);

        int size = ServerConfig.SLOTS.get();

        int[] dims = DecoratedPotContainerMenu.getRatio(size);
        if (dims[0] > 9) {
            dims[0] = 9;
        }

        int yp = 17 + (18 * 3) / 2 - (9);

        int dimx = 0;
        int dimXPrev;
        int xp;
        dimXPrev = dimx;
        dimx = Math.min(dims[0], size);
        xp = 8 + (18 * 9) / 2 - (dimx * 18) / 2;
        for (int j = 0; j < dimx; ++j) {
            this.addSlot(new Slot(inventory, j + (dimXPrev), xp + j * 18, yp + 18));
        }

        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInventory, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInventory, si, 8 + si * 18, 142));
    }


    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            itemstack = item.copy();
            int activeSlots = ServerConfig.SLOTS.get();
            if (index < activeSlots) {
                if (!this.moveItemStackTo(item, activeSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(item, 0, activeSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    public static int[] getRatio(int maxSize) {
        int[] dims = {Math.min(maxSize, 23), Math.max(maxSize / 23, 1)};
        for (int[] testAgainst : RATIOS) {
            if (testAgainst[0] * testAgainst[1] == maxSize) {
                dims = testAgainst;
                break;
            }
        }
        return dims;
    }

    private static final int[][] RATIOS = new int[][]{{1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}, {6, 1}, {7, 1}, {8, 1}, {9, 1},};
}

