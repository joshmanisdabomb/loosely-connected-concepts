package com.joshmanisdabomb.aimagg.gui;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class AimaggOverlayHearts extends Gui {

	private long lastSystemTime;
	
	//TODO moving hearts

	public void draw(EntityPlayer player, Minecraft minecraft, RenderGameOverlayEvent.Post event) {
        if (!player.isSpectator() && !player.isCreative()) {
			minecraft.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID + ":textures/gui/icons.png"));
	        
	        IHearts hearts = player.getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
	        
	        int originalHeartX = event.getResolution().getScaledWidth() / 2 - 91;
	        int originalHeartY = event.getResolution().getScaledHeight() - 39;
	        
	        int originalHeartRows = MathHelper.ceil((player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() + player.getAbsorptionAmount()) / 20.0F);
	        int originalHeartSeparation = Math.max(12 - originalHeartRows, 3);
	        
	        int heartColour = player.isPotionActive(MobEffects.POISON) ? 36 : player.isPotionActive(MobEffects.WITHER) ? 72 : 0;
	        
	        int crystalHeartRowY = originalHeartY - (originalHeartSeparation * (originalHeartRows - 1)) - (player.getTotalArmorValue() > 0 ? 20 : 10);
	
	        int ironHearts = MathHelper.ceil(hearts.getIronHealth());
	        int crystalHearts = MathHelper.ceil(hearts.getCrystalHealth());
	        int crystalHeartContainers = MathHelper.ceil(hearts.getCrystalMaxHealth());
	        
	        for (float i = crystalHeartContainers; i > 0; i -= (i % 2 == 1) ? 1 : 2) {
	        	if (i % 2 == 1) {
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), crystalHeartRowY, 0 + 36, 9, 9, 9);
	        	} else {
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), crystalHeartRowY, 0, 9, 9, 9);
	        	}
	        }
	        
	        for (float i = crystalHearts; i > 0; i -= (i % 2 == 1) ? 1 : 2) {
	        	if (i % 2 == 1) {
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), crystalHeartRowY, 0 + 72 + 9 + heartColour, 9, 9, 9);
	        	} else {
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), crystalHeartRowY, 0 + 72 + heartColour, 9, 9, 9);
	        	}
	        }
	        
	        int ironHeartRowY = crystalHeartRowY - (crystalHeartContainers > 0 ? 10 : 0);
	        
	        for (float i = ironHearts; i > 0; i -= (i % 2 == 1) ? 1 : 2) {
	        	if (i % 2 == 1) {
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), ironHeartRowY, 0 + 36, 0, 9, 9);
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), ironHeartRowY, 0 + 72 + 9 + heartColour, 0, 9, 9);
	        	} else {
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), ironHeartRowY, 0, 0, 9, 9);
	        		this.drawTexturedModalRect(originalHeartX + (MathHelper.floor(MathHelper.ceil(i / 2) - 1) * 8), ironHeartRowY, 0 + 72 + heartColour, 0, 9, 9);
	        	}
	        }
	        
	        minecraft.getTextureManager().bindTexture(ICONS);
        }
	}

}
