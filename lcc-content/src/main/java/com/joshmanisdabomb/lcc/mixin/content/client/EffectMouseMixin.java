package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.trait.LCCContentEffectTrait;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Collection;

@Mixin(Mouse.class)
public abstract class EffectMouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyArgs(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    public void changeLookDelta(Args args) {
        if (client.player == null) return;
        Collection<StatusEffectInstance> effects = client.player.getStatusEffects();
        for (StatusEffectInstance effect : effects) {
            if (effect.getEffectType() instanceof LCCContentEffectTrait leffect) {
                Object[] values = leffect.lcc_content_modifyLookSpeed(client.player, args.get(0), args.get(1));
                if (values != null) {
                    args.setAll(values);
                }
            }
        }
    }

}
