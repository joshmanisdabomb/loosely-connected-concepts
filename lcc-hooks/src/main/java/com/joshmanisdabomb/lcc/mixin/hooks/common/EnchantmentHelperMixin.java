package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getPossibleEntries", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void canGainEnchantment(int power, ItemStack stack, boolean treasure, CallbackInfoReturnable<List<EnchantmentLevelEntry>> info, List<EnchantmentLevelEntry> list) {
        Item item = stack.getItem();
        if (item instanceof LCCItemTrait trait) {
            List<Enchantment> enchantments = trait.lcc_canGainExtraEnchantments(stack);
            enchants: for (Enchantment e : enchantments) {
                for (int i = e.getMaxLevel(); i > e.getMinLevel() - 1; --i) {
                    if (power < e.getMinPower(i) || power > e.getMaxPower(i)) continue;
                    list.add(new EnchantmentLevelEntry(e, i));
                    continue enchants;
                }
            }
        }
    }

}