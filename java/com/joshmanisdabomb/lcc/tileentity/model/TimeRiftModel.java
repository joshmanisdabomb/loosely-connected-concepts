package com.joshmanisdabomb.lcc.tileentity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TimeRiftModel extends Model {

    public static final int TIME_RIFT_PARTS = 100;
    public static final int MAX_SIZE = 4;

    public int frameLastChanged = 0;

    private final ModelRenderer[] parts = new ModelRenderer[TIME_RIFT_PARTS];
    private final Vec3d[] positions = new Vec3d[TIME_RIFT_PARTS];
    private final Vec3d[] motions = new Vec3d[TIME_RIFT_PARTS];
    private final Vec3d[] colors = new Vec3d[TIME_RIFT_PARTS];

    private final Random rand = new Random();

    public TimeRiftModel() {
        super(rl -> RenderType.getEntityAlpha(rl, 0.0F));

        textureWidth = 64;
        textureHeight = 32;

        for (int i = 0; i < parts.length; i++) {
            int size = (i % (MAX_SIZE - 1)) + 1;
            parts[i] = new ModelRenderer(this);
            parts[i].setRotationPoint(-size/2F, -size/2F, -size/2F);
            parts[i].addBox(0.0F, 0.0F, 0.0F, size, size, size);
            positions[i] = Vec3d.ZERO;
            motions[i] = this.moveAmount();
            colors[i] = Vec3d.ZERO;
        }
    }

    @Override
    public void render(MatrixStack matrix, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //TODO vsync too fast, the frame last changed code not working
        if (frameLastChanged != Minecraft.getInstance().getFrameTimer().getIndex()) {
            frameLastChanged = Minecraft.getInstance().getFrameTimer().getIndex();

            for (int i = 0; i < parts.length; i++) {
                this.move(parts[i], i, Minecraft.getInstance().getRenderPartialTicks());
            }
        }
        for (int i = 0; i < parts.length; i++) {
            matrix.push();
            matrix.translate(positions[i].x, positions[i].y, positions[i].z);
            parts[i].render(matrix, buffer, packedLight, packedOverlay, (float)colors[i].x, (float)colors[i].y, (float)colors[i].z, 1);
            matrix.pop();
        }
    }

    private void move(ModelRenderer part, int key, float partialTicks) {
        int size = (key % (MAX_SIZE - 1)) + 1;
        float bound = 0.5F - (size/16F);
        positions[key] = positions[key].add(motions[key].getX() * partialTicks, motions[key].getY() * partialTicks, motions[key].getZ() * partialTicks);
        motions[key] = motions[key].mul(1.0F - (0.03F * partialTicks), 1.0F - (0.03F * partialTicks), 1.0F - (0.03F * partialTicks));
        double distance = Math.min(Math.pow(MathHelper.absMax(MathHelper.absMax(positions[key].x, positions[key].y), positions[key].z) * 2, 5) * 2, 1);
        colors[key] = Vec3d.ZERO.add(0,distance,distance);
        if (positions[key].x > bound || positions[key].x < -bound || positions[key].y > bound || positions[key].y < -bound || positions[key].z > bound || positions[key].z < -bound) {
            positions[key] = Vec3d.ZERO;
            motions[key] = Vec3d.ZERO.add(this.moveAmount());
        }
    }

    private Vec3d moveAmount() {
        List<Double> d = Arrays.asList(((rand.nextDouble() * 0.02) + 0.02) * (rand.nextBoolean() ? 1 : -1), ((rand.nextDouble() * 0.04) - 0.02), ((rand.nextDouble() * 0.04) - 0.02));
        Collections.shuffle(d);
        return new Vec3d(d.get(0), d.get(1), d.get(2));
    }

}