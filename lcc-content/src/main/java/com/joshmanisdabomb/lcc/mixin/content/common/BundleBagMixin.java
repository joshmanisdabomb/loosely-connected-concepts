package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.item.BagItem;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
