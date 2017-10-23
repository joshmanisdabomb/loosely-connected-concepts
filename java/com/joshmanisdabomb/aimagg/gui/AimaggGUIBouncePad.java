package com.joshmanisdabomb.aimagg.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.container.AimaggContainerBouncePad;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketBouncePadTextClient;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketBouncePadTextServer;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import scala.actors.threadpool.Arrays;

public class AimaggGUIBouncePad extends GuiContainer {
	
	private InventoryPlayer playerInv;
	private AimaggTEBouncePad te;

	private GuiTextField strengthPrimary;
	private GuiTextField strengthSecondary1;
	private GuiTextField strengthSecondary2;
	
	private static final int BUTTON_WIDTH = 12;
	private static final int BUTTON_HEIGHT = 12;

	private static final int BUTTON_MINUS_X = 114;
	private static final int BUTTON_PLUS_X = 176;
	private static final int BUTTON_1_Y = 46;
	private static final int BUTTON_2_Y = 70;
	private static final int BUTTON_3_Y = 88;

	public AimaggGUIBouncePad(EntityPlayer player, AimaggTEBouncePad tileEntity) {
		super(new AimaggContainerBouncePad(player, tileEntity));
		
		this.playerInv = player.inventory;
		this.te = tileEntity;

        this.xSize = 196;
		this.ySize = 202;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
        this.strengthPrimary = new GuiTextField(0, this.fontRenderer, this.guiLeft + 132, this.guiTop + 48, 39, 12);
        this.strengthPrimary.setText(String.format("%.2f", te.getTileData().getFloat("primary_strength")));
        this.strengthPrimary.setTextColor(16777215);
        this.strengthPrimary.setDisabledTextColour(10066329);
        this.strengthPrimary.setEnableBackgroundDrawing(false);
        this.strengthPrimary.setMaxStringLength(6);
        this.strengthPrimary.setValidator(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				try {
					Float.valueOf(input);
				} catch (NumberFormatException e) {
					return false;
				}
				float f = Float.valueOf(input);
				return f >= 0 && f <= 10;
			}
        });
        this.strengthSecondary1 = new GuiTextField(1, this.fontRenderer, this.guiLeft + 132, this.guiTop + 72, 39, 12);
        this.strengthSecondary1.setText(String.format("%.2f", te.getTileData().getFloat("secondary_strength_1")));
        this.strengthSecondary1.setTextColor(16777215);
        this.strengthSecondary1.setDisabledTextColour(10066329);
        this.strengthSecondary1.setEnableBackgroundDrawing(false);
        this.strengthSecondary1.setMaxStringLength(6);
        this.strengthSecondary1.setValidator(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				try {
					Float.valueOf(input);
				} catch (NumberFormatException e) {
					return false;
				}
				float f = Float.valueOf(input);
				return f >= -5 && f <= 5;
			}
        });
        this.strengthSecondary2 = new GuiTextField(2, this.fontRenderer, this.guiLeft + 132, this.guiTop + 90, 39, 12);
        this.strengthSecondary2.setText(String.format("%.2f", te.getTileData().getFloat("secondary_strength_2")));
        this.strengthSecondary2.setTextColor(16777215);
        this.strengthSecondary2.setDisabledTextColour(10066329);
        this.strengthSecondary2.setEnableBackgroundDrawing(false);
        this.strengthSecondary2.setMaxStringLength(6);
        this.strengthSecondary2.setValidator(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				try {
					Float.valueOf(input);
				} catch (NumberFormatException e) {
					return false;
				}
				float f = Float.valueOf(input);
				return f >= -5 && f <= 5;
			}
        });
		this.textUpdate();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID, "textures/gui/container/bounce_pad.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        
		for (int i = 0; i < 6; i++) {
			boolean isPlus = i % 2 == 0;
			int id = (i+2)/2;
			int xOffset = (isPlus ? BUTTON_PLUS_X : BUTTON_MINUS_X);
			int yOffset = (id == 3 ? BUTTON_3_Y : id == 2 ? BUTTON_2_Y : BUTTON_1_Y);
		    if (!this.isButtonEnabled(isPlus, id)) {
		    	this.drawTexturedModalRect(this.guiLeft + xOffset, this.guiTop + yOffset, BUTTON_WIDTH, 202 + (isPlus ? BUTTON_HEIGHT : 0), BUTTON_WIDTH, BUTTON_HEIGHT);
		    } else if (mouseX >= this.guiLeft + xOffset && mouseX <= this.guiLeft + xOffset + BUTTON_WIDTH && mouseY >= this.guiTop + yOffset && mouseY <= this.guiTop + yOffset + BUTTON_HEIGHT) {
		    	this.drawTexturedModalRect(this.guiLeft + xOffset, this.guiTop + yOffset, 0, 202 + (isPlus ? BUTTON_HEIGHT : 0), BUTTON_WIDTH, BUTTON_HEIGHT);
		    }
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRenderer.drawString(s, 8, 6, 4210752);
	    this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 109, 4210752);

	    this.fontRenderer.drawString(new TextComponentTranslation(this.te.getBaseName() + ".strength." + te.getDirection().getName()).getUnformattedText(), 8, 48, 4210752);
	    this.fontRenderer.drawString(new TextComponentTranslation(this.te.getBaseName() + ".strength." + te.getSecondaryStrengthAxis1().getName()).getUnformattedText(), 8, 73, 4210752);
	    this.fontRenderer.drawString(new TextComponentTranslation(this.te.getBaseName() + ".strength." + te.getSecondaryStrengthAxis2().getName()).getUnformattedText(), 8, 91, 4210752);
	    
	    GlStateManager.translate((float)-this.guiLeft, (float)-this.guiTop, 0.0F);
	    
		this.strengthPrimary.drawTextBox();
		this.strengthSecondary1.drawTextBox();
		this.strengthSecondary2.drawTextBox();
        
        if (mouseX + 1 >= this.strengthPrimary.x && mouseX + 1 <= this.strengthPrimary.x + this.strengthPrimary.width && mouseY + 2 >= this.strengthPrimary.y && mouseY + 2 <= this.strengthPrimary.y + this.strengthPrimary.height) {
        	this.drawHoveringText(new TextComponentTranslation(this.te.getBaseName() + ".input_range.primary",
        						  new TextComponentTranslation(this.te.getBaseName() + "." + this.te.getDirection().getName()).getUnformattedText().toLowerCase()
        						  ).getUnformattedText(), mouseX, mouseY);
        }
        
        if (mouseX + 1 >= this.strengthSecondary1.x && mouseX + 1 <= this.strengthSecondary1.x + this.strengthSecondary1.width && mouseY + 2 >= this.strengthSecondary1.y && mouseY + 2 <= this.strengthSecondary1.y + this.strengthSecondary1.height) {
        	this.drawHoveringText(Arrays.asList(new String[]{
        						  new TextComponentTranslation(this.te.getBaseName() + ".input_range.secondary.1",
        						  this.te.getSecondaryStrengthAxis1().getName()).getUnformattedText(),
        						  new TextComponentTranslation(this.te.getBaseName() + ".input_range.secondary.2",
        						  new TextComponentTranslation(this.te.getBaseName() + "." + EnumFacing.getFacingFromAxis(AxisDirection.POSITIVE, this.te.getSecondaryStrengthAxis1()).getName()).getUnformattedText().toLowerCase(),
        						  new TextComponentTranslation(this.te.getBaseName() + "." + EnumFacing.getFacingFromAxis(AxisDirection.NEGATIVE, this.te.getSecondaryStrengthAxis1()).getName()).getUnformattedText().toLowerCase()
                				  ).getUnformattedText()}), mouseX, mouseY);
        }
        
        if (mouseX + 1 >= this.strengthSecondary2.x && mouseX + 1 <= this.strengthSecondary2.x + this.strengthSecondary2.width && mouseY + 2 >= this.strengthSecondary2.y && mouseY + 2 <= this.strengthSecondary2.y + this.strengthSecondary2.height) {
        	this.drawHoveringText(Arrays.asList(new String[]{
								  new TextComponentTranslation(this.te.getBaseName() + ".input_range.secondary.1",
								  this.te.getSecondaryStrengthAxis2().getName()).getUnformattedText(),
								  new TextComponentTranslation(this.te.getBaseName() + ".input_range.secondary.2",
								  new TextComponentTranslation(this.te.getBaseName() + "." + EnumFacing.getFacingFromAxis(AxisDirection.POSITIVE, this.te.getSecondaryStrengthAxis2()).getName()).getUnformattedText().toLowerCase(),
								  new TextComponentTranslation(this.te.getBaseName() + "." + EnumFacing.getFacingFromAxis(AxisDirection.NEGATIVE, this.te.getSecondaryStrengthAxis2()).getName()).getUnformattedText().toLowerCase()
								  ).getUnformattedText()}), mouseX, mouseY);
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
		if (this.strengthPrimary.textboxKeyTyped(typedChar, keyCode) || this.strengthSecondary1.textboxKeyTyped(typedChar, keyCode) || this.strengthSecondary2.textboxKeyTyped(typedChar, keyCode)) {
			this.textUpdate();
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < 6; i++) {
			boolean isPlus = i % 2 == 0;
			int id = (i+2)/2;
			int xOffset = (isPlus ? BUTTON_PLUS_X : BUTTON_MINUS_X);
			int yOffset = (id == 3 ? BUTTON_3_Y : id == 2 ? BUTTON_2_Y : BUTTON_1_Y);
			float amount = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ? 0.01F : Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 1000 : 1;
			if (mouseX >= this.guiLeft + xOffset && mouseX <= this.guiLeft + xOffset + BUTTON_WIDTH && mouseY >= this.guiTop + yOffset && mouseY <= this.guiTop + yOffset + BUTTON_HEIGHT) {
		    	if (id == 3) {
		    		this.strengthSecondary2.setText(String.format("%.2f", Math.min(5F, Math.max(-5F, Float.valueOf(this.strengthSecondary2.getText()) + (amount * (isPlus ? 1 : -1))))));
		    	} else if (id == 2) {
		    		this.strengthSecondary1.setText(String.format("%.2f", Math.min(5F, Math.max(-5F, Float.valueOf(this.strengthSecondary1.getText()) + (amount * (isPlus ? 1 : -1))))));
		    	} else {
		    		this.strengthPrimary.setText(String.format("%.2f", Math.min(10F, Math.max(0F, Float.valueOf(this.strengthPrimary.getText()) + (amount * (isPlus ? 1 : -1))))));
		    	}
		    }
		}
        this.strengthPrimary.mouseClicked(mouseX, mouseY, mouseButton);
        this.strengthSecondary1.mouseClicked(mouseX, mouseY, mouseButton);
        this.strengthSecondary2.mouseClicked(mouseX, mouseY, mouseButton);
    }

	public void textUpdate() {
		//client update
		te.setStrength(Float.valueOf(strengthPrimary.getText()), Float.valueOf(strengthSecondary1.getText()), Float.valueOf(strengthSecondary2.getText()));

		//server update
		AimaggPacketBouncePadTextServer packetS = new AimaggPacketBouncePadTextServer();
		packetS.setTileEntityPosition(te.getPos());
		packetS.setStrength(Float.valueOf(strengthPrimary.getText()), Float.valueOf(strengthSecondary1.getText()), Float.valueOf(strengthSecondary2.getText()));
		AimaggPacketHandler.INSTANCE.sendToServer(packetS);
	}
	
	public boolean isButtonEnabled(boolean plus, int id) {
		float f = Float.valueOf(id == 3 ? this.strengthSecondary2.getText() : (id == 2 ? this.strengthSecondary1.getText() : this.strengthPrimary.getText()));
		return plus ? f < (id > 1 ? 5.00 : 10.00) : f > (id > 1 ? -5.00 : 0.00);
	}

}