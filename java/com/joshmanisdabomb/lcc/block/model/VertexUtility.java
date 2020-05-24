package com.joshmanisdabomb.lcc.block.model;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    private static void putVertex(BakedQuadBuilder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
        for (int e = 0; e < DefaultVertexFormats.BLOCK.getElements().size(); e++) {
            switch (DefaultVertexFormats.BLOCK.getElements().get(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float)x, (float)y, (float)z, 1f);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case NORMAL:
                    builder.put(e, (float)normal.x, (float)normal.y, (float)normal.z, 1f);
                    break;
                case UV:
                    u = sprite.getInterpolatedU(u);
                    v = sprite.getInterpolatedV(v);
                    builder.put(e, u, v);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

    public static BakedQuad createQuad(double[] vertices, TextureAtlasSprite sprite, Direction side, int uvX1, int uvY1, int uvX2, int uvY2) {
        Vec3d normal = new Vec3d(vertices[6], vertices[7], vertices[8]).subtract(new Vec3d(vertices[3], vertices[4], vertices[5])).crossProduct(new Vec3d(vertices[0], vertices[1], vertices[2]).subtract(new Vec3d(vertices[3], vertices[4], vertices[5]))).normalize();

        BakedQuadBuilder builder = new BakedQuadBuilder();
        putVertex(builder, normal, vertices[0], vertices[1], vertices[2], uvX1, uvY1, sprite);
        putVertex(builder, normal, vertices[3], vertices[4], vertices[5], uvX1, uvY2, sprite);
        putVertex(builder, normal, vertices[6], vertices[7], vertices[8], uvX2, uvY2, sprite);
        putVertex(builder, normal, vertices[9], vertices[10], vertices[11], uvX2, uvY1, sprite);
        builder.setTexture(sprite);
        builder.setQuadOrientation(side);
        builder.setApplyDiffuseLighting(true);
        BakedQuad quad = builder.build();

        //Workaround for lighting because I just don't know I hate it. :)
        for (int i = 6; i < 32; i += 8) {
            quad.getVertexData()[i] = 0;
        }

        return quad;
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

        return createQuad(vertices, sprite, side, uvX1, uvY1, uvX2, uvY2);
    }

    public static List<BakedQuad> rotateQuads(List<BakedQuad> quads, Rotation x, Rotation y, Rotation z) {
        ArrayList<BakedQuad> newQuads = new ArrayList<>();
        for (BakedQuad q : quads) {
            int[] data = Arrays.copyOf(q.getVertexData(), q.getVertexData().length);

            Direction face = q.getFace();
            Vector3f v = new Vector3f(face.getDirectionVec().getX(), face.getDirectionVec().getY(), face.getDirectionVec().getZ());
            v.transform(Vector3f.XN.rotationDegrees(x.ordinal() * 90));
            v.transform(Vector3f.YN.rotationDegrees(y.ordinal() * 90));
            v.transform(Vector3f.ZN.rotationDegrees(z.ordinal() * 90));
            v.normalize();
            face = Direction.getFacingFromVector(v.getX(), v.getY(), v.getZ());

            for (int i = 0; i < data.length; i += 8) {
                Vector3f v2 = new Vector3f(Float.intBitsToFloat(data[i]), Float.intBitsToFloat(data[i + 1]), Float.intBitsToFloat(data[i + 2]));
                v2.add(-0.5F, -0.5F, -0.5F);
                v2.transform(Vector3f.XN.rotationDegrees(x.ordinal() * 90));
                v2.transform(Vector3f.YN.rotationDegrees(y.ordinal() * 90));
                v2.transform(Vector3f.ZN.rotationDegrees(z.ordinal() * 90));
                v2.add(0.5F, 0.5F, 0.5F);
                data[i] = Float.floatToIntBits(v2.getX());
                data[i+1] = Float.floatToIntBits(v2.getY());
                data[i+2] = Float.floatToIntBits(v2.getZ());
            }
            newQuads.add(new BakedQuad(data, q.getTintIndex(), face, q.func_187508_a(), q.shouldApplyDiffuseLighting()));
        }
        return newQuads;
    }

    public static List<BakedQuad> rotateQuads(List<BakedQuad> quads, Direction facing) {
        return VertexUtility.rotateQuads(quads, Rotation.NONE, Rotation.values()[(facing.getHorizontalIndex() + 2) % 4], Rotation.NONE);
    }

}
