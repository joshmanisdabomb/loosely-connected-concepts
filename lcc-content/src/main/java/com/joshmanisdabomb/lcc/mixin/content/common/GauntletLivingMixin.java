package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction2;
import com.joshmanisdabomb.lcc.component.GauntletActorComponent;
import com.joshmanisdabomb.lcc.component.GauntletTargetComponent;
import com.joshmanisdabomb.lcc.directory.LCCComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        GauntletAction2<?> fh1 = null;
        GauntletAction2<?> fh2 = null;
        GauntletActorComponent c1 = null;
        if ((Object)this instanceof PlayerEntity) {
            c1 = LCCComponents.INSTANCE.getGauntlet_actor().maybeGet((PlayerEntity)(Object)this).orElse(null);
            if (c1 != null && c1.getFallHandler() != null) fh1 = c1.getFallHandler();
        }
        GauntletTargetComponent c2 = LCCComponents.INSTANCE.getGauntlet_target().maybeGet((LivingEntity)(Object)this).orElse(null);
        if (c2 != null && c2.getFallHandler() != null) fh2 = c2.getFallHandler();
        if (fh2 != null) {
            Integer fallRet = fh2.targetFall((LivingEntity)(Object)this, fallDistance, damageMultiplier);
            if (fallRet != null) {
                callback.setReturnValue(fallRet);
                callback.cancel();
            }
            return;
        } else if (fh1 != null) {
            Integer fallRet = fh1.actorFall((PlayerEntity)(Object)this, fallDistance, damageMultiplier);
            if (fallRet != null) {
                callback.setReturnValue(fallRet);
                callback.cancel();
            }
            return;
        }
        if (c1 != null) c1.setFallHandler(null);
        if (c2 != null) c2.setFallHandler(null);
    }

}
