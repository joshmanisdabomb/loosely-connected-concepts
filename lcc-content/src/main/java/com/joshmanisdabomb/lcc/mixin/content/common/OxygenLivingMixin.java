package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class OxygenLivingMixin extends Entity {

    public OxygenLivingMixin(EntityType<?> type, World world) { super(type, world); }

    @Shadow
    public abstract Iterable<ItemStack> getArmorItems();

    @Inject(at = @At("HEAD"), method = "canHaveStatusEffect", cancellable = true)
    public void blockStatusEffect(StatusEffectInstance instance, CallbackInfoReturnable<Boolean> callback) {
        Iterable<ItemStack> pieces = getArmorItems();
        for (ItemStack piece : pieces) {
            Item item = piece.getItem();
            if (item instanceof ContainedArmor) {
                if (((ContainedArmor)item).blockStatusEffect((LivingEntity)(Object)this, instance, piece, pieces)) {
                    callback.setReturnValue(false);
                    callback.cancel();
                }
            }
        }
    }

}
