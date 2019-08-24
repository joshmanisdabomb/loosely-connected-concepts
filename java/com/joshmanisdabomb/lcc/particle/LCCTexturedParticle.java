package com.joshmanisdabomb.lcc.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.TexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LCCTexturedParticle extends TexturedParticle implements IParticleRenderType {

    public LCCTexturedParticle(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public LCCTexturedParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    public abstract ResourceLocation getTexture();

    @Override
    public void renderParticle(BufferBuilder builder, ActiveRenderInfo info, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        TextureManager textureManager = Minecraft.getInstance().textureManager;
        this.beginRender(builder, textureManager);
        super.renderParticle(builder, info, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        this.finishRender(Tessellator.getInstance());
    }

    @Override
    protected float getMinU() {
        return 0f;
    }

    @Override
    protected float getMaxU() {
        return 1f;
    }

    @Override
    protected float getMinV() {
        return 0f;
    }

    @Override
    protected float getMaxV() {
        return 1f;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }

    @Override
    public void beginRender(BufferBuilder builder, TextureManager manager) {
        RenderHelper.disableStandardItemLighting();
        GlStateManager.depthMask(true);
        manager.bindTexture(this.getTexture());
        GlStateManager.disableBlend();
        builder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }

    @Override
    public void finishRender(Tessellator t) {
        t.draw();
    }

}
