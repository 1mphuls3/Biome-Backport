package com.myst.biomebackport.core.block;

import com.myst.biomebackport.core.blockentity.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModBlock<T extends BlockEntity> extends Block implements EntityBlock {
    protected Supplier<BlockEntityType<T>> blockEntityType = null;
    protected BlockEntityTicker<T> ticker = null;
    protected boolean opaque;
    public ModBlock(Properties properties) {
        super(properties);
    }

    public ModBlock<T> setBlockEntity(Supplier<BlockEntityType<T>> type) {
        this.ticker = (level, pos, state, blockEntity) -> ((ModBlockEntity<ModBlock>) blockEntity).tick(level, state, pos);
        this.blockEntityType = type;
        return this;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if(isBlockEntity(state)){
            return blockEntityType.get().create(pos, state);
        }
        return null;
    }

    public boolean isBlockEntity(BlockState state) {
        return this.blockEntityType != null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (isBlockEntity(state)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ModBlockEntity simpleBlockEntity) {
                simpleBlockEntity.onPlace(placer, stack);
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        if (isBlockEntity(state)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ModBlockEntity modBlockEntity) {
                ItemStack stack = modBlockEntity.clone(state, target, world, pos, player);
                if (!stack.isEmpty())
                {
                    return stack;
                }
            }
        }
        return super.getCloneItemStack(state, target, world, pos, player);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        onBlockBroken(state, level, pos, player);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        onBlockBroken(state, level, pos, null);
        super.onBlockExploded(state, level, pos, explosion);
    }

    public void onBlockBroken(BlockState state, BlockGetter level, BlockPos pos, @Nullable Player player) {
        if (isBlockEntity(state)) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ModBlockEntity blockEntity) {
                blockEntity.onBreak();
            }
        }
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (isBlockEntity(state)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ModBlockEntity modBlockEntity) {
                return modBlockEntity.onUse(player, hand);
            }
        }
        return super.use(state, level, pos, player, hand, ray);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (BlockEntityTicker<T>) ticker;
    }
}
