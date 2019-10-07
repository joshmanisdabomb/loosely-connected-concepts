package com.joshmanisdabomb.lcc.gui;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DriveScreen extends ContainerScreen<ComputingContainer> {

    public static final ResourceLocation FLOPPY = new ResourceLocation(LCC.MODID, "textures/gui/container/floppy_drive.png");
    public static final ResourceLocation CD = new ResourceLocation(LCC.MODID, "textures/gui/container/cd_drive.png");
    public static final ResourceLocation CARD = new ResourceLocation(LCC.MODID, "textures/gui/container/card_reader.png");
    public static final ResourceLocation STICK = new ResourceLocation(LCC.MODID, "textures/gui/container/stick_reader.png");
    public static final ResourceLocation DRIVE = new ResourceLocation(LCC.MODID, "textures/gui/container/drive_bay.png");

    private final ResourceLocation gui;

    public DriveScreen(ComputingContainer container, PlayerInventory playerInv, ITextComponent textComponent, ResourceLocation gui) {
        super(container, playerInv, textComponent);
        this.gui = gui;
        this.xSize = 176;
        this.ySize = 140;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.container.module.getName().getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(this.gui);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

}
