package com.myst.biomebackport.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;

public class ModWoodBlock extends RotatedPillarBlock {
    RegistryObject<Block> stripped;
    /**
     * @param strippedBlock The Block Registry Object that replaces this block when it is stripped (right-clicked with an axe)
     * */
    public ModWoodBlock(Properties properties, RegistryObject<Block> strippedBlock) {
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
            return stripped.get().defaultBlockState();
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}