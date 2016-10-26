package com.joshmanisdabomb.aimagg.gui;

import com.joshmanisdabomb.aimagg.container.AimaggContainerSpreaderConstructor;
import com.joshmanisdabomb.aimagg.te.AimaggTESpreaderConstructor;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class AimaggGUISpreaderConstructor extends GuiContainer {

	private InventoryPlayer playerInv;
	private AimaggTESpreaderConstructor te;

	public AimaggGUISpreaderConstructor(EntityPlayer player, AimaggTESpreaderConstructor tileEntity) {
		super(new AimaggContainerSpreaderConstructor(player, tileEntity));
		
		this.playerInv = player.inventory;
		this.te = tileEntity;
		
		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("aimagg:textures/gui/container/spreaderconstructor.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	    this.fontRendererObj.drawString(new TextComponentTranslation(this.te.getBaseName() + ".modifiers", new Object[0]).getUnformattedText(), 8, 72, 4210752);
	    this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 127, 4210752);
	}

}
