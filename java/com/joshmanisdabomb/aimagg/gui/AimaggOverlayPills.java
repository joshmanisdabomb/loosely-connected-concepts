package com.joshmanisdabomb.aimagg.gui;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.IPills;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.PillsProvider;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill.PillEffect;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill.PillType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class AimaggOverlayPills extends Gui {

	public static long pillTakenOnSystemTime;
	public static PillEffect recentEffect;
	
	public static long lastSystemTime;
	
	public int effect1xAddition = 0;
	public int effect1yAddition = 0;
	public int effect2xAddition = 0;
	public int effect2yAddition = 0;
	public int effect3xAddition = 0;
	public int effect3yAddition = 0;
	public int effect4xAddition = 0;
	public int effect4yAddition = 0;
	
	public void draw(EntityPlayer player, Minecraft minecraft, RenderGameOverlayEvent.Post event) {
		IPills pills = player.getCapability(PillsProvider.PILLS_CAPABILITY, null);
		float scale = 3.5F - ((System.currentTimeMillis() - pillTakenOnSystemTime) / 2000F);
		
		float translationX = minecraft.displayWidth / 2 / (float)event.getResolution().getScaleFactor();
		float translationY = minecraft.displayHeight / 2 / (float)event.getResolution().getScaleFactor();
		
		float transparency = 1;
		if (scale > 0) {
			transparency = Math.max(Math.min((scale * 12F) - 30.0F, 1) * 255.0F, 6F);
		} else {
			scale = 0;
		}
		
		if (minecraft.isGamePaused()) {
			pillTakenOnSystemTime += System.currentTimeMillis() - lastSystemTime;
		} else {
			Random rand = new Random();
			int effectAdditionToChange = rand.nextInt(4);
			
			switch(effectAdditionToChange) {
				case 1:
					this.effect2xAddition = rand.nextInt(3);
					this.effect2yAddition = rand.nextInt(3);
					break;
				case 2:
					this.effect3xAddition = rand.nextInt(3);
					this.effect3yAddition = rand.nextInt(3);
					break;
				case 3:
					this.effect4xAddition = rand.nextInt(3);
					this.effect4yAddition = rand.nextInt(3);
					break;
				default:
					this.effect1xAddition = rand.nextInt(3);
					this.effect1yAddition = rand.nextInt(3);
					break;
			}
		}
		
		if ((pills.getLastType() != PillType.NORMAL || (pills.getLastPrimaryColor() != -1 && pills.getLastSecondaryColor() != -1)) && transparency > 6F && scale > 0F) {
			String name = I18n.format("tooltip.pill.effect." + recentEffect.name().toLowerCase(), new Object[]{});
			
			translationX -= (minecraft.fontRenderer.getStringWidth(name) / 2F + 10) * scale;
			translationY -= ((minecraft.fontRenderer.FONT_HEIGHT - 1) / 2F + 10) * scale;

			GL11.glPushMatrix();
			GL11.glTranslatef(translationX, translationY, 1F);
			GL11.glScalef(scale, scale, 1F);
			minecraft.fontRenderer.drawString(name, 11, 11, recentEffect.getPolarity().colorShadow | (int)transparency << 24);
			minecraft.fontRenderer.drawString(name, 10, 10, recentEffect.getPolarity().color | (int)transparency << 24);
			if (scale > 3.25F) minecraft.fontRenderer.drawString(name, 9 + this.effect4xAddition, 9 + this.effect4yAddition, recentEffect.getPolarity().colorEffect4 | (int)transparency << 24);
			if (scale > 3.3F) minecraft.fontRenderer.drawString(name, 9 + this.effect3xAddition, 9 + this.effect3yAddition, recentEffect.getPolarity().colorEffect3 | (int)transparency << 24);
			if (scale > 3.35F) minecraft.fontRenderer.drawString(name, 9 + this.effect2xAddition, 9 + this.effect2yAddition, recentEffect.getPolarity().colorEffect2 | (int)transparency << 24);
			if (scale > 3.4F) minecraft.fontRenderer.drawString(name, 9 + this.effect1xAddition, 9 + this.effect1yAddition, recentEffect.getPolarity().colorEffect1 | (int)transparency << 24);
			GL11.glPopMatrix();
		}
		
		lastSystemTime = System.currentTimeMillis();
	}

}
