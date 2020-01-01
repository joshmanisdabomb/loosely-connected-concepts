package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.block.AtomicBombBlock;
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity;
import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
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
    @AdaptedFromSource
    public void doRender(AtomicBombEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x, (float)y + 0.5F, (float)z);
        if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scalef(f1, f1, f1);
        }

        float f2 = (1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entity);
        GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
        this.renderBlock(entity, blockrendererdispatcher, entity.facing);
        GlStateManager.translatef(-entity.facing.getXOffset(), 0.0F, -entity.facing.getZOffset());
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
            GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.renderBlock(entity, blockrendererdispatcher, entity.facing);
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        } else if (entity.isActive() && entity.getFuse() / 5 % 2 == 0) {
            GlStateManager.disableTexture();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.polygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.renderBlock(entity, blockrendererdispatcher, entity.facing);
            GlStateManager.polygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture();
        }

        GlStateManager.popMatrix();
        if (!this.renderOutlines) {
            this.renderName(entity, x, y, z);
        }
    }

    protected ResourceLocation getEntityTexture(AtomicBombEntity p_110775_1_) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    private void renderBlock(AtomicBombEntity entity, BlockRendererDispatcher blockrendererdispatcher, Direction facing) {
        blockrendererdispatcher.renderBlockBrightness(LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.MIDDLE).with(AtomicBombBlock.FACING, facing), entity.getBrightness());
        GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(-facing.getZOffset(), 0.0F, facing.getXOffset());
        blockrendererdispatcher.renderBlockBrightness(LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.BACK).with(AtomicBombBlock.FACING, facing), entity.getBrightness());
        GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(2.0F * facing.getZOffset(), 0.0F, -2.0F * facing.getXOffset());
        blockrendererdispatcher.renderBlockBrightness(LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.FRONT).with(AtomicBombBlock.FACING, facing), entity.getBrightness());
    }

}
