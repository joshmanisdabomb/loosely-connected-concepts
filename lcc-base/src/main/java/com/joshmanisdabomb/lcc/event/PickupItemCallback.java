package com.joshmanisdabomb.lcc.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

//TODO 0.4 lcc-extensions, more event based rather than direct calls to extended classes from mixins
public interface PickupItemCallback {

    Event<PickupItemCallback> EVENT = EventFactory.createArrayBacked(PickupItemCallback.class,
        (listeners) -> (player, world, stack, entity) -> {
            for (PickupItemCallback listener : listeners) {
                Boolean result = listener.pickup(player, world, stack, entity);

                if (result != null) return result;
            }

            return null;
        });

    //null to continue, false to block other listeners, true to cancel item pickup
    @Nullable Boolean pickup(PlayerEntity player, World world, ItemStack stack, ItemEntity entity);

}
