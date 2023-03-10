package com.myst.biomebackport.common.block;

import com.myst.biomebackport.common.blockentity.ChiseledBookshelfBlockEntity;
import com.myst.biomebackport.core.block.ModBlock;
import com.myst.biomebackport.core.blockentity.ExtendedItemStackHandler;
import com.myst.biomebackport.core.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ChiseledBookshelfBlock extends ModBlock<ChiseledBookshelfBlockEntity> {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty SLOT_0_OCCUPIED = BooleanProperty.create("slot_0_occupied");
    public static final BooleanProperty SLOT_1_OCCUPIED = BooleanProperty.create("slot_1_occupied");
    public static final BooleanProperty SLOT_2_OCCUPIED = BooleanProperty.create("slot_2_occupied");
    public static final BooleanProperty SLOT_3_OCCUPIED = BooleanProperty.create("slot_3_occupied");
    public static final BooleanProperty SLOT_4_OCCUPIED = BooleanProperty.create("slot_4_occupied");
    public static final BooleanProperty SLOT_5_OCCUPIED = BooleanProperty.create("slot_5_occupied");

    public ChiseledBookshelfBlock(BlockBehaviour.Properties properties) {
        super(properties);
        setBlockEntity(BlockEntityRegistry.CHISELED_BOOKSHELF);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(SLOT_0_OCCUPIED, false).setValue(SLOT_1_OCCUPIED, false).setValue(SLOT_2_OCCUPIED, false)
                .setValue(SLOT_3_OCCUPIED, false).setValue(SLOT_4_OCCUPIED, false).setValue(SLOT_5_OCCUPIED, false));
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        int signal = 0;
        if(level.getBlockEntity(pos) instanceof ChiseledBookshelfBlockEntity block) {
            signal = block.inventory.slotChanged;
        }
        return signal;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(SLOT_0_OCCUPIED, false).setValue(SLOT_1_OCCUPIED, false).setValue(SLOT_2_OCCUPIED, false)
                .setValue(SLOT_3_OCCUPIED, false).setValue(SLOT_4_OCCUPIED, false).setValue(SLOT_5_OCCUPIED, false);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
                .setValue(SLOT_0_OCCUPIED, false).setValue(SLOT_1_OCCUPIED, false).setValue(SLOT_2_OCCUPIED, false)
                .setValue(SLOT_3_OCCUPIED, false).setValue(SLOT_4_OCCUPIED, false).setValue(SLOT_5_OCCUPIED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SLOT_0_OCCUPIED, SLOT_1_OCCUPIED, SLOT_2_OCCUPIED, SLOT_3_OCCUPIED, SLOT_4_OCCUPIED, SLOT_5_OCCUPIED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block();
    }
}
