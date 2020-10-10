package com.joshmanisdabomb.lcc.mixin.client;

import com.joshmanisdabomb.lcc.concepts.hearts.HeartType;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OverlayTexture.class)
public abstract class HeartsEntityOverlayMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;setPixelColor(III)V", ordinal = 0), index = 2)
    private int modifyPixelColor(int x, int y, int color) {
        for (HeartType ht : HeartType.values()) {
            if (y != ht.ordinal()) continue;
            return ht.getHurtColor();
        }
        return color;
    }

    @Inject(method = "getV", at = @At(value = "HEAD"), cancellable = true)
    private static void getV(boolean hurt, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(hurt ? 0 : 10);
        info.cancel();
    }

}
