package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public abstract class BowMixin {

    @ModifyVariable(method = "onStoppedUsing", at = @At(value = "STORE"), ordinal = 0)
    private PersistentProjectileEntity projectileOverride(PersistentProjectileEntity original, ItemStack shooter, World world, LivingEntity user) {
        if (!(user instanceof PlayerEntity)) return original;
        PlayerEntity player = (PlayerEntity)user;
        ItemStack projectile = player.getArrowType(shooter);
        if (projectile.getItem() instanceof LCCItemTrait) {
            PersistentProjectileEntity proj = ((LCCItemTrait)projectile.getItem()).lcc_createArrow(world, projectile, player);
            if (proj != null) return proj;
        }
        return original;
    }

    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    public void decrementOverride(ItemStack stack, int amount) {
        if (stack.getItem() instanceof LCCItemTrait) {
            if (!((LCCItemTrait)stack.getItem()).lcc_fireStackBow(stack)) stack.decrement(amount);
        }
    }

}
