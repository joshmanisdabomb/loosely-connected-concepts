package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {

    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow @Final
    protected abstract ItemStack asItemStack();

    @Inject(method = "tryPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"), cancellable = true)
    public void onPlayerCollide(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        ItemStack stack = this.asItemStack();
        Item item = stack.getItem();
        if (item instanceof LCCItemTrait) {
            PersistentProjectileEntity entity = (PersistentProjectileEntity)(Object) this;

            int size = player.getInventory().size();
            for (int i = 0; i < size; i++) {
                ItemStack s = player.getInventory().getStack(i);
                if (s.isEmpty()) continue;
                Item listener = s.getItem();
                if (listener instanceof LCCItemTrait) {
                    Boolean result = ((LCCItemTrait)listener).lcc_pickupProjectileListen(world, s, i, stack, player, entity);
                    if (result != null) {
                        ((LCCItemTrait)item).lcc_pickupProjectile(world, stack, s, i, player, entity);
                        if (result) {
                            player.sendPickup(entity, 1);
                            this.discard();
                        }
                        info.cancel();
                        return;
                    }
                }
            }

            Boolean result = ((LCCItemTrait)item).lcc_pickupProjectile(world, stack, null, null, player, entity);
            if (result != null) {
                if (result) {
                    player.sendPickup(entity, 1);
                    this.discard();
                }
                info.cancel();
            }
        }
    }

}
