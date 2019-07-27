package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import com.joshmanisdabomb.lcc.entity.model.NuclearExplosionModel;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NuclearExplosionRenderer extends EntityRenderer<NuclearExplosionEntity> {

    private static final ResourceLocation NUCLEAR_EXPLOSION_TEXTURES = new ResourceLocation(LCC.MODID, "textures/entity/nuke.png");
    protected final NuclearExplosionModel model = new NuclearExplosionModel();

    public NuclearExplosionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0F;
        this.shadowOpaque = 0F;
    }

    @Override
    public boolean shouldRender(NuclearExplosionEntity entity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void doRender(NuclearExplosionEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x, (float)y, (float)z);
        GlStateManager.scalef(entity.getTicks(), entity.getTicks(), entity.getTicks());
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.ZERO);
        this.bindEntityTexture(entity);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
        }
        this.model.render(entity, partialTicks, 0.0F, entity.getTicks(), 0.0F, 0.0F, 0.025f);
        if (this.renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NuclearExplosionEntity entity) {
        return NUCLEAR_EXPLOSION_TEXTURES;
    }

}