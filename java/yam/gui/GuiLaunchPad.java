package yam.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityLaunchPad;
import yam.container.ContainerLaunchPad;
import yam.container.ContainerTickField;

public class GuiLaunchPad extends GuiContainer {

    private TileEntityLaunchPad te;
    private GuiLaunchButton launchButton;

	public GuiLaunchPad(InventoryPlayer inventoryPlayer, TileEntityLaunchPad tileEntity) {
        super(new ContainerLaunchPad(inventoryPlayer, tileEntity));
        this.te = tileEntity;
    }
	
	public void initGui()
	{
		super.initGui();
		
		this.launchButton = new GuiLaunchButton(100, this.guiLeft + (xSize / 2 + 13), this.guiTop + 34);
		this.buttonList.add(launchButton);
	}

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString(te.getInventoryName(), (xSize / 2) - (fontRendererObj.getStringWidth(te.getInventoryName()) / 2), 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        ItemStack i = te.getStackInSlot(0);
        launchButton.enabled = false;
        if (i != null && i.hasTagCompound()) {
        	String destination = i.stackTagCompound.getInteger("x") + "," + i.stackTagCompound.getInteger("y") + "," + i.stackTagCompound.getInteger("z");
        	fontRendererObj.drawString(destination, (xSize / 2) - fontRendererObj.getStringWidth(destination) - 13, 39, 4210752);
        	fontRendererObj.drawString(EnumChatFormatting.BOLD + "LAUNCH", (xSize / 2) + 36, 39, Integer.parseInt("DD0000", 16));
            launchButton.enabled = true;
        }
    }
    
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/launch_pad.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
	
	protected void actionPerformed(GuiButton guibutton) {
        if (guibutton == launchButton) {
        	
        }
	}
	
	public static class GuiLaunchButton extends GuiButton {
		
		public GuiLaunchButton(int par1, int par2, int par3)
	    {
			super(par1,par2,par3,18,18,"");
	        this.width = 18;
	        this.height = 18;
	        this.enabled = true;
	        this.visible = true;
	        this.id = par1;
	        this.xPosition = par2;
	        this.yPosition = par3;
	    }
		
		public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
	    {
	        if (this.visible)
	        {
	            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
	            p_146112_1_.getTextureManager().bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/launch_pad.png"));
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
	            int k = this.getHoverState(this.field_146123_n);
	            GL11.glEnable(GL11.GL_BLEND);
	            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, k * 18, 18, 18);
	            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
	            int l = 14737632;

	            if (packedFGColour != 0)
	            {
	                l = packedFGColour;
	            }
	            else if (!this.enabled)
	            {
	                l = 10526880;
	            }
	            else if (this.field_146123_n)
	            {
	                l = 16777120;
	            }
	        }
	    }
		
	}

}