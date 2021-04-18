package com.joshmanisdabomb.lcc.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;

public interface MobSpawnCallback {

    Event<MobSpawnCallback> EVENT = EventFactory.createArrayBacked(MobSpawnCallback.class,
        (listeners) -> (entity, world, difficulty, reason, data, tag) -> {
            for (MobSpawnCallback listener : listeners) {
                listener.initialize(entity, world, difficulty, reason, data, tag);
            }
        });

    void initialize(MobEntity entity, ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt);

}
