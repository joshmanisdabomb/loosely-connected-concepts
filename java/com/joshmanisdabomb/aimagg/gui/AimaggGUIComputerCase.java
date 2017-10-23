package com.joshmanisdabomb.aimagg.gui;

import java.io.IOException;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockComputerCase;
import com.joshmanisdabomb.aimagg.container.AimaggContainerComputerCase;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketComputerCasePowerServer;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTEComputerCase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class AimaggGUIComputerCase extends GuiContainer {

	private static final int xSizeButton = 26;
	private static final int ySizeButton = 37;
	
	private InventoryPlayer playerInv;
	private AimaggTEComputerCase te;

	public AimaggGUIComputerCase(EntityPlayer player, AimaggTEComputerCase tileEntity) {
		super(new AimaggContainerComputerCase(player, tileEntity));
		
		this.playerInv = player.inventory;
		this.te = tileEntity;

        this.xSize = 176;
		this.ySize = 184;
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID, "textures/gui/container/computer_case.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    if (!this.isOnButtonEnabled()) {
	    	this.drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 16, 78, this.ySize, this.xSizeButton, this.ySizeButton);
	    } else if (mouseX >= this.guiLeft + 75 && mouseX <= this.guiLeft + 75 + this.xSizeButton && mouseY >= this.guiTop + 16 && mouseY <= this.guiTop + 16 + this.ySizeButton) {
	    	this.drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 16, 26 + (this.isOnButtonActive() ? 26 : 0), this.ySize, this.xSizeButton, this.ySizeButton);
	    } else if (this.isOnButtonActive()) {
	    	this.drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 16, 0, this.ySize, this.xSizeButton, this.ySizeButton);
	    }
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRenderer.drawString(s, 8, 6, 4210752);
	    this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 7, 91, 4210752);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isOnButtonEnabled() && mouseX >= this.guiLeft + 75 && mouseX <= this.guiLeft + 75 + this.xSizeButton && mouseY >= this.guiTop + 16 && mouseY <= this.guiTop + 16 + this.ySizeButton) {
        	this.onButtonClicked();
        }
    }
	
	public boolean isOnButtonEnabled() {
 		return !this.te.getStackInSlot(0).isEmpty() && !this.te.getStackInSlot(1).isEmpty() && !this.te.getStackInSlot(2).isEmpty() && !this.te.getStackInSlot(3).isEmpty();
	}
	
	public boolean isOnButtonActive() {
 		return this.te.getPowerState();
	}
	
	public void onButtonClicked() {
		//client
		((AimaggTEComputerCase)te).setPowerState(!((AimaggTEComputerCase)te).getPowerState());
		((AimaggTEComputerCase)te).getWorld().notifyBlockUpdate(((AimaggTEComputerCase)te).getPos(), ((AimaggTEComputerCase)te).getWorld().getBlockState(((AimaggTEComputerCase)te).getPos()), ((AimaggTEComputerCase)te).getWorld().getBlockState(((AimaggTEComputerCase)te).getPos()).withProperty(AimaggBlockComputerCase.POWER_STATE, ((AimaggTEComputerCase)te).getPowerState()), 2);
		//server
		AimaggPacketComputerCasePowerServer packet = new AimaggPacketComputerCasePowerServer();
		packet.setTileEntityPosition(te.getPos());
		AimaggPacketHandler.INSTANCE.sendToServer(packet);
	}

}
