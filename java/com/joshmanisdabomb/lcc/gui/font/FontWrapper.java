package com.joshmanisdabomb.lcc.gui.font;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FontWrapper extends FontRenderer {

    private final ResourceLocation locator;
    private final TextureManager tm;
    private FontRenderer base;

    public FontWrapper(ResourceLocation locator, TextureManager tm) {
        super(tm, null);
        this.tm = tm;
        this.locator = locator;
    }

    public void setGlyphProviders(List<IGlyphProvider> glyphProviders) {
        this.getBaseRenderer().setGlyphProviders(glyphProviders);
    }

    public void close() {
        this.getBaseRenderer().close();
        super.close();
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return this.getBaseRenderer().drawStringWithShadow(text, x, y, color);
}

    @Override
    public int drawString(String text, float x, float y, int color) {
        return this.getBaseRenderer().drawString(text, x, y, color);
    }

    @Override
    public String bidiReorder(String text) {
        return this.getBaseRenderer().bidiReorder(text);
    }

    @Override
    public int getStringWidth(String text) {
        return this.getBaseRenderer().getStringWidth(text);
    }

    @Override
    public float getCharWidth(char character) {
        return this.getBaseRenderer().getCharWidth(character);
    }

    @Override
    public String trimStringToWidth(String text, int width, boolean reverse) {
        return this.getBaseRenderer().trimStringToWidth(text, width, reverse);
    }

    @Override
    public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
        this.getBaseRenderer().drawSplitString(str, x, y, wrapWidth, textColor);
    }

    @Override
    public int getWordWrappedHeight(String str, int maxLength) {
        return this.getBaseRenderer().getWordWrappedHeight(str, maxLength);
    }

    @Override
    public void setBidiFlag(boolean bidiFlagIn) {
        this.getBaseRenderer().setBidiFlag(bidiFlagIn);
    }

    @Override
    public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
        return this.getBaseRenderer().listFormattedStringToWidth(str, wrapWidth);
    }

    @Override
    public String wrapFormattedStringToWidth(String str, int wrapWidth) {
        return this.getBaseRenderer().wrapFormattedStringToWidth(str, wrapWidth);
    }

    @Override
    public int sizeStringToWidth(String str, int wrapWidth) {
        return this.getBaseRenderer().sizeStringToWidth(str, wrapWidth);
    }

    @Override
    public int getWordPosition(String p_216863_1_, int p_216863_2_, int p_216863_3_, boolean p_216863_4_) {
        return this.getBaseRenderer().getWordPosition(p_216863_1_, p_216863_2_, p_216863_3_, p_216863_4_);
    }

    @Override
    public boolean getBidiFlag() {
        return this.getBaseRenderer().getBidiFlag();
    }

    private FontRenderer getBaseRenderer() {
        if (this.base == null) {
            this.base = Minecraft.getInstance().getFontResourceManager().getFontRenderer(this.locator);
            //this.base.font = new WrappedFont(this.tm, this.locator, this.base.font);
        }
        return this.base;
    }

    public IGlyph findGlyph(char c, WrappedFont font, Font base) {
        return null;
    }

    public class WrappedFont extends Font {

        private final Font base;
        private final Char2ObjectMap<IGlyph> overrides = new Char2ObjectOpenHashMap<>();

        private WrappedFont(TextureManager tm, ResourceLocation rl, Font font) {
            super(tm, rl);
            this.base = font;
        }

        @Override
        public void setGlyphProviders(List<IGlyphProvider> p_211570_1_) {
            this.base.setGlyphProviders(p_211570_1_);
        }

        @Override
        public void close() {
            this.base.close();
            super.close();
        }

        @Override
        public IGlyph findGlyph(char c) {
            if (!this.overrides.containsKey(c)) this.overrides.put(c, FontWrapper.this.findGlyph(c, this, this.base));
            IGlyph g = this.overrides.get(c);
            if (g != null) return this.overrides.get(c);
            return this.base.findGlyph(c);
        }

        @Override
        public TexturedGlyph getGlyph(char p_211187_1_) {
            return this.base.getGlyph(p_211187_1_);
        }

        @Override
        public TexturedGlyph obfuscate(IGlyph p_211188_1_) {
            return this.base.obfuscate(p_211188_1_);
        }

    }

}
