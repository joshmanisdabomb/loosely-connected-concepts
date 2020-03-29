package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.block.AtomicBombBlock;
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class AtomicBombRenderer extends EntityRenderer<AtomicBombEntity> {

    public AtomicBombRenderer(EntityRendererManager manager) {
        super(manager);
        this.shadowSize = 1.3F;
    }

    @Override
    public void render(AtomicBombEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scalef(f1, f1, f1);
        }
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
        this.renderBlock(entity, Minecraft.getInstance().getBlockRendererDispatcher(), entity.facing, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(AtomicBombEntity p_110775_1_) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    private void renderBlock(AtomicBombEntity entity, BlockRendererDispatcher blockrendererdispatcher, Direction facing, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        TNTMinecartRenderer.renderTntFlash(LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.MIDDLE).with(AtomicBombBlock.FACING, entity.getHorizontalFacing()), matrixStackIn, bufferIn, packedLightIn, entity.getFuse() / 5 % 2 == 0);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(-facing.getZOffset(), 0.0F, facing.getXOffset());
        TNTMinecartRenderer.renderTntFlash(LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.BACK).with(AtomicBombBlock.FACING, entity.getHorizontalFacing()), matrixStackIn, bufferIn, packedLightIn, entity.getFuse() / 5 % 2 == 0);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(2.0F * facing.getZOffset(), 0.0F, -2.0F * facing.getXOffset());
        TNTMinecartRenderer.renderTntFlash(LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.FRONT).with(AtomicBombBlock.FACING, entity.getHorizontalFacing()), matrixStackIn, bufferIn, packedLightIn, entity.getFuse() / 5 % 2 == 0);
    }

}
