package com.joshmanisdabomb.lcc.gui.font;

import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class FixedWidthFontWrapper extends FontWrapper {

    public static final IGlyph WIDTH = () -> 6.0F;

    public FixedWidthFontWrapper(ResourceLocation locator, TextureManager tm) {
        super(locator, tm);
    }

    @Override
    public IGlyph findGlyph(char c, WrappedFont font, Font base) {
        return c == 32 ? WIDTH : super.findGlyph(c, font, base);
    }
}
