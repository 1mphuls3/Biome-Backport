package com.myst.biomebackport.common.blockentity;

import com.myst.biomebackport.common.sound.SoundRegistry;
import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.blockentity.ExtendedItemStackHandler;
import com.myst.biomebackport.core.blockentity.ModBlockEntity;
import com.myst.biomebackport.core.helper.BlockHelper;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

import static com.myst.biomebackport.common.block.ChiseledBookshelfBlock.*;

public class ChiseledBookshelfBlockEntity extends ModBlockEntity<ModBlock> {
    public ExtendedItemStackHandler inventory = new ExtendedItemStackHandler(6, 1, stack ->
            stack.getItem() instanceof BookItem || stack.getItem() instanceof EnchantedBookItem
                    || stack.getItem() instanceof WritableBookItem || stack.getItem() instanceof WrittenBookItem) {
        @Override
        public void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            slotChanged = slot+1;
            BlockHelper.updateStateAndNeighbor(level, worldPosition);
        }
    };

    public ChiseledBookshelfBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ChiseledBookshelfBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CHISELED_BOOKSHELF.get(), pos, state);
    }

    protected void updateBlockState(BlockState state) {
        boolean slot0 = !inventory.getStackInSlot(0).isEmpty();
        boolean slot1 = !inventory.getStackInSlot(1).isEmpty();
        boolean slot2 = !inventory.getStackInSlot(2).isEmpty();
        boolean slot3 = !inventory.getStackInSlot(3).isEmpty();
        boolean slot4 = !inventory.getStackInSlot(4).isEmpty();
        boolean slot5 = !inventory.getStackInSlot(5).isEmpty();
        level.setBlock(getBlockPos(), state
                .setValue(SLOT_0_OCCUPIED, slot0).setValue(SLOT_1_OCCUPIED, slot1).setValue(SLOT_2_OCCUPIED, slot2)
                .setValue(SLOT_3_OCCUPIED, slot3).setValue(SLOT_4_OCCUPIED, slot4).setValue(SLOT_5_OCCUPIED, slot5), 2);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        HitResult hitResult = Minecraft.getInstance().hitResult;
        double x = (hitResult.getLocation().x);
        double y = (hitResult.getLocation().y);
        double z = (hitResult.getLocation().z);

        double xla = Minecraft.getInstance().player.getLookAngle().x;
        double yla = Minecraft.getInstance().player.getLookAngle().y;
        double zla = Minecraft.getInstance().player.getLookAngle().z;

        if ((x%1==0)&&(xla<0))x-=0.01;
        if ((y%1==0)&&(yla<0))y-=0.01;
        if ((z%1==0)&&(zla<0))z-=0.01;

        BlockPos p = this.getBlockPos();

        Vec3 pos = new Vec3(x-p.getX(), y-p.getY(), z-p.getZ());

        if(pos.z == 0 && this.getBlockState().getValue(FACING) == Direction.NORTH) { //North Face
            if(pos.y <= 8/16F && pos.y >= 0) { //Bottom Row
                faceInteract(pos.x, false, true, level, player, hand);
            } else if(pos.y <= 1 && pos.y >= 8/16F) { //Top Row
                faceInteract(pos.x, false, false, level, player, hand);
            }
            return InteractionResult.SUCCESS;
        } else if(pos.z >= 0.9 && this.getBlockState().getValue(FACING) == Direction.SOUTH) { //South Face
            if(pos.y <= 8/16F && pos.y >= 0) { //Bottom Row
                faceInteract(pos.x, true, true, level, player, hand);
            } else if(pos.y <= 1 && pos.y >= 8/16F) { //Top Row
                faceInteract(pos.x, true, false, level, player, hand);
            }
            return InteractionResult.SUCCESS;
        } else if(pos.x >= 0.9 && this.getBlockState().getValue(FACING) == Direction.EAST) { //East Face
            if(pos.y <= 8/16F && pos.y >= 0) { //Bottom Row
                faceInteract(pos.z, false, true, level, player, hand);
            } else if(pos.y <= 1 && pos.y >= 8/16F) { //Top Row
                faceInteract(pos.z, false, false, level, player, hand);
            }
            return InteractionResult.SUCCESS;
        } else if(pos.x == 0 && this.getBlockState().getValue(FACING) == Direction.WEST) { //West Face
            if(pos.y <= 8/16F && pos.y >= 0) { //Bottom Row
                faceInteract(pos.z, true, true, level, player, hand);
            } else if(pos.y <= 1 && pos.y >= 8/16F) { //Top Row
                faceInteract(pos.z, true, false, level, player, hand);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private void faceInteract(double axis, boolean reverse, boolean bottom, Level level, Player player, InteractionHand hand) {
        int[] slots = reverse ? new int[]{3, 4, 5, 0, 1, 2} : new int[]{5, 4, 3, 2 ,1 ,0};
        if(bottom) {
            interact(axis, 0, 5, slots[0], level, player, hand);
            interact(axis, 5, 10, slots[1], level, player, hand);
            interact(axis, 10, 16, slots[2], level, player, hand);
        } else {
            interact(axis, 0, 5, slots[3], level, player, hand);
            interact(axis, 5, 10, slots[4], level, player, hand);
            interact(axis, 10, 16, slots[5], level, player, hand);
        }
    }

    private void interact(double axis, int min, int max, int slot, Level level, Player player, InteractionHand hand) {
        if(axis <= max/16F && axis >= min/16F) {
            ItemStack held = player.getItemInHand(hand);
            if(held.isEmpty() && inventory.getStackInSlot(slot).isEmpty()) return;
            if(!held.isEmpty() && !(held.getItem() instanceof BookItem || held.getItem() instanceof EnchantedBookItem
            || held.getItem() instanceof WritableBookItem || held.getItem() instanceof WrittenBookItem)) return;
            player.swing(hand, true);
            if(inventory.getStackInSlot(slot).isEmpty()) {
                level.playSound(player, worldPosition, held.getItem() instanceof EnchantedBookItem ?
                        SoundRegistry.CHISELED_BOOKSHELF_INSERT_ENCHANTED.get() : SoundRegistry.CHISELED_BOOKSHELF_INSERT.get(), SoundSource.BLOCKS, 1F, 1F);
                inventory.insertItem(level, slot, held);
            } else {
                level.playSound(player, worldPosition, inventory.getStackInSlot(slot).getItem() instanceof EnchantedBookItem ?
                        SoundRegistry.CHISELED_BOOKSHELF_PICKUP_ENCHANTED.get() : SoundRegistry.CHISELED_BOOKSHELF_PICKUP.get(), SoundSource.BLOCKS, 1F, 1F);
                inventory.extractItem(level, slot, held, player, false);
            }
            this.updateBlockState(getBlockState());
        }
    }

    @Override
    public void onBreak() {
        inventory.dropItems(level, getBlockPos());
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        nbt.putInt("slotChanged", inventory.slotChanged);
        inventory.save(nbt, "inventory");
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        inventory.slotChanged = nbt.contains("slotChanged") ? nbt.getInt("slotChanged") : 0;
        inventory.load(nbt, "inventory");
        super.load(nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventory.invOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventory.invOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}