package com.joshmanisdabomb.lcc.tileentity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class TimeRiftModel extends Model {

    public static final int TIME_RIFT_PARTS = 100;
    public static final int MAX_SIZE = 4;

    public int frameLastChanged = 0;

    private final RendererModel[] parts = new RendererModel[TIME_RIFT_PARTS];
    private final Vec3d[] motions = new Vec3d[TIME_RIFT_PARTS];
    private final Vec3d[] colors = new Vec3d[TIME_RIFT_PARTS];

    private final Random rand = new Random();

    public TimeRiftModel() {
        textureWidth = 64;
        textureHeight = 32;

        for (int i = 0; i < parts.length; i++) {
            int size = (i % (MAX_SIZE - 1)) + 1;
            parts[i] = new RendererModel(this);
            parts[i].setRotationPoint(-size/2F, -size/2F, -size/2F);
            parts[i].addBox(0.0F, 0.0F, 0.0F, size, size, size);
            motions[i] = this.moveAmount();
            colors[i] = new Vec3d(0, 0, 0);
        }
    }

    public void render(float scale, float partialTicks) {
        if (frameLastChanged != Minecraft.getInstance().getFrameTimer().getIndex()) {
            frameLastChanged = Minecraft.getInstance().getFrameTimer().getIndex();

            for (int i = 0; i < parts.length; i++) {
                this.move(parts[i], i, partialTicks);
            }
        }
        GlStateManager.disableCull();
        GlStateManager.disableTexture();
        GlStateManager.disableLighting();
        GlStateManager.disableColorMaterial();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        for (int i = 0; i < parts.length; i++) {
            GlStateManager.color4f((float)colors[i].x, (float)colors[i].y, (float)colors[i].z, 1);
            parts[i].render(scale);
        }
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture();
        GlStateManager.enableCull();
        GlStateManager.color4f(1, 1, 1, 1);
    }

    private void move(RendererModel part, int key, float partialTicks) {
        int size = (key % (MAX_SIZE - 1)) + 1;
        float bound = 0.5F - (size/16F);
        part.offsetX += motions[key].getX() * partialTicks;
        part.offsetY += motions[key].getY() * partialTicks;
        part.offsetZ += motions[key].getZ() * partialTicks;
        motions[key] = motions[key].mul(1.0F - (0.02F * partialTicks), 1.0F - (0.02F * partialTicks), 1.0F - (0.02F * partialTicks));
        double distance = Math.pow(MathHelper.absMax(MathHelper.absMax(part.offsetX, part.offsetY), part.offsetZ) * 2, 3);
        colors[key] = colors[key].mul(0,0,0).add(0,distance,distance);
        if (part.offsetX > bound || part.offsetX < -bound || part.offsetY > bound || part.offsetY < -bound || part.offsetZ > bound || part.offsetZ < -bound) {
            part.offsetX = part.offsetY = part.offsetZ = 0;
            motions[key] = motions[key].mul(0, 0, 0).add(this.moveAmount());
        }
    }

    private Vec3d moveAmount() {
        List<Double> d = Arrays.asList(((rand.nextDouble() * 0.02) + 0.03) * (rand.nextBoolean() ? 1 : -1), ((rand.nextDouble() * 0.08) - 0.04), ((rand.nextDouble() * 0.08) - 0.04));
        Collections.shuffle(d);
        return new Vec3d(d.get(0), d.get(1), d.get(2));
    }

}