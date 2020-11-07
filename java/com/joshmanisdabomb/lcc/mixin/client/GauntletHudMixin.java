package com.joshmanisdabomb.lcc.mixin.client;

import com.joshmanisdabomb.lcc.gui.overlay.GauntletOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class GauntletHudMixin {

    @Shadow
    private int ticks;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V", ordinal = 1))
    private void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        Entity camera = MinecraftClient.getInstance().getCameraEntity();
        if (camera instanceof PlayerEntity) GauntletOverlay.INSTANCE.render(matrixStack, (PlayerEntity)camera, ticks, tickDelta);
    }

}
