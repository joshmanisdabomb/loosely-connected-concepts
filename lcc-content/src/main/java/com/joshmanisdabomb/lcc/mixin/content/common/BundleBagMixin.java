package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.item.BagItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleItem.class)
public class BundleBagMixin extends Item implements BagItem {

    public BundleBagMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getSize() {
        return 64;
    }

    @Override
    public boolean canBagStore(@NotNull ItemStack stack) {
        return true;
    }

    @Inject(method = "getItemOccupancy", at = @At("HEAD"), cancellable = true)
    private static void overrideItemOccupancy(ItemStack stack, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(((BagItem)Items.BUNDLE).getBagItemOccupancy(stack));
        info.cancel();
    }

}
