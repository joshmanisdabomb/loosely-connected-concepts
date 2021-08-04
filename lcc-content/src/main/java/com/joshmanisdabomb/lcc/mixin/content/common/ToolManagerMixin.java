package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity;
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait;
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait;
import net.fabricmc.fabric.api.tool.attribute.v1.ToolManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolManager.class)
public abstract class ToolManagerMixin {

    @Inject(method = "handleIsEffectiveOnIgnoresVanilla(Lnet/minecraft/block/BlockState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Z)Z", at = @At(value = "RETURN"), cancellable = true)
    private static void effectiveHook(BlockState state, ItemStack stack, @Nullable LivingEntity user, boolean vanillaResult, CallbackInfoReturnable<Boolean> info) {
        isToolTypeSuitableFor(state, stack, user, vanillaResult, info);
    }

    @Inject(method = "handleIsEffectiveOnIgnoresVanilla(Lnet/minecraft/block/BlockState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "RETURN"), cancellable = true)
    private static void effectiveHook(BlockState state, ItemStack stack, @Nullable LivingEntity user, CallbackInfoReturnable<Boolean> info) {
        isToolTypeSuitableFor(state, stack, user, false, info);
    }

    private static void isToolTypeSuitableFor(BlockState state, ItemStack stack, @Nullable LivingEntity user, boolean vanillaResult, CallbackInfoReturnable<Boolean> info) {
        if (info.getReturnValueZ()) {
            Block block = state.getBlock();
            Item item = stack.getItem();
            if (block instanceof LCCContentBlockTrait && item instanceof LCCContentItemTrait) {
                for (ToolEffectivity t : ToolEffectivity.values()) {
                    if (!((LCCContentItemTrait)item).lcc_content_isTool(stack, state, t) && ((LCCContentBlockTrait)block).lcc_content_isToolRequired(state, stack, t)) {
                        info.setReturnValue(false);
                        return;
                    }
                }
            }
        }
    }

}
