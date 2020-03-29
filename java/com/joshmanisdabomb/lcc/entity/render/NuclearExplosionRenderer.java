package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import com.joshmanisdabomb.lcc.registry.LCCModels;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class NuclearExplosionRenderer extends EntityRenderer<NuclearExplosionEntity> {

    private static final ResourceLocation NUCLEAR_EXPLOSION_TEXTURES = new ResourceLocation(LCC.MODID, "textures/entity/nuke.png");
    protected IBakedModel model = null;

    public NuclearExplosionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0F;
        this.shadowOpaque = 0F;
    }

    @Override
    public boolean shouldRender(NuclearExplosionEntity entity, ClippingHelperImpl camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(NuclearExplosionEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (model == null) model = LCCModels.getBakedModel(LCCModels.SPHERE);

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)entity.getPosX(), (float)entity.getPosY(), (float)entity.getPosZ());
        double s = entity.getTicks() * 4;
        GlStateManager.scaled(s, s, s);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();

        //GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        GL13.glMultiTexCoord2f(GL13.GL_TEXTURE0, (float)j, (float)k);

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(
            Minecraft.getInstance().world,
            model,
            Blocks.COBBLESTONE.getDefaultState(),
            BlockPos.ZERO,
            matrixStackIn,
            buffer,
        false, new Random(), 0, 0
        );
        t.draw();

        AbstractClientPlayerEntity player = Minecraft.getInstance().player;
        int a = WorldRenderer.getCombinedLight(Minecraft.getInstance().world, new BlockPos(player.getPosX(), player.getPosY() + (double)player.getEyeHeight(), player.getPosZ()));
        float f = (float)(a & '\uffff');
        float f1 = (float)(a >> 16);
        GL13.glMultiTexCoord2f(GL13.GL_TEXTURE0, f, f1);
        //GlStateManager.activeTexture(GLX.GL_TEXTURE0);

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(NuclearExplosionEntity entity) {
        return NUCLEAR_EXPLOSION_TEXTURES;
    }

}