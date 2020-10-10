package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction;
import com.joshmanisdabomb.lcc.directory.LCCTrackers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class GauntletEntityMixin {

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void initTrackers(CallbackInfo callback) {
        LCCTrackers.INSTANCE.init((s, p) -> s.startsWith("gauntlet") && p == Entity.class);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readCustomDataFromTag(Lnet/minecraft/nbt/CompoundTag;)V"), method = "fromTag")
    public void read(CompoundTag tag, CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            if (g.canTarget()) g.getTargetManager().read((Entity)(Object)this, tag);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeCustomDataToTag(Lnet/minecraft/nbt/CompoundTag;)V"), method = "toTag")
    public void write(CompoundTag tag, CallbackInfoReturnable<CompoundTag> callback) {
        for (GauntletAction g : GauntletAction.values()) {
            if (g.canTarget()) g.getTargetManager().write((Entity)(Object)this, tag);
        }
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void addTrackers(CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            if (g.canTarget()) g.getTargetManager().startTracker((Entity)(Object)this);
        }
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            if (!g.canTarget()) continue;
            if (g.isTarget((Entity)(Object)this)) {
                g.getTargetManager().modifyTracker((Entity)(Object)this, tag -> g.baseTargetTick((Entity)(Object)this, tag));
            }
        }
    }

}
