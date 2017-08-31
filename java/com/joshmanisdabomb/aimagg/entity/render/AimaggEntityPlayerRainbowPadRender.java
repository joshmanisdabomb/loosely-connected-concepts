package com.joshmanisdabomb.aimagg.entity.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowPad;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowPad.RainbowPadType;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class AimaggEntityPlayerRainbowPadRender extends RenderLivingBase<AbstractClientPlayer> {

	public static AimaggEntityPlayerRainbowPadRender instance;

	public final boolean smallArms;
	
	public static AimaggEntityPlayerRainbowPadRender getInstance(RenderManager renderManagerIn, boolean useSmallArms) {
        if (instance != null) {return instance;}
        return instance = new AimaggEntityPlayerRainbowPadRender(renderManagerIn, useSmallArms);
	}
	
	@Override
	protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
	
	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
            y -= entity.isSneaking() ? 0.125D : 0.0D;

            this.setModelVisibilities(entity);
            GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
            GL11.glColor4f(2.0F, 0.75F, 2.0F, 1.0F);
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        }
    }
	
	private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
		ModelPlayer modelplayer = (ModelPlayer) this.getMainModel();

		if (clientPlayer.isSpectator()) {
			modelplayer.setVisible(false);
			modelplayer.bipedHead.showModel = true;
			modelplayer.bipedHeadwear.showModel = true;
		} else {
			ItemStack itemstack = clientPlayer.getHeldItemMainhand();
			ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
			modelplayer.setVisible(true);
			modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
			modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
			modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
			modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
			modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
			modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
			modelplayer.isSneak = clientPlayer.isSneaking();
			ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
			ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

			if (!itemstack.isEmpty()) {
				modelbiped$armpose = ModelBiped.ArmPose.ITEM;

				if (clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction = itemstack.getItemUseAction();

					if (enumaction == EnumAction.BLOCK) {
						modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
					} else if (enumaction == EnumAction.BOW) {
						modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (!itemstack1.isEmpty()) {
				modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

				if (clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction1 = itemstack1.getItemUseAction();

					if (enumaction1 == EnumAction.BLOCK) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
					}
					// FORGE: fix MC-88356 allow offhand to use bow and arrow
					// animation
					else if (enumaction1 == EnumAction.BOW) {
						modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
				modelplayer.rightArmPose = modelbiped$armpose;
				modelplayer.leftArmPose = modelbiped$armpose1;
			} else {
				modelplayer.rightArmPose = modelbiped$armpose1;
				modelplayer.leftArmPose = modelbiped$armpose;
			}
		}
	}
	
	public AimaggEntityPlayerRainbowPadRender(RenderManager renderManagerIn, boolean useSmallArms) {
		super(renderManagerIn, new ModelPlayer(0.0F, useSmallArms), 0.5F);
		this.smallArms = useSmallArms;
	}

	@Override
	public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
        return entity.getLocationSkin();
    }

	public static boolean isOnActivePad(Entity entity) {
		return entity.getEntityWorld().getBlockState(new BlockPos(entity.posX, entity.posY-0.75d, entity.posZ)) == AimaggBlocks.rainbowPad.getDefaultState().withProperty(AimaggBlockRainbowPad.TYPE, RainbowPadType.ACTIVE);
	}

}
