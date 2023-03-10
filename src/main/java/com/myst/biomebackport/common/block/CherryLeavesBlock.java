package com.myst.biomebackport.common.block;

import com.myst.biomebackport.client.particle.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CherryLeavesBlock extends LeavesBlock {
    public CherryLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(!(level.getBlockState(pos.below()).getBlock() instanceof CherryLeavesBlock)) {
            if(random.nextFloat() > 0.75F) {
                Vec3 offset = new Vec3(pos.getX() + random.nextFloat(), pos.getY(), pos.getZ() + random.nextFloat());
                level.addParticle(ParticleRegistry.CHERRY_TYPE.get(), offset.x, offset.y, offset.z, 0, 0, 0);
            }
        }
        super.animateTick(state, level, pos, random);
    }
}
