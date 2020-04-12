package com.joshmanisdabomb.lcc.entity.render;

import com.joshmanisdabomb.lcc.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.functionality.GauntletFunctionality;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.HandSide;

public class GauntletPlayerRenderer extends PlayerRenderer {

    public GauntletPlayerRenderer(EntityRendererManager rm) {
        this(rm, false);
    }

    public GauntletPlayerRenderer(EntityRendererManager rm, boolean useSmallArms) {
        super(rm, useSmallArms);
    }

    @Override
    public void render(AbstractClientPlayerEntity player, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        this.entityModel.bipedRightArm.rotateAngleX = 1.4F;
        this.entityModel.bipedRightArm.rotateAngleY = 1.4F;
        this.entityModel.bipedRightArm.rotateAngleZ = 1.4F;
        super.render(player, entityYaw, partialTicks, matrix, buffer, light);
    }

    @Override
    protected void applyRotations(AbstractClientPlayerEntity player, MatrixStack matrix, float age, float rotationYaw, float partialTicks) {
        super.applyRotations(player, matrix, age, rotationYaw, partialTicks);
        player.getCapability(GauntletCapability.Provider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
            if (gauntlet.uppercutDuration > 0) {
                float thrust = (float)Math.pow((gauntlet.uppercutDuration - partialTicks) / GauntletFunctionality.UPPERCUT_MAX_DURATION, 2);
                matrix.rotate(Vector3f.YP.rotationDegrees(thrust * 360 * (player.getPrimaryHand() == HandSide.LEFT ? 1 : -1)));
            }
        });
    }

}
