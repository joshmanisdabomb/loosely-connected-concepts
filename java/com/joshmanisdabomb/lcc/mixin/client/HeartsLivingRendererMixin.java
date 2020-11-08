package com.joshmanisdabomb.lcc.mixin.client;

import com.joshmanisdabomb.lcc.concepts.EntityDataManagersKt;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntityRenderer.class)
public abstract class HeartsLivingRendererMixin {

    @Redirect(method = "getOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OverlayTexture;getV(Z)I", ordinal = 0))
    private static int getV(boolean hurt, LivingEntity entity) {
        if (!hurt) return 10;
        return EntityDataManagersKt.getHeartsLastType().fromTracker(entity);
    }

}
