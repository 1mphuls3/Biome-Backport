package com.myst.biomebackport.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class ModLogBlock extends RotatedPillarBlock  {
    RegistryObject<RotatedPillarBlock> stripped;
    /**
     * @param strippedBlock The Block Registry Object that replaces this block when it is stripped (right-clicked with an axe)
     * */
    public ModLogBlock(Properties properties, RegistryObject<RotatedPillarBlock> strippedBlock) {
        super(properties);
        this.stripped = strippedBlock;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(toolAction.equals(ToolActions.AXE_STRIP)) {
        return stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
    }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}