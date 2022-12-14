package com.joshmanisdabomb.lcc.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.Nullable;

public interface DamageEntityCallback {

    Event<DamageEntityCallback> EVENT = EventFactory.createArrayBacked(DamageEntityCallback.class,
        (listeners) -> (entity, source, initial, original) -> {
            Float damage = initial;
            for (DamageEntityCallback listener : listeners) {
                damage = listener.modifyDamage(entity, source, damage, original);
                if (damage == null) return null;
            }
            return damage;
        });

    @Nullable Float modifyDamage(LivingEntity entity, DamageSource source, float initial, float original);

}
