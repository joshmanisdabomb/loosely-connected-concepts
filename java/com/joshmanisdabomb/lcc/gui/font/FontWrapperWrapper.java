package com.joshmanisdabomb.lcc.gui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiFunction;

public class FontWrapperWrapper {

    private final ResourceLocation locator;
    private final BiFunction<ResourceLocation, TextureManager, FontWrapper> creator;

    private FontWrapper fw;

    public FontWrapperWrapper(ResourceLocation locator, BiFunction<ResourceLocation, TextureManager, FontWrapper> creator) {
        this.locator = locator;
        this.creator = creator;
    }

    public FontWrapper get() {
        if (this.fw == null) this.fw = this.creator.apply(this.locator, Minecraft.getInstance().textureManager);
        return this.fw;
    }

}
