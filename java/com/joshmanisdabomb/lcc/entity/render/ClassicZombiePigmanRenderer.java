package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.ClassicZombiePigmanEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class ClassicZombiePigmanRenderer extends BipedRenderer<ClassicZombiePigmanEntity, BipedModel<ClassicZombiePigmanEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(LCC.MODID, "textures/entity/nostalgia/zombie_pigman.png");

    public ClassicZombiePigmanRenderer(EntityRendererManager p_i46168_1_) {
        super(p_i46168_1_, new BipedModel<>(RenderType::getEntityCutoutNoCull, 0.0F, 0.0F, 64, 64), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(ClassicZombiePigmanEntity p_110775_1_) {
        return TEXTURE;
    }

}
