package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.item.LCCExtendedItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

}
