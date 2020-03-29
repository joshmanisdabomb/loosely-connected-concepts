package com.joshmanisdabomb.lcc.item.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.item.model.GauntletModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GauntletRenderer extends ItemStackTileEntityRenderer {

    private final GauntletModel gm = new GauntletModel();
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/gauntlet.png");

    /*@Override
    public void renderByItem(ItemStack is) {
        this.render(is, false);
    }

    public void render(ItemStack is, boolean onHand) {
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        gm.render(is, onHand);
        GlStateManager.popMatrix();
    }*/

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        //super.render(p_228364_1_, p_228364_2_, p_228364_3_, p_228364_4_, p_228364_5_);
    }
}
