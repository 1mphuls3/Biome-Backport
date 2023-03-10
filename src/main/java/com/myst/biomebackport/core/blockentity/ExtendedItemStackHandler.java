package com.myst.biomebackport.core.blockentity;

import com.myst.biomebackport.core.helper.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExtendedItemStackHandler extends ItemStackHandler {
    public final int slotCount;
    public final int itemCount;
    public int slotChanged;
    
    public ArrayList<Item> items = new ArrayList<>();
    public ArrayList<ItemStack> itemStacks = new ArrayList<>();
    
    public Predicate<ItemStack> inPredicate;
    public Predicate<ItemStack> outPredicate;

    public int firstEmptyIndex;
    
    public final LazyOptional<IItemHandler> invOptional = LazyOptional.of(() -> this);
    
    public ExtendedItemStackHandler(int slotCount, int itemCount) {
        super(slotCount);
        this.slotCount = slotCount;
        this.itemCount = itemCount;
        update();
    }

    public ExtendedItemStackHandler(int slotCount, int itemCount, Predicate<ItemStack> inputPred) {
        this(slotCount, itemCount);
        this.inPredicate = inputPred;
    }

    public ExtendedItemStackHandler(int slotCount, int itemCount, Predicate<ItemStack> inputPred, Predicate<ItemStack> outputPred) {
        this(slotCount, itemCount, inputPred);
        this.outPredicate = outputPred;
    }

    public void update() {
        items = getItems();
        itemStacks = getNonEmptyItemStacks();
        firstEmptyIndex = getfirstEmptyIndex();
    }

    @Override
    public void onContentsChanged(int slot) {
        update();
    }

    @Override
    public int getSlots() {
        return slotCount;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemCount;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (inPredicate != null) {
            if (!inPredicate.test(stack)) {
                return false;
            }
        }
        return super.isItemValid(slot, stack);
    }

    public int getfirstEmptyIndex() {
        for (int i = 0; i < slotCount; i++) {
            if (getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    public ArrayList<ItemStack> getNonEmptyItemStacks() {
        return getStacks().stream().filter(stack -> !stack.isEmpty()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Item> getItems() {
        return getStacks().stream().map(ItemStack::getItem).collect(Collectors.toCollection(ArrayList::new));
    }

    public void clear() {
        for (int i = 0; i < slotCount; i++) {
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void dropItems(Level level, BlockPos pos) {
        dropItems(level, VecHelper.vec3FromBlockPos(pos).add(0.5, 0.5, 0.5));
    }

    public void dropItems(Level level, Vec3 pos) {
        for (int i = 0; i < slotCount; i++) {
            if (!getStackInSlot(i).isEmpty()) {
                level.addFreshEntity(new ItemEntity(level, pos.x(), pos.y(), pos.z(), getStackInSlot(i)));
            }
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public ItemStack interact(Level level, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        player.swing(hand, true);
        int size = itemStacks.size() - 1;
        if ((held.isEmpty() || firstEmptyIndex == -1) && size != -1) {
            ItemStack removeStack = itemStacks.get(size);
            if (removeStack.getItem().equals(held.getItem()) && !player.isCrouching()) {
                return insertItem(level, held);
            }
            ItemStack extractedStack = extractItem(level, held, player);
            if (!extractedStack.isEmpty()) {
                insertItem(level, held);
            }
            return extractedStack;
        } else {
            return insertItem(level, held);
        }
    }

    public ItemStack slotInteract(Level level, int slot, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        player.swing(hand, true);
        int size = itemStacks.size() - 1;
        if ((held.isEmpty() || firstEmptyIndex == -1) && size != -1) {
            ItemStack removeStack = itemStacks.get(size);
            if (removeStack.getItem().equals(held.getItem()) && !player.isCrouching()) {
                if(insertItem(level, slot, held) == ItemStack.EMPTY) {
                    return extractItem(level, slot, held, player, true);
                } else {
                    return insertItem(level, slot, held);
                }
            }
            ItemStack extractedStack = extractItem(level, slot, held, player, true);
            if (!extractedStack.isEmpty()) {
                insertItem(level, slot, held);
            }
            return extractedStack;
        } else {
            return insertItem(level, slot, held);
        }
    }

    public ItemStack extractItem(Level level, int slot, ItemStack held, Player player, boolean sound) {
        if (!level.isClientSide) {
            List<ItemStack> nonEmptyStacks = this.itemStacks;
            if (nonEmptyStacks.isEmpty()) {
                return held;
            }
            ItemStack removeStack = this.getStackInSlot(slot);
            if (extractItem(slot, removeStack.getCount(), true).equals(ItemStack.EMPTY)) {
                return held;
            }
            extractItem(player, removeStack, slot, sound);
            return removeStack;
        }
        return ItemStack.EMPTY;
    }

    public ItemStack extractItem(Level level, ItemStack held, Player player) {
        if (!level.isClientSide) {
            List<ItemStack> nonEmptyStacks = this.itemStacks;
            if (nonEmptyStacks.isEmpty()) {
                return held;
            }
            ItemStack removeStack = nonEmptyStacks.get(nonEmptyStacks.size() - 1);
            int slot = stacks.indexOf(removeStack);
            if (extractItem(slot, removeStack.getCount(), true).equals(ItemStack.EMPTY)) {
                return held;
            }
            extractItem(player, removeStack, slot, true);
            return removeStack;
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (outPredicate != null) {
            if (!outPredicate.test(super.extractItem(slot, amount, true))) {
                return ItemStack.EMPTY;
            }
        }
        return super.extractItem(slot, amount, simulate);
    }

    public void extractItem(Player player, ItemStack stack, int slot, boolean sound) {
        if(sound) {
            ItemHandlerHelper.giveItemToPlayer(player, stack, player.getInventory().selected);
        } else {
            giveItemToPlayer(player, stack, player.getInventory().selected);
        }
        this.onContentsChanged(slot);
        setStackInSlot(slot, ItemStack.EMPTY);
    }

    public ItemStack insertItem(Level level, int slot, ItemStack stack) {
        if (!level.isClientSide) {
            if (!stack.isEmpty()) {
                ItemStack simulate = insertItem(slot, stack, true);
                if (simulate.equals(stack)) {
                    return ItemStack.EMPTY;
                }
                int count = stack.getCount() - simulate.getCount();
                if (count > itemCount) {
                    count = itemCount;
                }
                ItemStack input = stack.split(count);
                insertItem(slot, input, false);
                return input;
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack insertItem(Level level, ItemStack stack) {
        if (!level.isClientSide) {
            if (!stack.isEmpty()) {
                ItemStack simulate = insertItem(stack, true);
                if (simulate.equals(stack)) {
                    return ItemStack.EMPTY;
                }
                int count = stack.getCount() - simulate.getCount();
                if (count > itemCount) {
                    count = itemCount;
                }
                ItemStack input = stack.split(count);
                insertItem(input, false);
                return input;
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack insertItem(ItemStack stack, boolean simulate) {
        return ItemHandlerHelper.insertItem(this, stack, simulate);
    }

    public void load(CompoundTag compound) {
        load(compound, "inventory");
    }

    public void load(CompoundTag compound, String name) {
        deserializeNBT(compound.getCompound(name));
        if (stacks.size() != slotCount) {
            int missing = slotCount -stacks.size();
            for (int i = 0; i < missing; i++) {
                stacks.add(ItemStack.EMPTY);
            }
        }
        update();
    }

    public void save(CompoundTag compound) {
        save(compound, "inventory");
    }

    public void save(CompoundTag compound, String name) {
        compound.put(name, serializeNBT());
    }

    //Copy of ItemHandlerHelper#giveItemToPlayer without the sound playing
    public static void giveItemToPlayer(Player player, @NotNull ItemStack stack, int preferredSlot) {
        if (stack.isEmpty()) return;

        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        Level level = player.level;

        // try adding it into the inventory
        ItemStack remainder = stack;
        // insert into preferred slot first
        if (preferredSlot >= 0 && preferredSlot < inventory.getSlots())
        {
            remainder = inventory.insertItem(preferredSlot, stack, false);
        }
        // then into the inventory in general
        if (!remainder.isEmpty())
        {
            remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
        }

        // drop remaining itemstack into the level
        if (!remainder.isEmpty() && !level.isClientSide)
        {
            ItemEntity entityitem = new ItemEntity(level, player.getX(), player.getY() + 0.5, player.getZ(), remainder);
            entityitem.setPickUpDelay(40);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));

            level.addFreshEntity(entityitem);
        }
    }
}
