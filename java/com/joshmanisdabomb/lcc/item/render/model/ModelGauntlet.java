package com.joshmanisdabomb.lcc.item.render.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelGauntlet extends ModelBase {

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

    public ModelGauntlet() {
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

    public void render(ItemStack is) {
        baseBack.render(0.0625F);
        baseFront.render(0.0625F);
        support1.render(0.0625F);
        support2.render(0.0625F);
        fistBase.render(0.0625F);
        fistFingers.render(0.0625F);

        mainGem.render(0.0625F);
        subGem1.render(0.0625F);
        subGem2.render(0.0625F);
        subGem3.render(0.0625F);
        subGem4.render(0.0625F);
        subGem5.render(0.0625F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        /*baseBack.render(f5);
        baseFront.render(f5);
        support1.render(f5);
        support2.render(f5);
        fistBase.render(f5);
        fistFingers.render(f5);

        mainGem.render(f5);
        subGem1.render(f5);
        subGem2.render(f5);
        subGem3.render(f5);
        subGem4.render(f5);
        subGem5.render(f5);*/
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

    }

}