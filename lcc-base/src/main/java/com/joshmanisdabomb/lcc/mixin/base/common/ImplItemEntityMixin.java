package com.joshmanisdabomb.lcc.mixin.base.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ImplItemEntityMixin extends Entity {

    public ImplItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    private int pickupDelay;

    @Shadow
    private UUID owner;

    @Inject(method = "onPlayerCollision", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getCount()I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onPlayerCollide(PlayerEntity player, CallbackInfo info, ItemStack stack, Item item, int count) {
        if (pickupDelay > 0 || (owner != null && !owner.equals(player.getUuid()))) return;
        if (item instanceof LCCExtendedItem) {
            ItemEntity entity = (ItemEntity)(Object) this;

            int size = player.getInventory().size();
            for (int i = 0; i < size; i++) {
                ItemStack s = player.getInventory().getStack(i);
                if (s.isEmpty()) continue;
                Item listener = s.getItem();
                if (listener instanceof LCCExtendedItem) {
                    Boolean result = ((LCCExtendedItem)listener).lcc_pickupItemListen(world, s, i, stack, player, entity);
                    if (result != null) {
                        ((LCCExtendedItem)item).lcc_pickupItem(world, stack, s, i, player, entity);
                        if (result) {
                            player.sendPickup(entity, count);
                            if (stack.isEmpty()) {
                                this.discard();
                                stack.setCount(count);
                            }

                            player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), count);
                            player.triggerItemPickedUpByEntityCriteria(entity);
                        }
                        info.cancel();
                        return;
                    }
                }
            }

            Boolean result = ((LCCExtendedItem)item).lcc_pickupItem(world, stack, null, null, player, entity);
            if (result != null) {
                if (result) {
                    player.sendPickup(entity, count);
                    if (stack.isEmpty()) {
                        this.discard();
                        stack.setCount(count);
                    }

                    player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), count);
                    player.triggerItemPickedUpByEntityCriteria(entity);
                }
                info.cancel();
            }
        }
    }

}
