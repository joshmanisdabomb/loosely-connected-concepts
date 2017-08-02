package com.joshmanisdabomb.aimagg.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.container.AimaggContainerLaunchPad;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketLaunchPadLaunch;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketLaunchPadText;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class AimaggGUILaunchPad extends GuiContainer {

	private static final int xSizeButton = 48;
	private static final int ySizeButton = 37;
	
	private InventoryPlayer playerInv;
	private AimaggTELaunchPad te;

	private GuiTextField destinationx;
	private GuiTextField destinationy;
	private GuiTextField destinationz;

	public AimaggGUILaunchPad(EntityPlayer player, AimaggTELaunchPad tileEntity) {
		super(new AimaggContainerLaunchPad(player, tileEntity));
		
		this.playerInv = player.inventory;
		this.te = tileEntity;

        this.xSize = 230;
		this.ySize = 177;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        Keyboard.enableRepeatEvents(true);
        this.destinationx = new GuiTextField(0, this.fontRenderer, this.guiLeft + 9, this.guiTop + 67, 35, 12);
        this.destinationx.setText(Integer.toString(te.getTileData().getInteger("destinationx")));
        this.destinationx.setTextColor(16777215);
        this.destinationx.setDisabledTextColour(10066329);
        this.destinationx.setEnableBackgroundDrawing(false);
        this.destinationx.setMaxStringLength(9);
        this.destinationx.setValidator(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				try {
					Integer.valueOf(input);
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			}
        });
        this.destinationy = new GuiTextField(1, this.fontRenderer, this.guiLeft + 56, this.guiTop + 67, 35, 12);
        this.destinationy.setText(Integer.toString(te.getTileData().getInteger("destinationy")));
        this.destinationy.setTextColor(16777215);
        this.destinationy.setDisabledTextColour(10066329);
        this.destinationy.setEnableBackgroundDrawing(false);
        this.destinationy.setMaxStringLength(9);
        this.destinationy.setValidator(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				try {
					Integer.valueOf(input);
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			}
        });
        this.destinationz = new GuiTextField(2, this.fontRenderer, this.guiLeft + 103, this.guiTop + 67, 35, 12);
        this.destinationz.setText(Integer.toString(te.getTileData().getInteger("destinationz")));
        this.destinationz.setTextColor(16777215);
        this.destinationz.setDisabledTextColour(10066329);
        this.destinationz.setEnableBackgroundDrawing(false);
        this.destinationz.setMaxStringLength(9);
        this.destinationz.setValidator(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				try {
					Integer.valueOf(input);
				} catch (NumberFormatException e) {
					return false;
				}
				return true;
			}
        });
		this.textUpdate();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("aimagg:textures/gui/container/launch_pad.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    if (!this.isButtonEnabled()) {
	    	this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 20, 0, 177, this.xSizeButton, this.ySizeButton);
	    } else if (mouseX >= this.guiLeft + 20 && mouseX <= this.guiLeft + 20 + this.xSizeButton && mouseY >= this.guiTop + 20 && mouseY <= this.guiTop + 20 + this.ySizeButton) {
	    	this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 20, 0 + this.xSizeButton, 177, this.xSizeButton, this.ySizeButton);
	    }
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRenderer.drawString(s, 8, 6, 4210752);
	    this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 82, 4210752);
	    
        GlStateManager.translate((float)-this.guiLeft, (float)-this.guiTop, 0.0F);
	    
		if (this.te.getStackInSlot(2).getItem() instanceof AimaggItemVectorPearl) {
			this.destinationx.setEnabled(false);
			this.destinationy.setEnabled(false);
			this.destinationz.setEnabled(false);
			NBTTagCompound vpNBT = this.te.getStackInSlot(2).getOrCreateSubCompound(Constants.MOD_ID + "_vector_pearl");
			if (Integer.valueOf(this.destinationx.getText()) != vpNBT.getInteger("xcoord")) {
				this.destinationx.setText(Integer.toString(vpNBT.getInteger("xcoord")));
				this.textUpdate();
			}
			if (Integer.valueOf(this.destinationy.getText()) != vpNBT.getInteger("ycoord")) {
				this.destinationy.setText(Integer.toString(vpNBT.getInteger("ycoord")));
				this.textUpdate();
			}
			if (Integer.valueOf(this.destinationz.getText()) != vpNBT.getInteger("zcoord")) {
				this.destinationz.setText(Integer.toString(vpNBT.getInteger("zcoord")));
				this.textUpdate();
			}
			this.destinationx.drawTextBox();
			this.destinationy.drawTextBox();
			this.destinationz.drawTextBox();
		} else {
			this.destinationx.setEnabled(true);
			this.destinationy.setEnabled(true);
			this.destinationz.setEnabled(true);
			this.destinationx.drawTextBox();
			this.destinationy.drawTextBox();
			this.destinationz.drawTextBox();
		}

        GlStateManager.translate((float)this.guiLeft, (float)this.guiTop, 0.0F);
	}
	
	@Override
	public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
		this.textUpdate();
        super.onGuiClosed();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.destinationx.textboxKeyTyped(typedChar, keyCode) || this.destinationy.textboxKeyTyped(typedChar, keyCode) || this.destinationz.textboxKeyTyped(typedChar, keyCode)) {
			this.textUpdate();
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isButtonEnabled() && mouseX >= this.guiLeft + 20 && mouseX <= this.guiLeft + 20 + this.xSizeButton && mouseY >= this.guiTop + 20 && mouseY <= this.guiTop + 20 + this.ySizeButton) {
        	this.initiateLaunch();
        }
        this.destinationx.mouseClicked(mouseX, mouseY, mouseButton);
        this.destinationy.mouseClicked(mouseX, mouseY, mouseButton);
        this.destinationz.mouseClicked(mouseX, mouseY, mouseButton);
    }
	
	private void initiateLaunch() {
		//client update
		te.launch(this.playerInv.player.isCreative());
		//server update
		AimaggPacketLaunchPadLaunch packet = new AimaggPacketLaunchPadLaunch();
		packet.setTileEntityPosition(te.getPos());
		packet.setCreativeMode(this.playerInv.player.isCreative());
		AimaggPacketHandler.INSTANCE.sendToServer(packet);
	}

	public void textUpdate() {
		//client update
		te.setDestination(Integer.valueOf(destinationx.getText()),Integer.valueOf(destinationy.getText()),Integer.valueOf(destinationz.getText()));
		//server update
		AimaggPacketLaunchPadText packet = new AimaggPacketLaunchPadText();
		packet.setTileEntityPosition(te.getPos());
		packet.setDestination(new BlockPos(Integer.valueOf(destinationx.getText()),Integer.valueOf(destinationy.getText()),Integer.valueOf(destinationz.getText())));
		AimaggPacketHandler.INSTANCE.sendToServer(packet);
	}
	
	public boolean isButtonEnabled() {
		//TODO Lava fuel is placeholder.
 		return (this.te.getStackInSlot(0).getItem() instanceof AimaggItemMissile) && (this.te.getStackInSlot(1).getItem() == Items.LAVA_BUCKET || this.playerInv.player.isCreative());
	}

}
