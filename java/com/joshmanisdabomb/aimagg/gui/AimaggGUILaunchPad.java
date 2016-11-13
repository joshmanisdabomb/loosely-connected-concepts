package com.joshmanisdabomb.aimagg.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.container.AimaggContainerLaunchPad;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketLaunchPadText;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;

public class AimaggGUILaunchPad extends GuiContainer {

	private InventoryPlayer playerInv;
	private AimaggTELaunchPad te;

	public AimaggGUILaunchPad(EntityPlayer player, AimaggTELaunchPad tileEntity) {
		super(new AimaggContainerLaunchPad(player, tileEntity));
		
		this.playerInv = player.inventory;
		this.te = tileEntity;

        this.xSize = 230;
		this.ySize = 177;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("aimagg:textures/gui/container/launchpad.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRendererObj.drawString(s, 8, 6, 4210752);
	    this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 82, 4210752);
	}
	
	@Override
	public void onGuiClosed() {
		this.sendTextToServer();
		super.onGuiClosed();
	}
	
	public void sendTextToServer() {
		AimaggPacketLaunchPadText packet = new AimaggPacketLaunchPadText(Constants.MOD_ID, te, 0, 0, 0);
		AimaggPacketHandler.INSTANCE.sendToServer(packet);
	}

}
