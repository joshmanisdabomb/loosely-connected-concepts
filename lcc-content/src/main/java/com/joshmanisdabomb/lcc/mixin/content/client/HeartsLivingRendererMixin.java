package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType;
import com.joshmanisdabomb.lcc.component.HeartsComponent;
import com.joshmanisdabomb.lcc.directory.LCCComponents;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(LivingEntityRenderer.class)
public abstract class HeartsLivingRendererMixin {

    @Redirect(method = "getOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OverlayTexture;getV(Z)I", ordinal = 0))
    private static int getV(boolean hurt, LivingEntity entity) {
        if (!hurt) return 10;
        return LCCComponents.INSTANCE.getHearts().maybeGet(entity).map(HeartsComponent::getDamageLayer).orElse(HeartType.RED).ordinal();
    }

}
