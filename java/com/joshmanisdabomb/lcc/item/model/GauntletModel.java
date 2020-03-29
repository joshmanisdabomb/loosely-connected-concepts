package com.joshmanisdabomb.lcc.item.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GauntletModel extends Model {

    private final ModelRenderer baseBack;
    private final ModelRenderer baseFront;
    private final ModelRenderer support1;
    private final ModelRenderer support2;
    private final ModelRenderer fistBase;
    private final ModelRenderer fistFingers;

    private final ModelRenderer mainGem;
    private final ModelRenderer subGem1;
    private final ModelRenderer subGem2;
    private final ModelRenderer subGem3;
    private final ModelRenderer subGem4;
    private final ModelRenderer subGem5;

    public GauntletModel() {
        super(RenderType::getEntitySolid);

        textureWidth = 32;
        textureHeight = 64;

        baseBack = new ModelRenderer(this, 0, 0);
        baseBack.addBox(-3.0F, -15.0F, 1.0F, 6, 3, 2, 0.0F, false);
        baseFront = new ModelRenderer(this, 0, 5);
        baseFront.addBox(-3.0F, -15.0F, -3.0F, 6, 6, 4, 0.0F, false);
        support1 = new ModelRenderer(this, 0, 15);
        support1.addBox(-3.0F, -9.0F, -1.0F, 1, 4, 1, 0.0F, false);
        support2 = new ModelRenderer(this, 4, 15);
        support2.addBox(2.0F, -9.0F, -1.0F, 1, 4, 1, 0.0F, false);
        fistBase = new ModelRenderer(this, 0, 20);
        fistBase.addBox(-3.0F, -5.0F, -3.0F, 6, 4, 6, 0.0F, false);
        fistFingers = new ModelRenderer(this, 0, 30);
        fistFingers.addBox(-3.0F, -1.0F, -2.0F, 6, 1, 5, 0.0F, false);

        mainGem = new ModelRenderer(this, 0, 36);
        mainGem.addBox(-4.0F, -4.0F, -1.0F, 1, 2, 2, 0.0F, false);
        subGem1 = new ModelRenderer(this, 0, 40);
        subGem1.addBox( -4.0F, -1.0F, 1.0F, 1, 1, 1, 0.0F, false);
        subGem2 = new ModelRenderer(this, 6, 40);
        subGem2.addBox(-4.0F, -2.0F, -3.0F, 1, 1, 1, 0.0F, false);
        subGem3 = new ModelRenderer(this, 12, 40);
        subGem3.addBox(-4.0F, -1.0F, -2.0F, 1, 1, 1, 0.0F, false);
        subGem4 = new ModelRenderer(this, 18, 40);
        subGem4.addBox(-4.0F, -1.0F, -1.0F, 1, 1, 1, 0.0F, false);
        subGem5 = new ModelRenderer(this, 24, 40);
        subGem5.addBox(-4.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        baseBack.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        baseFront.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        support1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        support2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        fistBase.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        fistFingers.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        mainGem.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        subGem1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        subGem2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        subGem3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        subGem4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        subGem5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

}