package com.joshmanisdabomb.aimagg.entity.render;

import javax.annotation.Nonnull;

import com.joshmanisdabomb.aimagg.entity.missile.AimaggEntityMissile;
import com.joshmanisdabomb.aimagg.entity.missile.AimaggEntityMissileExplosive;
import com.joshmanisdabomb.aimagg.entity.model.AimaggEntityMissileSmallModel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class AimaggEntityMissileSmallRender extends Render<AimaggEntityMissile> {

    private ResourceLocation mobTexture;

    private AimaggEntityMissileSmallModel missileModel = new AimaggEntityMissileSmallModel();
    
    public static final Factory FACTORYEXPLOSIVE = new Factory();

    public AimaggEntityMissileSmallRender(RenderManager rendermanagerIn) {
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

        this.missileModel.render(entity, 0.0F, 0.0F, entity.ticksExisted, entity.rotationYaw, entity.rotationPitch, 1.0F);

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
            return new AimaggEntityMissileSmallRender(manager);
        }

    }

}