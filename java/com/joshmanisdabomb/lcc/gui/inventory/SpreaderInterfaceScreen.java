package com.joshmanisdabomb.lcc.gui.inventory;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.SpreaderInterfaceContainer;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpreaderInterfaceScreen extends ContainerScreen<SpreaderInterfaceContainer> {

    private ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/spreader_interface.png");

    private DyeColor tab = DyeColor.WHITE;

    public SpreaderInterfaceScreen(SpreaderInterfaceContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
        this.xSize = 230;
        this.ySize = 231;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (mouseX >= i + 202 && mouseX <= i + 202 + 16 && mouseY >= j + 10 && mouseY <= j + 10 + 16) {
            this.renderTooltip(new ItemStack(LCCBlocks.spreaders.get(tab)), mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 8.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
        this.font.drawString(I18n.format("block.lcc.spreader_interface.cost"), 8.0F, 118, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

        this.changeDrawingColor(tab);
        this.blit(i+7, j+34, 7, 34, 216, 72);

        for (DyeColor color : DyeColor.values()) {
            this.changeDrawingColor(color);
            if (color == tab) {
                this.blit(i+8+(color.getId() * 10), j+21, 240, 0, 10, 14);
            } else {
                this.blit(i+8+(color.getId() * 10), j+21, 230, 0, 10, 13);
            }
        }

        //render items
        this.changeDrawingColor(null);
        this.itemRenderer.zLevel = 200.0F;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.translatef(0.5F, 0.5F, 32.0F);

        this.itemRenderer.renderItemIntoGUI(new ItemStack(LCCBlocks.spreaders.get(tab)), i + 202, j + 10);
        //this.itemRenderer.renderItemOverlays(Minecraft.getInstance().fontRenderer, new ItemStack(Items.EMERALD, Integer.MAX_VALUE), i + 42, j + 109);

        GlStateManager.translatef(-0.5F, -0.5F, -32.0F);
        RenderHelper.enableStandardItemLighting();
        this.itemRenderer.zLevel = 0.0F;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.changeTabs(mouseX, mouseY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.changeTabs(mouseX, mouseY);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseDX, double mouseDY) {
        this.changeTabs(mouseX, mouseY);
        return super.mouseDragged(mouseX, mouseY, button, mouseDX, mouseDY);
    }

    private void changeTabs(double mouseX, double mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        for (DyeColor color : DyeColor.values()) {
            if (mouseX >= i+8+(color.getId() * 10) && mouseX < i+18+(color.getId() * 10) && mouseY >= j+21 && mouseY < j+35) {
                tab = color;
            }
        }
    }

    private void changeDrawingColor(DyeColor color) {
        if (color != null) {
            float[] c = color.getColorComponentValues();
            GlStateManager.color4f((c[0] + 0.2F) / 1.2F, (c[1] + 0.2F) / 1.2F, (c[2] + 0.2F) / 1.2F, 1.0F);
        } else {
            GlStateManager.color4f(0.0F, 0.0F, 0.0F, 1.0F);
        }
    }

}
