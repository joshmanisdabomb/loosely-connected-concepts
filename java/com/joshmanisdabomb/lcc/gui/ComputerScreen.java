package com.joshmanisdabomb.lcc.gui;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComputerScreen extends ComputingScreen {

    public static final ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/computer.png");

    public ComputerScreen(ComputingContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
        this.xSize = 176;
        this.ySize = 150;
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.container.module.getName().getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

}
