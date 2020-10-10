package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.concepts.EntityDataManagersKt;
import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction;
import com.joshmanisdabomb.lcc.directory.LCCTrackers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class GauntletLivingMixin extends Entity {

    public GauntletLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void initTrackers(CallbackInfo callback) {
        LCCTrackers.INSTANCE.init((s, p) -> s.startsWith("gauntlet") && p == LivingEntity.class);
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag")
    public void read(CompoundTag tag, CallbackInfo callback) {
        EntityDataManagersKt.getGauntletFallHandler().read(this, tag);
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag")
    public void write(CompoundTag tag, CallbackInfo callback) {
        EntityDataManagersKt.getGauntletFallHandler().write(this, tag);
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    public void addTrackers(CallbackInfo callback) {
        EntityDataManagersKt.getGauntletFallHandler().startTracker(this);
    }

    @Inject(at = @At("HEAD"), method = "computeFallDamage", cancellable = true)
    public void fallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> callback) {
        Byte fh = EntityDataManagersKt.getGauntletFallHandler().fromTracker(this);
        if (fh > 0) {
            Integer fallRet = GauntletAction.handleFall((LivingEntity)(Object)this, fallDistance, damageMultiplier, fh);
            if (fallRet != null) {
                EntityDataManagersKt.getGauntletFallHandler().resetTracker(this);
                callback.setReturnValue(fallRet);
                callback.cancel();
            }
        }
    }

}
