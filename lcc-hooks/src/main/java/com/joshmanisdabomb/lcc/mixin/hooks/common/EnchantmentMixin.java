package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Shadow @Final
    private EnchantmentTarget type;

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void canHaveEnchantment(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        Item item = stack.getItem();
        if (item instanceof LCCItemTrait trait) {
            Boolean ret = trait.lcc_canHaveEnchantment(stack, (Enchantment)(Object)this);
            if (ret != null) {
                info.setReturnValue(ret);
                info.cancel();
            }
        }
    }

}