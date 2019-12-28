package com.joshmanisdabomb.lcc.gui.inventory;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.container.AtomicBombContainer;
import com.joshmanisdabomb.lcc.network.AtomicBombDetonatePacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.IntSupplier;

public class AtomicBombScreen extends ContainerScreen<AtomicBombContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(LCC.MODID, "textures/gui/container/atomic_bomb.png");

    private FunctionalSpriteButton buttonDetonate;

    public AtomicBombScreen(AtomicBombContainer container, PlayerInventory playerInv, ITextComponent textComponent) {
        super(container, playerInv, textComponent);
        this.xSize = 176;
        this.ySize = 171;
    }

    @Override
    protected void init() {
        super.init();

        this.buttonDetonate = this.addButton(new DetonateButton(() -> {
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new AtomicBombDetonatePacket(container.te.getWorld().getDimension().getType(), container.te.getPos(), playerInventory.player.getUniqueID()));
            minecraft.player.closeScreen();
            return -1;
        }, (x, y) -> this.renderTooltip(I18n.format("gui.lcc.atomic_bomb.detonate"), x, y), GUI, this.guiLeft + 77, this.guiTop + 47, 88, 0, 171));
        this.buttonDetonate.active = container.te.canDetonate();
    }

    @Override
    public void tick() {
        this.buttonDetonate.active = container.te.canDetonate();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        for (Widget widget : this.buttons) {
            if (widget.isHovered()) {
                widget.renderToolTip(mouseX, mouseY);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    private static class DetonateButton extends FunctionalSpriteButton {

        public DetonateButton(IntSupplier onPress, BiConsumer<Integer, Integer> tooltip, ResourceLocation texture, int x, int y, int ix, int sx, int sy) {
            super(onPress, tooltip, texture, x, y, ix, sx, sy);
        }

        @Override
        protected void onRenderButton() {
            if (this.active && this.isHovered()) {
                Color c = new Color(Color.HSBtoRGB((float)((Math.cos(((System.currentTimeMillis() % 2000) / 2000F) * Math.PI * 2) * 0.03F) + 0.11F), 0.8F, 1.0F));
                GlStateManager.color4f(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F, 1.0F);
            } else {
                super.onRenderButton();
            }
        }

    }
}