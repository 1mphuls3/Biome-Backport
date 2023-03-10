package com.myst.biomebackport.core.blockentity;

import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.helper.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class ModInventoryBlockEntity extends ModBlockEntity<ModBlock> {
    public ExtendedItemStackHandler inv;

    public ModInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ModInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        inv.interact(player.level, player, hand);
        return InteractionResult.SUCCESS;
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    //drops items when broken
    @Override
    public void onBreak() {
        inv.dropItems(level, VecHelper.vec3FromBlockPos(worldPosition).add(0.5f,0.5f,0.5f));
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inv.save(compound);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inv.load(compound);
        super.load(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return inv.invOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return inv.invOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
