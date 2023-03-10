package com.myst.biomebackport.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class HangingSignBlockCeiling extends HangingSignBlock {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final BooleanProperty ATTACHED = BooleanProperty.create("attached");
    private static final VoxelShape SHAPEX = Block.box(7, 0, 1, 9, 10, 15);
    private static final VoxelShape SHAPEZ = Block.box(1, 0, 7, 15, 10, 9);

    public HangingSignBlockCeiling(BlockBehaviour.Properties pProperties, WoodType pType) {
        super(pProperties, pType);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0).setValue(WATERLOGGED, Boolean.FALSE).setValue(ATTACHED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(state.getValue(ROTATION) == 12 || state.getValue(ROTATION) == 4) {
            return SHAPEX;
        } else if (state.getValue(ROTATION) == 0 || state.getValue(ROTATION) == 8) {
            return SHAPEZ;
        } else {
            return SHAPE;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).getBlock().hasDynamicShape();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
        BlockState state = level.getBlockState(pos);

        VoxelShape shape = context.getLevel().getBlockState(pos).getBlock().getShape(state, level, pos, CollisionContext.empty());

        Direction.Axis axis = Direction.Axis.X;
        int rot = context.getHorizontalDirection().getOpposite().get2DDataValue()*4;

        if(rot == 12 || rot == 4) {
            axis = Direction.Axis.Z;
        } else if (rot == 0 || rot == 8) {
            axis = Direction.Axis.X;
        }

        boolean attached = ((shape.max(axis) <= (0.625f)) || shape.min(axis) >= (0.375f));

        context.getPlayer().displayClientMessage(Component.literal(((shape.max(axis)) + " " + shape.min(axis))), true);

        if(!state.isFaceSturdy(level, pos, context.getClickedFace()) && !attached) return null;

        attached = context.getPlayer().isShiftKeyDown() ? true : attached;

        return this.defaultBlockState()
                .setValue(ROTATION, attached ? Mth.floor((double) ((180.0F + context.getRotation()) * 16.0F / 360.0F) + 0.5D) & 15 : rot)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER)
                .setValue(ATTACHED, attached);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.setValue(ROTATION, pMirror.mirror(pState.getValue(ROTATION), 16));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ROTATION, WATERLOGGED, ATTACHED);
    }
}
