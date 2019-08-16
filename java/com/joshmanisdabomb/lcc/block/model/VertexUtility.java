package com.joshmanisdabomb.lcc.block.model;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

import javax.annotation.Nonnull;
import java.util.HashMap;

public abstract class VertexUtility {

    public static final double[] DOWN = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0};
    public static final double[] UP = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0};
    public static final double[] NORTH = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
    public static final double[] SOUTH = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0};
    public static final double[] WEST = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0};
    public static final double[] EAST = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0};
    public static final HashMap<Direction, double[]> DIRECTION_TO_VERTICES = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });
    public static final HashMap<Direction, Direction[]> PERPENDICULARS = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
        map.put(Direction.DOWN, new Direction[]{Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST});
        map.put(Direction.NORTH, new Direction[]{Direction.UP, Direction.WEST, Direction.DOWN, Direction.EAST});
        map.put(Direction.EAST, new Direction[]{Direction.UP, Direction.NORTH, Direction.DOWN, Direction.SOUTH});
        map.put(Direction.SOUTH, new Direction[]{Direction.UP, Direction.EAST, Direction.DOWN, Direction.WEST});
        map.put(Direction.WEST, new Direction[]{Direction.UP, Direction.SOUTH, Direction.DOWN, Direction.NORTH});
    });

    private static void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
        for (int e = 0; e < DefaultVertexFormats.ITEM.getElementCount(); e++) {
            switch (DefaultVertexFormats.ITEM.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float)x, (float)y, (float)z);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    u = sprite.getInterpolatedU(u);
                    v = sprite.getInterpolatedV(v);
                    builder.put(e, u, v);
                    break;
                case NORMAL:
                    builder.put(e, (float)normal.x, (float)normal.y, (float)normal.z);
                    break;
                default:
                    break;
            }
        }
    }

    public static BakedQuad createQuad(double[] vertices, TextureAtlasSprite sprite, int uvX1, int uvY1, int uvX2, int uvY2) {
        Vec3d normal = Vec3d.ZERO;//vertices[2].subtract(vertices[1]).crossProduct(vertices[0].subtract(vertices[1])).normalize();

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(DefaultVertexFormats.BLOCK);
        builder.setTexture(sprite);
        putVertex(builder, normal, vertices[0], vertices[1], vertices[2], uvX1, uvY1, sprite);
        putVertex(builder, normal, vertices[3], vertices[4], vertices[5], uvX1, uvY2, sprite);
        putVertex(builder, normal, vertices[6], vertices[7], vertices[8], uvX2, uvY2, sprite);
        putVertex(builder, normal, vertices[9], vertices[10], vertices[11], uvX2, uvY1, sprite);
        return builder.build();
    }

    public static BakedQuad create2DFace(@Nonnull Direction side, double x1, double y1, double x2, double y2, double z, TextureAtlasSprite sprite, int uvX1, int uvY1, int uvX2, int uvY2) {
        double[] vertices = DIRECTION_TO_VERTICES.get(side).clone();

        //Change face's extension from center of block based on z.
        for (int i = side.getAxis().ordinal(); i < 12; i+=3) vertices[i] = 0.5 + (z*0.5*side.getAxisDirection().getOffset());

        //Fix some bugs that I'm not smart enough for with magical ways
        if (side == Direction.EAST || side == Direction.NORTH) {
            double x = x1;
            x1 = 1-x2;
            x2 = 1-x;
        }
        if (side != Direction.UP) {
            double y = y1;
            y1 = 1-y2;
            y2 = 1-y;
        }

        //Change vertex points based on axis and direction.
        boolean pos = side.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        switch (side.getAxis()) {
            case X:
                vertices[2] = pos ? x2 : x1;
                vertices[5] = pos ? x2 : x1;
                vertices[8] = pos ? x1 : x2;
                vertices[11] = pos ? x1 : x2;
                vertices[1] = y2;
                vertices[4] = y1;
                vertices[7] = y1;
                vertices[10] = y2;
                break;
            case Z:
                vertices[0] = pos ? x1 : x2;
                vertices[3] = pos ? x1 : x2;
                vertices[6] = pos ? x2 : x1;
                vertices[9] = pos ? x2 : x1;
                vertices[1] = y2;
                vertices[4] = y1;
                vertices[7] = y1;
                vertices[10] = y2;
                break;
            default:
                vertices[0] = x1;
                vertices[3] = x1;
                vertices[6] = x2;
                vertices[9] = x2;
                vertices[2] = pos ? y1 : y2;
                vertices[5] = pos ? y2 : y1;
                vertices[8] = pos ? y2 : y1;
                vertices[11] = pos ? y1 : y2;
                break;
        }

        return createQuad(vertices, sprite, uvX1, uvY1, uvX2, uvY2);
    }

}
