package yam.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityComputer;

public class GuiComputer extends GuiScreen {
	
    private TileEntityComputer te;
	private final int xSize = 256;
	private final int ySize = 252;
	private final int tabWidth = 28;
	private final int tabHeight = 35;
    
    public GuiComputer(TileEntityComputer te) {
    	this.te = te;
    }

	@Override
    public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/computer.png"));
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        this.mc.getTextureManager().bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/computerBottomTabs.png"));
        this.drawTexturedModalRect(k + this.tabWidth, l + (this.ySize - this.tabHeight), this.tabWidth, 0, this.xSize - this.tabWidth, this.tabHeight);
        
        fontRendererObj.drawString(te.getInventoryName(), k + 7, l + 7, 4210752);
		
		super.drawScreen(par1, par2, par3);
    }
	
	public boolean doesGuiPauseGame() {
        return false;
    }
	
}
