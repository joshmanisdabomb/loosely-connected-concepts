package com.joshmanisdabomb.lcc.mixin.client;

import com.joshmanisdabomb.lcc.concepts.EntityDataManagersKt;
import com.joshmanisdabomb.lcc.concepts.hearts.HeartType;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class HeartsLivingRendererMixin {

    /*@Redirect(method = "getOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OverlayTexture;getV(Z)I", ordinal = 0))
    private static int getV(boolean hurt) {
        return 2;
    }*/

    @Redirect(method = "getOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OverlayTexture;getV(Z)I", ordinal = 0))
    private static int getV(boolean hurt, LivingEntity entity) {
        if (!hurt) return 10;
        return EntityDataManagersKt.getHeartsLastType().fromTracker(entity);
    }

}
