package com.joshmanisdabomb.lcc.mixin.hooks.client;

import com.joshmanisdabomb.lcc.cache.NativeSkinCache;
import com.joshmanisdabomb.lcc.hooks.RenderingUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.HashMap;

@Mixin(PlayerSkinTexture.class)
public abstract class PlayerSkinTextureMixin extends ResourceTexture implements NativeSkinCache {

    private NativeImage image = null;

    public PlayerSkinTextureMixin(Identifier location) {
        super(location);
    }

    @Inject(method = "uploadTexture", at = @At("HEAD"))
    private void swipeTexture(NativeImage image, CallbackInfo info) {
        this.image = new NativeImage(image.getFormat(), image.getWidth(), image.getHeight(), true);
        this.image.copyFrom(image);
    }

    @NotNull
    @Override
    public NativeImage getImage() {
        return image;
    }

}
