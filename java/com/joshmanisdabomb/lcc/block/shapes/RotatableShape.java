package com.joshmanisdabomb.lcc.block.shapes;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class RotatableShape {

    private final VoxelShape[] bakedShapes = new VoxelShape[64];

    public RotatableShape(VoxelShape original) {
        bakedShapes[0] = original;
    }

    public VoxelShape get(Rotation x, Rotation y, Rotation z) {
        int key = x.ordinal() + (y.ordinal() * 4) + (z.ordinal() * 16);
        if (bakedShapes[key] == null) bakedShapes[key] = this.rotateShape(x, y, z);
        return bakedShapes[key];
    }

    public VoxelShape get(Direction d) {
        switch (d) {
            case DOWN:
                return get(Rotation.CLOCKWISE_90, Rotation.NONE, Rotation.NONE);
            case UP:
                return get(Rotation.COUNTERCLOCKWISE_90, Rotation.NONE, Rotation.NONE);
            case SOUTH:
                return get(Rotation.NONE, Rotation.CLOCKWISE_180, Rotation.NONE);
            case WEST:
                return get(Rotation.NONE, Rotation.CLOCKWISE_90, Rotation.NONE);
            case EAST:
                return get(Rotation.NONE, Rotation.COUNTERCLOCKWISE_90, Rotation.NONE);
            default:
                return get(Rotation.NONE, Rotation.NONE, Rotation.NONE);
        }
    }

    public VoxelShape original() {
        return this.get(Rotation.NONE, Rotation.NONE, Rotation.NONE);
    }

    private static double rotateA(double a, double b, double centerA, double centerB, Rotation r) {
        float angle = r.ordinal() * 0.5F * (float)Math.PI;
        return MathHelper.cos(angle) * (a - 0.5) - MathHelper.sin(angle) * (b-0.5) + centerA;
    }

    private static double rotateB(double a, double b, double centerA, double centerB, Rotation r) {
        float angle = r.ordinal() * 0.5F * (float)Math.PI;
        return MathHelper.sin(angle) * (a - 0.5) - MathHelper.cos(angle) * (b-0.5) + centerA;
    }

    private VoxelShape rotateShape(Rotation x, Rotation y, Rotation z) {
        VoxelShape ret = VoxelShapes.empty();
        for (AxisAlignedBB box : bakedShapes[0].toBoundingBoxList()) {
            double minX = box.minX;
            double minY = box.minY;
            double minZ = box.minZ;
            double maxX = box.maxX;
            double maxY = box.maxY;
            double maxZ = box.maxZ;
            //x
            double a = minY, b = minZ, c = maxY, d = maxZ;
            minY = rotateA(a, b, 0.5, 0.5, x);
            minZ = rotateB(a, b, 0.5, 0.5, x);
            maxY = rotateA(c, d, 0.5, 0.5, x);
            maxZ = rotateB(c, d, 0.5, 0.5, x);
            //y
            a = minX; b = minZ; c = maxX; d = maxZ;
            minX = rotateA(a, b, 0.5, 0.5, y);
            minZ = rotateB(a, b, 0.5, 0.5, y);
            maxX = rotateA(c, d, 0.5, 0.5, y);
            maxZ = rotateB(c, d, 0.5, 0.5, y);
            //z
            a = minX; b = minY; c = maxX; d = maxY;
            minX = rotateA(a, b, 0.5, 0.5, z);
            minY = rotateB(a, b, 0.5, 0.5, z);
            maxX = rotateA(c, d, 0.5, 0.5, z);
            maxY = rotateB(c, d, 0.5, 0.5, z);
            ret = VoxelShapes.or(ret, VoxelShapes.create(minX, minY, minZ, maxX, maxY, maxZ));
        }
        return ret;
    }

}
