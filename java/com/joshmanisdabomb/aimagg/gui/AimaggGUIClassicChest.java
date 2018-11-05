package com.joshmanisdabomb.aimagg.gui;

import java.io.IOException;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockComputerCase;
import com.joshmanisdabomb.aimagg.container.AimaggContainerClassicChest;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketComputerCasePowerServer;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTEClassicChest;
import com.joshmanisdabomb.aimagg.te.AimaggTEComputerCase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class AimaggGUIClassicChest extends GuiContainer {

	private static final int xSizeButton = 26;
	private static final int ySizeButton = 37;
	
	private InventoryPlayer playerInv;
	private AimaggTEClassicChest te;
	private AimaggTEClassicChest teNeighbour;

	public AimaggGUIClassicChest(EntityPlayer player, AimaggTEClassicChest tileEntity) {
		super(new AimaggContainerClassicChest(player, tileEntity));
		
		this.playerInv = player.inventory;
		this.te = tileEntity;
		this.teNeighbour = tileEntity.getNeighbour();

        this.xSize = 176;
        this.ySize = 168 + (this.isDoubleChest() ? 54 : 0);
	}
	
	private boolean isDoubleChest() {
		return this.teNeighbour != null;
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, 71 + (this.isDoubleChest() ? 54 : 0));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 71 + (this.isDoubleChest() ? 54 : 0), 0, 126, this.xSize, 96);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRenderer.drawString(s, 8, 6, 4210752);
	    this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 7, this.ySize - 94, 4210752);
	}

}
