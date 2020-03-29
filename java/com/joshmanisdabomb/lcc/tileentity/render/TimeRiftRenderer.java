package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.TimeRiftTileEntity;
import com.joshmanisdabomb.lcc.tileentity.model.TimeRiftModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TimeRiftRenderer extends TileEntityRenderer<TimeRiftTileEntity> {

    protected final TimeRiftModel model = new TimeRiftModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/time_rift.png");

    public TimeRiftRenderer(TileEntityRendererDispatcher terd) {
        super(terd);
    }

    @Override
    public void render(TimeRiftTileEntity te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        GlStateManager.pushMatrix();
        //GlStateManager.translated(x+0.5, y+0.5, z+0.5);
        GlStateManager.enableBlend();

        /*this.bindTexture(TEXTURE);
        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        float size = MathHelper.lerp(partialTicks, te.lastSize, te.size);
        GlStateManager.scalef(size,size,size);
        model.render(0.0625F, partialTicks);
        GlStateManager.activeTexture(GLX.GL_TEXTURE0);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        this.setLightmapDisabled(false);*/
    }

    public static class Item extends ItemStackTileEntityRenderer {

        protected final TimeRiftModel model = new TimeRiftModel();
        public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/time_rift.png");

        @Override
        public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        }

        /*@Override
        public void renderByItem(ItemStack is) {
            Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);

            GlStateManager.pushMatrix();
            GlStateManager.translated(0.5, 0.5, 0.5);

            GlStateManager.scalef(1.0F, 1.0F, 1.0F);
            int i = 15728880;
            int j = i % 65536;
            int k = i / 65536;
            GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
            model.render(0.0625F, Minecraft.getInstance().getRenderPartialTicks());

            AbstractClientPlayerEntity player = Minecraft.getInstance().player;
            int a = Minecraft.getInstance().world.getCombinedLight(new BlockPos(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ), 0);
            float f = (float)(a & '\uffff');
            float f1 = (float)(a >> 16);
            GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, f, f1);

            GlStateManager.popMatrix();

            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }*/

    }

}
