package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.EntityDataManagersKt;
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType;
import com.joshmanisdabomb.lcc.directory.LCCTrackers;
import kotlin.Unit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class HeartsLivingMixin extends Entity {

    public HeartsLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void initTrackers(CallbackInfo callback) {
        LCCTrackers.INSTANCE.init(Unit.INSTANCE, c -> c.getName().startsWith("hearts") && c.getProperties() == LivingEntity.class);
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag")
    public void read(CompoundTag tag, CallbackInfo callback) {
        for (HeartType h : HeartType.values()) {
            if (h.getAmountManager() != null) h.getAmountManager().read(this, tag);
            if (h.getMaxManager() != null) h.getMaxManager().read(this, tag);
        }
        EntityDataManagersKt.getHeartsLastType().read(this, tag);
        HeartType.Companion.getCrystalRegen().read(this, tag);
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag")
    public void write(CompoundTag tag, CallbackInfo callback) {
        for (HeartType h : HeartType.values()) {
            if (h.getAmountManager() != null) h.getAmountManager().write(this, tag);
            if (h.getMaxManager() != null) h.getMaxManager().write(this, tag);
        }
        EntityDataManagersKt.getHeartsLastType().write(this, tag);
        HeartType.Companion.getCrystalRegen().write(this, tag);
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    public void addTrackers(CallbackInfo callback) {
        for (HeartType h : HeartType.values()) {
            if (h.getAmountManager() != null) h.getAmountManager().startTracker(this);
            if (h.getMaxManager() != null) h.getMaxManager().startTracker(this);
        }
        EntityDataManagersKt.getHeartsLastType().startTracker(this);
        HeartType.Companion.getCrystalRegen().startTracker(this);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo callback) {
        for (HeartType h : HeartType.values()) {
            h.tick((LivingEntity)(Object)this);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private float setDamageAmount(float amount) {
        return HeartType.calculateDamageAll((LivingEntity)(Object)this, amount);
    }

}
