package com.myst.biomebackport.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

public class HangingSignBlockWall extends HangingSignBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPEX = Stream.of(
            Block.box(7, 0, 1, 9, 10, 15),
            Block.box(6, 14, 0, 10, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPEZ = Stream.of(
            Block.box(1, 0, 7, 15, 10, 9),
            Block.box(0, 14, 6, 16, 16, 10)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public HangingSignBlockWall(BlockBehaviour.Properties pProperties, WoodType pType) {
        super(pProperties, pType);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public String getDescriptionId() {
        return this.asItem().getDescriptionId();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(state.getValue(FACING).getAxis() == Direction.Axis.X) {
            return SHAPEX;
        } else {
            return SHAPEZ;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] direction = Direction.orderedByNearest(context.getPlayer());
        Direction clickedFace = context.getClickedFace().getOpposite();

        Direction dir = direction[0];

        if(direction[0] == clickedFace) {
            for (int i = 1; i < direction.length; i++) {
                dir = direction[i];
                if(direction[i] != Direction.UP && direction[i] != Direction.DOWN) {
                    break;
                }
            }
        }

        if(dir.getAxis().isHorizontal() && (context.getLevel().getBlockState(blockpos.relative(blockstate.getValue(FACING).getClockWise()))
                .isFaceSturdy(context.getLevel(), blockpos, clickedFace) || 
                context.getLevel().getBlockState(blockpos.relative(blockstate.getValue(FACING).getCounterClockWise()))
                .isFaceSturdy(context.getLevel(), blockpos, clickedFace))) {
            blockstate = blockstate.setValue(FACING, dir.getOpposite());
            if (blockstate.canSurvive(levelreader, blockpos)) {
                return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            }
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState pFacingState, LevelAccessor level, BlockPos pos, BlockPos pFacingPos) {
        return facing.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, pFacingState, level, pos, pFacingPos);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
    }
}