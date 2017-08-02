package com.joshmanisdabomb.aimagg.entity.render;

import javax.annotation.Nonnull;

import com.joshmanisdabomb.aimagg.data.MissileType.ModelType;
import com.joshmanisdabomb.aimagg.entity.AimaggEntityMissile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class AimaggEntityMissileRender extends Render<AimaggEntityMissile> {
	
    private ResourceLocation mobTexture;

    private ModelBase[] models = ModelType.getEntityModels();
    
    public static final Factory FACTORYEXPLOSIVE = new Factory();

    public AimaggEntityMissileRender(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull AimaggEntityMissile entity) {
        return entity.getEntityTexturePath();
    }
    
    @Override
    public void doRender(AimaggEntityMissile entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        float f = 0.0625F;
        GlStateManager.scale(f, f, f);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.models[entity.getMissileType().getModelType().ordinal()].render(entity, 0.0F, 0.0F, entity.ticksExisted, entity.rotationYaw, entity.rotationPitch, 1.0F);
        	
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    public static class Factory implements IRenderFactory<AimaggEntityMissile> {
    	
		@Override
        public Render<? super AimaggEntityMissile> createRenderFor(RenderManager manager) {
            return new AimaggEntityMissileRender(manager);
        }

    }

}