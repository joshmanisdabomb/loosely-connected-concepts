package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.concepts.hearts.HeartType;
import com.joshmanisdabomb.lcc.events.DamageEntityCallback;
import com.joshmanisdabomb.lcc.events.InteractEntityCallback;
import com.joshmanisdabomb.lcc.item.LCCExtendedItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class EventLivingMixin extends Entity {

    public EventLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"), cancellable = true)
    public void swing(Hand hand, boolean sendToAll, CallbackInfo info) {
        ItemStack stack = ((LivingEntity)(Object)this).getStackInHand(hand);
        if (!stack.isEmpty() && ((LCCExtendedItem)stack.getItem()).lcc_onEntitySwing(stack, ((LivingEntity)(Object)this))) {
            info.cancel();
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", shift = At.Shift.BEFORE), ordinal = 0)
    private float setDamageAmount(float amount, DamageSource source) {
        Float f = DamageEntityCallback.EVENT.invoker().modifyDamage((LivingEntity)(Object)this, source, amount, amount);
        return f == null ? -1 : Math.max(f, 0);
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfo info) {
        if (amount == -999) info.cancel();
    }

}
