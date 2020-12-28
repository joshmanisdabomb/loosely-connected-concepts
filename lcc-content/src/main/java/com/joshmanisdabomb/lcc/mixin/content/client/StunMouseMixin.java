package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.directory.LCCEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Mouse.class)
public abstract class StunMouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyArgs(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
    public void changeLookDelta(Args args) {
        if (client.player.hasStatusEffect(LCCEffects.INSTANCE.getStun())) {
            args.setAll(0.0, 0.0);
        }
    }

}
