package com.joshmanisdabomb.lcc.gui;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import com.joshmanisdabomb.lcc.container.TerminalContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerminalScreen extends ContainerScreen<TerminalContainer> {

    public static final ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/terminal.png");

    private final TerminalSession session;

    public TerminalScreen(TerminalContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
        (this.session = new TerminalSession(container.te)).updateActiveComputer();

        this.xSize = 256;
        this.ySize = 231;
    }

    @Override
    public void tick() {
        this.session.updateActiveComputer();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 7.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        AbstractGui.fill(this.guiLeft + 8, this.guiTop + 18, this.guiLeft + 248, this.guiTop + 134, session.getBackgroundColor());

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        session.render(partialTicks, this.guiLeft + 8, this.guiTop + 18);
        this.minecraft.getTextureManager().bindTexture(GUI);

        GlStateManager.enableBlend();
        this.blit(this.guiLeft + 8, this.guiTop + 18, 256, 0, 240, 116);
        GlStateManager.disableBlend();
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (!session.active()) return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);

        InputMappings.Input mouseKey = InputMappings.getInputByCode(p_keyPressed_1_, p_keyPressed_2_);
        if (mouseKey.getType() == InputMappings.Type.MOUSE) return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        if (p_keyPressed_1_ == 256) return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
            if (this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
            if (this.minecraft.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
            if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null) {
                for (int i = 0; i < 9; i++) {
                    if (this.minecraft.gameSettings.keyBindsHotbar[i].isActiveAndMatches(mouseKey)) {
                        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
                    }
                }
            }
        }
        if (!session.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        return true;
    }

    @Override
    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        if (!session.active()) return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
        return session.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        if (!session.active()) return super.charTyped(p_charTyped_1_, p_charTyped_2_);
        return session.charTyped(p_charTyped_1_, p_charTyped_2_);
    }

    @Override
    public void blit(int p_blit_1_, int p_blit_2_, int p_blit_3_, int p_blit_4_, int p_blit_5_, int p_blit_6_) {
        blit(p_blit_1_, p_blit_2_, this.blitOffset, (float)p_blit_3_, (float)p_blit_4_, p_blit_5_, p_blit_6_, 512, 512);
    }

}
