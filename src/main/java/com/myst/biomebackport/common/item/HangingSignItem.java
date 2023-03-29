package com.myst.biomebackport.common.item;

import com.myst.biomebackport.common.block.HangingSignBlockCeiling;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Map;

public class HangingSignItem extends BlockItem {
    protected final Block wallBlock;

    public HangingSignItem(Block ceilingBlock, Block wallBlock, Item.Properties properties) {
        super(ceilingBlock, properties);
        this.wallBlock = wallBlock;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.getBlock().getStateForPlacement(context);
        BlockState blockstate1 = this.wallBlock.getStateForPlacement(context);

        if(context.getClickedFace() == Direction.DOWN) {
            if(blockstate != null) {
                if(context.getLevel().getBlockState(context.getClickedPos()).getBlock().hasDynamicShape() || context.getPlayer().isShiftKeyDown()) {
                    return blockstate.setValue(HangingSignBlockCeiling.ATTACHED, true);
                } else {
                    return blockstate;
                }
            } else {
                return null;
            }
        }
        return blockstate1;
    }

    @Override
    public void registerBlocks(Map<Block, Item> map, Item item) {
        super.registerBlocks(map, item);
        map.put(this.wallBlock, item);
    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> map, Item item) {
        super.removeFromBlockToItemMap(map, item);
        map.remove(this.wallBlock);
    }
}
