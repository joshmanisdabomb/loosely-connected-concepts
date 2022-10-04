package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.gui.overlay.HeartsOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.util.math.random.Random;

@Mixin(InGameHud.class)
public abstract class HeartsHudMixin {

    @Shadow
    private int ticks;

    @Shadow
    private Random random;

    @ModifyVariable(method = "renderStatusBars", at = @At(value = "STORE"), ordinal = 9)
    private int armorPosition(int y) {
        Entity camera = MinecraftClient.getInstance().getCameraEntity();
        if (camera instanceof PlayerEntity) return y - HeartsOverlay.INSTANCE.getTotalSpace((PlayerEntity)camera);
        return y;
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0))
    private int disableDefaultShake(Random random, int bound) {
        return 0;
    }

    @ModifyVariable(method = "renderStatusBars", at = @At(value = "STORE", ordinal = 0), ordinal = 19)
    private int heartShake(int original) {
        Entity camera = MinecraftClient.getInstance().getCameraEntity();
        if (camera instanceof PlayerEntity) return original + (HeartsOverlay.INSTANCE.shouldShake((PlayerEntity)camera) ? random.nextInt(2) : 0);
        return original;
    }

    @Inject(method = "renderStatusBars", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = "ldc=health"))
    private void render(MatrixStack matrixStack, CallbackInfo info, PlayerEntity camera, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s) {
        HeartsOverlay.INSTANCE.render(matrixStack, camera, s, ticks);
    }

}
