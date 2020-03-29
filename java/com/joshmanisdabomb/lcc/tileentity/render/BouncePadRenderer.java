package com.joshmanisdabomb.lcc.tileentity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class BouncePadRenderer extends TileEntityRenderer<BouncePadTileEntity>  {

    private final ModelRenderer[] bouncers = new ModelRenderer[9];
    public static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/tile/bounce_pad.png");

    public BouncePadRenderer(TileEntityRendererDispatcher terd) {
        super(terd);

        for (int i = 0; i < bouncers.length; i++) {
            bouncers[i] = new ModelRenderer(64, 32, 0, 0);
            bouncers[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            bouncers[i].rotateAngleX = (float)Math.PI;
            bouncers[i].addBox(-7.0F, i, -7.0F, 14, 10 - i, 14);
        }
    }

    @Override
    public void render(BouncePadTileEntity te, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        /*matrixStack.push();
        int bouncer = te.getExtensionModel();
        float offset = bouncer == 0 ? 0 : te.getExtensionOffset();

        bouncers[bouncer].offsetY += offset;
        bouncers[bouncer].render(scale);
        bouncers[bouncer].offsetY -= offset;
        Material material = this.getMaterial(tileEntityIn, chesttype);*/
    }

}