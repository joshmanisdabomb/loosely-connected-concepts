package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowMixin {

    @ModifyVariable(method = "createArrow", at = @At(value = "STORE"), ordinal = 0)
    private static PersistentProjectileEntity projectileOverride(PersistentProjectileEntity original, World world, LivingEntity user, ItemStack shooter) {
        if (!(user instanceof PlayerEntity)) return original;
        PlayerEntity player = (PlayerEntity)user;
        ItemStack projectile = player.getArrowType(shooter);
        if (projectile.getItem() instanceof LCCItemTrait) {
            PersistentProjectileEntity proj = ((LCCItemTrait)projectile.getItem()).lcc_createArrow(world, projectile, player);
            if (proj != null) return proj;
        }
        return original;
    }

    @ModifyArg(method = "loadProjectiles", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;loadProjectile(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;ZZ)Z"), index = 2)
    private static ItemStack loadOverride(ItemStack projectile) {
        if (projectile.getItem() instanceof LCCItemTrait) {
            ItemStack stack2 = ((LCCItemTrait)projectile.getItem()).lcc_fireStackCrossbow(projectile);
            if (stack2 != null) return stack2;
        }
        return projectile;
    }

}
