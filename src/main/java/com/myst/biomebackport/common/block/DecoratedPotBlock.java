package com.myst.biomebackport.common.block;

import com.google.common.collect.ImmutableMap;
import com.myst.biomebackport.common.blockentity.ChiseledBookshelfBlockEntity;
import com.myst.biomebackport.common.blockentity.DecoratedPotBlockEntity;
import com.myst.biomebackport.common.sound.SoundRegistry;
import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.helper.IntHelper;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import com.myst.biomebackport.core.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class DecoratedPotBlock extends Block implements EntityBlock {
    public static final IntegerProperty NORTH = IntegerProperty.create("north", 0, 4);
    public static final IntegerProperty EAST = IntegerProperty.create("east", 0, 4);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south", 0, 4);
    public static final IntegerProperty WEST = IntegerProperty.create("west", 0, 4);
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
    VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);
    public DecoratedPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, 0).setValue(EAST, 0)
                .setValue(SOUTH, 0).setValue(WEST, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(player.getItemInHand(hand).getItem() == ItemRegistry.CHISEL.get()) {

            Direction side = hit.getDirection();
            if(side == Direction.NORTH) {
                level.setBlock(pos, state.setValue(NORTH, IntHelper.wrapAround(state.getValue(NORTH) + 1, 0, 4)), 2);
            } else if(side == Direction.EAST) {
                level.setBlock(pos, state.setValue(EAST, IntHelper.wrapAround(state.getValue(EAST) + 1, 0, 4)), 2);
            } else if(side == Direction.WEST) {
                level.setBlock(pos, state.setValue(WEST, IntHelper.wrapAround(state.getValue(WEST) + 1, 0, 4)), 2);
            } else if(side == Direction.SOUTH) {
                level.setBlock(pos, state.setValue(SOUTH, IntHelper.wrapAround(state.getValue(SOUTH) + 1, 0, 4)), 2);
            }

            if(side.getAxis() != Direction.Axis.Y) {
                level.playSound(player, pos, SoundRegistry.CHISEL_USE.get(), SoundSource.BLOCKS, 1.0F, 0.5F);
                player.getItemInHand(hand).hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(hand);
                });
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.CONSUME;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            if (level.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
                player.openMenu(pot);

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(player.getItemInHand(player.getUsedItemHand()).is(Tags.Items.TOOLS_PICKAXES)) {
            level.playSound(player, pos, SoundRegistry.DECORATED_POT_SHATTER.get(), SoundSource.BLOCKS, 1.0F, 0.5F);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (level.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
            pot.recheckOpen();
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof DecoratedPotBlockEntity) {
                Containers.dropContents(level, pos, (DecoratedPotBlockEntity)blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, moving);
        }
    }
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof DecoratedPotBlockEntity blockEntity) {
            builder = builder.withDynamicDrop(CONTENTS, (context, stackConsumer) -> {
                for (int i = 0; i < blockEntity.getContainerSize(); ++i) {
                    stackConsumer.accept(blockEntity.getItem(i));
                }
            });
        }
        return super.getDrops(state, builder);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack itemstack = super.getCloneItemStack(state, target, world, pos, player);
        if (world.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
            CompoundTag compoundTag = new CompoundTag();
            pot.saveAdditional(compoundTag);
            if (!compoundTag.isEmpty()) {
                itemstack.addTagElement("BlockEntityTag", compoundTag);
            }
        }
        return itemstack;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(NORTH, 0).setValue(EAST, 0)
                .setValue(SOUTH, 0).setValue(WEST, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DecoratedPotBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return EntityBlock.super.getTicker(level, state, type);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel level, T blockEntity) {
        return EntityBlock.super.getListener(level, blockEntity);
    }
}
