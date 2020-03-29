package com.joshmanisdabomb.lcc.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EmptyRenderer extends EntityRenderer {

    public EmptyRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public void render(Entity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {

    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

}
