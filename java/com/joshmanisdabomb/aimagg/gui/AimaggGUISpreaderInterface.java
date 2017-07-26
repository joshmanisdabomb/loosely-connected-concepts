package com.joshmanisdabomb.aimagg.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Mouse;

import com.joshmanisdabomb.aimagg.container.AimaggContainerSpreaderInterface;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AimaggGUISpreaderInterface extends GuiContainer {

	private InventoryPlayer playerInv;
	
	private int tab;
	
	public AimaggGUISpreaderInterface(EntityPlayer player, World world) {
		super(new AimaggContainerSpreaderInterface(player, world));
		
		this.playerInv = player.inventory;

		this.tab = 0;
		this.updateSlotPositions();
		
		this.xSize = 176;
		this.ySize = 222;
	}

	private void updateSlotPositions() {
		for (int i = 0; i < 16*8; i++) {
			this.inventorySlots.inventorySlots.get(i).xPos = -10000;
			this.inventorySlots.inventorySlots.get(i).yPos = -10000;
		}
		this.inventorySlots.inventorySlots.get((tab*8)+0).xPos = AimaggContainerSpreaderInterface.defaultSpeedSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+0).yPos = AimaggContainerSpreaderInterface.defaultSpeedSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+1).xPos = AimaggContainerSpreaderInterface.defaultDamageSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+1).yPos = AimaggContainerSpreaderInterface.defaultDamageSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+2).xPos = AimaggContainerSpreaderInterface.defaultRangeSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+2).yPos = AimaggContainerSpreaderInterface.defaultRangeSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+3).xPos = AimaggContainerSpreaderInterface.defaultSpreadSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+3).yPos = AimaggContainerSpreaderInterface.defaultSpreadSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+4).xPos = AimaggContainerSpreaderInterface.defaultEatingSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+4).yPos = AimaggContainerSpreaderInterface.defaultEatingSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+5).xPos = AimaggContainerSpreaderInterface.defaultInGroundSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+5).yPos = AimaggContainerSpreaderInterface.defaultInGroundSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+6).xPos = AimaggContainerSpreaderInterface.defaultInLiquidSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+6).yPos = AimaggContainerSpreaderInterface.defaultInLiquidSlotY;
		this.inventorySlots.inventorySlots.get((tab*8)+7).xPos = AimaggContainerSpreaderInterface.defaultInAirSlotX;
		this.inventorySlots.inventorySlots.get((tab*8)+7).yPos = AimaggContainerSpreaderInterface.defaultInAirSlotY;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("aimagg:textures/gui/container/spreaderinterface.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
		for (int i = 0; i < 16; i++) {
		    this.mc.getTextureManager().bindTexture(new ResourceLocation("aimagg:textures/gui/container/spreaderinterface.png"));
			Color cv = new Color(EnumDyeColor.byMetadata(i).getColorValue());
		    GlStateManager.color((cv.getRed()+25)/230F, (cv.getGreen()+25)/230F, (cv.getBlue()+25)/230F, 1.0f);
		    if (i == tab) {
		    	this.drawTexturedModalRect(this.guiLeft + 8+(i*10), this.guiTop + 15, 186, 0, 10, 13);
		    	this.drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 28, 7, 28, 162, 106);
		    	
		    	int textColor = cv.darker().darker().getRGB();
			    this.fontRenderer.drawString(I18n.format("container.aimagg_spreader_interface.modifiers", new Object[]{}), this.guiLeft + 12, this.guiTop + 31, textColor);
			    this.fontRenderer.drawString(I18n.format("container.aimagg_spreader_interface.spread", new Object[]{}), this.guiLeft + 12, this.guiTop + 102, textColor);
		    } else {
		    	this.drawTexturedModalRect(this.guiLeft + 8+(i*10), this.guiTop + 15, 176, 0, 10, 13);
		    }
		}
		
		if ((Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || Mouse.isButtonDown(2)) && mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 167 && mouseY >= this.guiTop + 15 && mouseY <= this.guiTop + 27) {
			this.tab = (int)Math.floor((mouseX - this.guiLeft - 8) / 10F);
			this.updateSlotPositions();
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    this.fontRenderer.drawString(I18n.format("container.aimagg_spreader_interface", new Object[]{}), 6, 6, 4210752);
	}

}
