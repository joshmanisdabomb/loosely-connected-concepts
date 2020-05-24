package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class NuclearExplosionRenderer extends EntityRenderer<NuclearExplosionEntity> {

    private static final Random RANDOM = new Random();

    private static final ResourceLocation NUCLEAR_EXPLOSION_TEXTURES = new ResourceLocation(LCC.MODID, "textures/entity/nuke.png");
    protected IBakedModel model = null;

    public NuclearExplosionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0F;
        this.shadowOpaque = 0F;
    }

    @Override
    public boolean shouldRender(NuclearExplosionEntity entity, ClippingHelperImpl camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(NuclearExplosionEntity entity, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int packedLightIn) {
        if (model == null) model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(LCC.MODID, "entity/nuke"));

        matrix.push();

        entity.partialTicks = MathHelper.clamp(entity.partialTicks + (partialTicks * 0.5F), entity.getTicks() - 5, entity.getTicks());
        float s = entity.partialTicks * 4;
        matrix.scale(s, s, s);

        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(
            Minecraft.getInstance().world,
            model,
            LCCBlocks.time_rift.getDefaultState(),
            entity.getPosition(),
            matrix,
            buffer.getBuffer(RenderType.getEntityAlpha(this.getEntityTexture(entity), 0.0F)),
            false, RANDOM, 0, 0
        );

        matrix.pop();

        super.render(entity, entityYaw, partialTicks, matrix, buffer, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(NuclearExplosionEntity entity) {
        return NUCLEAR_EXPLOSION_TEXTURES;
    }

}