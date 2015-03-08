package yam.gui;

import java.sql.Time;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityComputer;
import yam.container.ContainerComputer;

public class GuiComputer extends GuiContainer {
	
    private TileEntityComputer te;
	private final int tabWidth = 28;
	private final int tabHeight = 35;
    
    public GuiComputer(InventoryPlayer inventoryPlayer, TileEntityComputer te) {
    	super(new ContainerComputer(inventoryPlayer, te));
    	this.te = te;
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
        this.mc.getTextureManager().bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/computer.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        fontRendererObj.drawString(te.getInventoryName(), k + 7, l + 7, 4210752);
        fontRendererObj.drawString("Day " + getDays(te.getWorldObj().getWorldInfo()) + " | " + getTime(te.getWorldObj().getWorldInfo().getWorldTime()), k + 11, l + 117, Integer.parseInt("FFFFFF", 16));
    }

	public boolean doesGuiPauseGame() {
        return false;
    }
	
	public void loadFilesystem(int drive) {
		GuiHandler.setSubGUIID(drive);
		this.mc.thePlayer.openGui(YetAnotherMod.instance, GuiHandler.computerStorage, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		return;
	}
	
	public void mouseClicked(int x, int y, int z) {
		super.mouseClicked(x,y,z);
		
		x = x - this.guiLeft;
		y = y - this.guiTop;
		if (x >= 91 && x <= 164 && y >= 29 && y <= 48) {
			this.loadFilesystem(0);
		}
	}
	
	public static String getTime(long time) {
		int hours = (int)((Math.floor(time / 1000L) + 6) % 24);
		int minutes = (int)Math.floor(Double.parseDouble(String.format("%05d", time).substring(2, 4)) / 100D * 60D);
		return ((hours == 12) ? "12" : String.format("%02d", hours % 12)) + ":" + String.format("%02d", minutes) + ((hours >= 12) ? " PM" : " AM");
	}

	public static int getDays(WorldInfo info) {
		return (int)Math.floor(info.getWorldTotalTime() / 24000) + 1;
	}
	
}
