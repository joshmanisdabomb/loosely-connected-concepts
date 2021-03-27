package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants;
import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemUsage.class)
public abstract class ContainedItemUsageMixin {

    @Inject(at = @At("HEAD"), method = "consumeHeldItem", cancellable = true)
    private static void blockEating(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> callback) {
        ItemStack stack = player.getStackInHand(hand);
        UseAction action = stack.getItem().getUseAction(stack);
        if (action != UseAction.DRINK && action != UseAction.EAT) return;
        Iterable<ItemStack> pieces = player.getArmorItems();
        for (ItemStack piece : pieces) {
            Item item = piece.getItem();
            if (item instanceof ContainedArmor) {
                if (((ContainedArmor)item).blockEating(player, piece, pieces)) {
                    player.sendMessage(new TranslatableText(TooltipConstants.contained_armor_consume), true);
                    callback.setReturnValue(TypedActionResult.fail(stack));
                    callback.cancel();
                }
            }
        }
    }

}
