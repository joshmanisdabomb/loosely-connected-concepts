package com.joshmanisdabomb.lcc.tileentity.model;

import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BouncePadModel extends Model {

    private final ModelRenderer[] bouncers = new ModelRenderer[9];

    public BouncePadModel() {
        super(RenderType::getEntitySolid);

        textureWidth = 64;
        textureHeight = 32;

        for (int i = 0; i < bouncers.length; i++) {
            bouncers[i] = new ModelRenderer(this);
            bouncers[i].setRotationPoint(0.0F, 8.0F, 0.0F);
            bouncers[i].rotateAngleX = (float)Math.PI;
            bouncers[i].addBox(-7.0F, i, -7.0F, 14, 10 - i, 14);
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        /*int bouncer = te.getExtensionModel();
        float offset = bouncer == 0 ? 0 : te.getExtensionOffset();

        bouncers[bouncer].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);*/
    }

}