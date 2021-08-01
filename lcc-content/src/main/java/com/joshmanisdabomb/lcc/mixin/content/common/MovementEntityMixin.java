package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MovementEntityMixin {

    private Vec3d lcc_fullVelocity = Vec3d.ZERO;

    @Inject(method = "adjustMovementForCollisions", at = @At("HEAD"))
    private void adjustMovementForCollisions(Vec3d movement, CallbackInfoReturnable<Vec3d> info) {
        lcc_fullVelocity = movement;
    }

}
