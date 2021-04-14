package com.joshmanisdabomb.lcc.mixin.base.common;

import com.joshmanisdabomb.lcc.adaptation.boat.LCCBoat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {

    public BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"), cancellable = true)
    public void fall(CallbackInfo info) {
        if (this instanceof LCCBoat) {
            ((LCCBoat)this).getLcc_boat().getDrops().invoke((BoatEntity)(Object)this);
            this.fallDistance = 0.0F;
            info.cancel();
        }
    }

}
