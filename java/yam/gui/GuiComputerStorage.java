package yam.gui;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityComputer;
import yam.container.ContainerComputer;
import yam.container.ContainerComputerStorage;
import yam.container.InventoryDrive;

public class GuiComputerStorage extends GuiContainer {
	
    private TileEntityComputer te;
	private final int tabWidth = 28;
	private final int tabHeight = 35;
	private InventoryDrive drive;
	private int sub = 0;
    
    public GuiComputerStorage(InventoryPlayer inventoryPlayer, TileEntityComputer te, int drive) {
    	super(new ContainerComputerStorage(inventoryPlayer, te, drive));
    	this.te = te;
    	this.sub = drive+1;
    	this.drive = te.getStorageDrive(sub);
    }
    
    public void initGui() {
    	this.xSize = 256;
    	this.ySize = 252;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/computerFilesystem.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        fontRendererObj.drawString(drive.getInventoryName(), k + 7, l + 7, 4210752);
    }
	
	public boolean doesGuiPauseGame() {
        return false;
    }
	
	protected void mouseClicked(int par1, int par2, int par3) {
		/*
		int x = par1 - this.guiLeft;
		int y = par2 - this.guiTop;
		Minecraft.getMinecraft().thePlayer.openGui(YetAnotherMod.instance, GuiHandler.computer, Minecraft.getMinecraft().theWorld, ((int)Minecraft.getMinecraft().thePlayer.posX), ((int)Minecraft.getMinecraft().thePlayer.posY), ((int)Minecraft.getMinecraft().thePlayer.posZ));
		*/
		super.mouseClicked(par1, par2, par3);
    }
	
}
