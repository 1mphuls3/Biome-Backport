package com.myst.biomebackport.core.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class VecHelper {
    public static final Vec3 MIDDLE = new Vec3(.5, 0, .5);
    public static final Vec3 CENTER = new Vec3(.5, .5, .5);

    public static Vec3 vec3FromBlockPos(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static BlockPos blockPosfromVec3(Vec3 pos) {
        return new BlockPos(pos.x(), pos.y(), pos.z());
    }

    public static Vec3 radialOffset(Vec3 pos, float distance, float current, float total) {
        double angle = current / total * (Math.PI * 2);
        double dx2 = (distance * Math.cos(angle));
        double dz2 = (distance * Math.sin(angle));

        Vec3 vector = new Vec3(dx2, 0, dz2);
        double x = vector.x * distance;
        double z = vector.z * distance;
        return pos.add(new Vec3(x, 0, z));
    }
}
