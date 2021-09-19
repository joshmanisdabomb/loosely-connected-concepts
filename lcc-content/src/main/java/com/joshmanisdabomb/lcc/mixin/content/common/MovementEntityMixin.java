package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.cache.EntityValueCache;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MovementEntityMixin implements EntityValueCache {

    private Vec3d lcc_fullVelocityBeforeCollisions = Vec3d.ZERO;
    private Vec3d lcc_fullVelocity = Vec3d.ZERO;

    @Inject(method = "adjustMovementForCollisions", at = @At("RETURN"))
    private void adjustMovementForCollisions(Vec3d movement, CallbackInfoReturnable<Vec3d> info) {
        lcc_fullVelocityBeforeCollisions = movement;
        lcc_fullVelocity = info.getReturnValue();
    }

    @NotNull
    @Override
    public Vec3d getLcc_fullVelocity() {
        return lcc_fullVelocity;
    }

    @NotNull
    @Override
    public Vec3d getLcc_fullVelocityBeforeCollides() {
        return lcc_fullVelocityBeforeCollisions;
    }

}
