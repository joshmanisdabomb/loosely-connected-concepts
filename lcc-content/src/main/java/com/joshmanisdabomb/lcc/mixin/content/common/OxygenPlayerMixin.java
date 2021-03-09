package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants;
import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class OxygenPlayerMixin extends LivingEntity {

    protected OxygenPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract Iterable<ItemStack> getArmorItems();

    @Inject(at = @At("HEAD"), method = "canConsume", cancellable = true)
    public void blockEating(boolean ignoreHunger, CallbackInfoReturnable<Boolean> callback) {
        Iterable<ItemStack> pieces = getArmorItems();
        for (ItemStack piece : pieces) {
            Item item = piece.getItem();
            if (item instanceof ContainedArmor) {
                if (((ContainedArmor)item).disableEating((PlayerEntity)(Object)this, piece, pieces)) {
                    ((PlayerEntity)(Object)this).sendMessage(new TranslatableText(TooltipConstants.contained_armor_consume), true);
                    callback.setReturnValue(false);
                    callback.cancel();
                }
            }
        }
    }

}
