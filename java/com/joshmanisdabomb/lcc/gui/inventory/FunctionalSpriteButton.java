package com.joshmanisdabomb.lcc.gui.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.IntSupplier;

class FunctionalSpriteButton extends AbstractButton {

    private final IntSupplier onPress;
    private final BiConsumer<Integer, Integer> tooltip;

    private final ResourceLocation texture;

    public int ix;
    public int sx;
    public int sy;

    public FunctionalSpriteButton(IntSupplier onPress, BiConsumer<Integer, Integer> tooltip, ResourceLocation texture, int x, int y, int ix, int sx, int sy) {
        super(x, y, 22, 22, "");
        this.onPress = onPress;
        this.tooltip = tooltip;
        this.ix = ix;
        this.sx = sx;
        this.sy = sy;
        this.texture = texture;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int blit = 0;
        if (!this.active) {
            blit += this.width * 2;
        } else if (this.isHovered()) {
            blit += this.width * 3;
        }

        this.onRenderButton();
        this.blit(this.x, this.y, this.sx + blit, this.sy, this.width, this.height);
        GlStateManager.enableBlend();
        this.onRenderIcon();
        this.blit(this.x, this.y, this.sx + this.ix, this.sy, 22, 22);
        GlStateManager.disableBlend();
    }

    @Override
    public void onPress() {
        int ix = this.onPress.getAsInt();
        if (ix >= 0) this.ix = ix;
    }

    @Override
    public void renderToolTip(int x, int y) {
        tooltip.accept(x, y);
    }

    protected void onRenderButton() {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected void onRenderIcon() {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}