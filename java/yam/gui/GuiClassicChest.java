package yam.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityClassicChest;
import yam.container.ContainerClassicChest;
import yam.container.ContainerTrashCan;

public class GuiClassicChest extends GuiContainer {

    private TileEntityClassicChest te;
	private int inventoryRows;
	private TileEntityClassicChest upperChestInventory;
	private TileEntityClassicChest lowerChestInventory;

	public GuiClassicChest(InventoryPlayer inventoryPlayer, TileEntityClassicChest tileEntity, TileEntityClassicChest tileEntity2) {
        super(new ContainerClassicChest(inventoryPlayer, tileEntity, tileEntity2));
        this.upperChestInventory = tileEntity;
        this.lowerChestInventory = tileEntity2;
        this.inventoryRows = tileEntity.getSizeInventory() / 9;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        this.fontRendererObj.drawString(this.lowerChestInventory.hasCustomInventoryName() ? this.lowerChestInventory.getInventoryName() : I18n.format(this.lowerChestInventory.getInventoryName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.hasCustomInventoryName() ? this.upperChestInventory.getInventoryName() : I18n.format(this.upperChestInventory.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(x, y + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

}