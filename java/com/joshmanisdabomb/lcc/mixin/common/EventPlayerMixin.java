package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.events.DamageEntityCallback;
import com.joshmanisdabomb.lcc.events.InteractEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class EventPlayerMixin extends LivingEntity {

    protected EventPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        World world = this.getEntityWorld();

        if (entity != null) {
            ActionResult result = InteractEntityCallback.EVENT.invoker().interact((PlayerEntity)(Object)this, world, hand, entity);

            if (result != ActionResult.PASS) {
                info.setReturnValue(result);
                info.cancel();
            }
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", shift = At.Shift.BEFORE), ordinal = 0)
    private float setDamageAmount(float amount, DamageSource source) {
        Float f = DamageEntityCallback.EVENT.invoker().modifyDamage(this, source, amount, amount);
        return f == null ? -999 : Math.max(f, 0);
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfo info) {
        if (amount == -999) info.cancel();
    }

}
