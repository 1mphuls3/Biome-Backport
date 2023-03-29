package com.myst.biomebackport.common.block;

import com.myst.biomebackport.client.screen.HangingSignEditScreen;
import com.myst.biomebackport.common.blockentity.HangingSignBlockEntity;
import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.networking.HangingSignC2SPacket;
import com.myst.biomebackport.core.networking.ModMessages;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HangingSignBlock extends ModBlock<HangingSignBlockEntity> implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private final WoodType type;

    protected HangingSignBlock(BlockBehaviour.Properties properties, WoodType type) {
        super(properties);
        setBlockEntity(BlockEntityRegistry.HANGING_SIGN);
        this.type = type;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(placer instanceof Player player) {
            if(level.getBlockEntity(pos) instanceof HangingSignBlockEntity block) {
                block.setEditable(true);
                block.setAllowedPlayerEditor(player.getUUID());
            }
            if(player.getLevel().isClientSide()) {
                Minecraft.getInstance().setScreen(new HangingSignEditScreen(BlockEntityRegistry.HANGING_SIGN.get().getBlockEntity(level, pos),
                        Minecraft.getInstance().isTextFilteringEnabled()));
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState facing, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, dir, facing, level, pos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isPossibleToRespawnInThis() {
        return true;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        boolean flag = item instanceof DyeItem;
        boolean flag1 = itemstack.is(Items.GLOW_INK_SAC);
        boolean flag2 = itemstack.is(Items.INK_SAC);
        boolean flag3 = (flag1 || flag || flag2) && player.getAbilities().mayBuild;
        if (level.isClientSide) {
            return flag3 ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (!(blockentity instanceof HangingSignBlockEntity signblockentity)) {
                return InteractionResult.PASS;
            } else {
                boolean flag4 = signblockentity.hasGlowingText();
                if ((!flag1 || !flag4) && (!flag2 || flag4)) {
                    if (flag3) {
                        boolean flag5;
                        if (flag1) {
                            level.playSound(null, pos, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            flag5 = signblockentity.setHasGlowingText(true);
                            if (player instanceof ServerPlayer) {
                                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, itemstack);
                            }
                        } else if (flag2) {
                            level.playSound(null, pos, SoundEvents.INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            flag5 = signblockentity.setHasGlowingText(false);
                        } else {
                            level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                            flag5 = signblockentity.setColor(((DyeItem)item).getDyeColor());
                        }

                        if (flag5) {
                            if (!player.isCreative()) {
                                itemstack.shrink(1);
                            }

                            player.awardStat(Stats.ITEM_USED.get(item));
                        }
                    }

                    return signblockentity.executeClickCommands((ServerPlayer)player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
                } else {
                    return InteractionResult.PASS;
                }
            }
        }
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public WoodType type() {
        return this.type;
    }
}
