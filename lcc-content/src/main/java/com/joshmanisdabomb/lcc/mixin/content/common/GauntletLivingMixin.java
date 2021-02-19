package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class GauntletLivingMixin extends Entity {

    public GauntletLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "computeFallDamage", cancellable = true)
    public void fallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> callback) {
        /*Byte fh = EntityDataManagersKt.getGauntletFallHandler().fromTracker(this);
        if (fh > 0) {
            Integer fallRet = GauntletAction.handleFall((LivingEntity)(Object)this, fallDistance, damageMultiplier, fh);
            if (fallRet != null) {
                EntityDataManagersKt.getGauntletFallHandler().resetTracker(this);
                callback.setReturnValue(fallRet);
                callback.cancel();
            }
        }*/
    }

}
