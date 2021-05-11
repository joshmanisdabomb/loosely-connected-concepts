package com.joshmanisdabomb.lcc.mixin.infra.common;

import com.joshmanisdabomb.lcc.event.MobSpawnCallback;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class ImplMobMixin {

    @Inject(method = "initialize", at = @At("TAIL"))
    public void spawn(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> info) {
        MobSpawnCallback.EVENT.invoker().initialize((MobEntity)(Object)this, world, difficulty, spawnReason, entityData, entityNbt);
    }

}
