package com.joshmanisdabomb.lcc.item.render.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.item.ItemStack;

public class GauntletModel extends Model {

    private final RendererModel baseBack;
    private final RendererModel baseFront;
    private final RendererModel support1;
    private final RendererModel support2;
    private final RendererModel fistBase;
    private final RendererModel fistFingers;

    private final RendererModel mainGem;
    private final RendererModel subGem1;
    private final RendererModel subGem2;
    private final RendererModel subGem3;
    private final RendererModel subGem4;
    private final RendererModel subGem5;

    public GauntletModel() {
        textureWidth = 32;
        textureHeight = 64;

        baseBack = new RendererModel(this, 0, 0);
        baseBack.addBox(-3.0F, -15.0F, 1.0F, 6, 3, 2, 0.0F, false);
        baseFront = new RendererModel(this, 0, 5);
        baseFront.addBox(-3.0F, -15.0F, -3.0F, 6, 6, 4, 0.0F, false);
        support1 = new RendererModel(this, 0, 15);
        support1.addBox(-3.0F, -9.0F, -1.0F, 1, 4, 1, 0.0F, false);
        support2 = new RendererModel(this, 4, 15);
        support2.addBox(2.0F, -9.0F, -1.0F, 1, 4, 1, 0.0F, false);
        fistBase = new RendererModel(this, 0, 20);
        fistBase.addBox(-3.0F, -5.0F, -3.0F, 6, 4, 6, 0.0F, false);
        fistFingers = new RendererModel(this, 0, 30);
        fistFingers.addBox(-3.0F, -1.0F, -2.0F, 6, 1, 5, 0.0F, false);

        mainGem = new RendererModel(this, 0, 36);
        mainGem.addBox(-4.0F, -4.0F, -1.0F, 1, 2, 2, 0.0F, false);
        subGem1 = new RendererModel(this, 0, 40);
        subGem1.addBox( -4.0F, -1.0F, 1.0F, 1, 1, 1, 0.0F, false);
        subGem2 = new RendererModel(this, 6, 40);
        subGem2.addBox(-4.0F, -2.0F, -3.0F, 1, 1, 1, 0.0F, false);
        subGem3 = new RendererModel(this, 12, 40);
        subGem3.addBox(-4.0F, -1.0F, -2.0F, 1, 1, 1, 0.0F, false);
        subGem4 = new RendererModel(this, 18, 40);
        subGem4.addBox(-4.0F, -1.0F, -1.0F, 1, 1, 1, 0.0F, false);
        subGem5 = new RendererModel(this, 24, 40);
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

}