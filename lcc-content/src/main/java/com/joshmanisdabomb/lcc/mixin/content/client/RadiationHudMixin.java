package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.gui.overlay.RadiationOverlay;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class RadiationHudMixin {

    @Shadow
    private int ticks;

    @Inject(method = "renderStatusBars", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = "ldc=health"))
    private void render(MatrixStack matrixStack, CallbackInfo info, PlayerEntity camera, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s) {
        RadiationOverlay.INSTANCE.render(matrixStack, camera, s, ticks);
    }

}
