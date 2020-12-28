package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction;
import com.joshmanisdabomb.lcc.directory.LCCTrackers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class GauntletPlayerMixin extends LivingEntity {

    protected GauntletPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void initTrackers(CallbackInfo callback) {
        LCCTrackers.INSTANCE.init((s, p) -> s.startsWith("gauntlet") && p == PlayerEntity.class);
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag")
    public void read(CompoundTag tag, CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            g.getActorManager().read(this, tag);
        }
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag")
    public void write(CompoundTag tag, CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            g.getActorManager().write(this, tag);
        }
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    public void addTrackers(CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            g.getActorManager().startTracker(this);
        }
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo callback) {
        for (GauntletAction g : GauntletAction.values()) {
            if (g.isActing((PlayerEntity)(Object)this)) {
                g.getActorManager().modifyTracker(this, tag -> g.baseActorTick((PlayerEntity)(Object)this, tag));
            }
        }
    }

}
