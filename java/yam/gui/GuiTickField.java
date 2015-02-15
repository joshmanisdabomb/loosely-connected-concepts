package yam.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityTickField;
import yam.container.ContainerTickField;

public class GuiTickField extends GuiContainer {

    private TileEntityTickField te;

	public GuiTickField(InventoryPlayer inventoryPlayer, TileEntityTickField tileEntity) {
        super(new ContainerTickField(inventoryPlayer, tileEntity));
        this.te = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString(te.getInventoryName(), (xSize / 2) - fontRendererObj.getStringWidth(te.getInventoryName()) / 2, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        drawInfoText("Range", 25);
        drawInfoText(this.te.getRange() + " blocks", 35);
        drawInfoText("Speed", 50);
        drawInfoText(this.te.getSpeed() + " ticks", 60);
    }

    private void drawInfoText(String string, int y) {
        fontRendererObj.drawString(string, (xSize / 2) - 30 - fontRendererObj.getStringWidth(string), y, 4210752);
	}

	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(YetAnotherMod.MODID, "textures/gui/tick_field.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

}