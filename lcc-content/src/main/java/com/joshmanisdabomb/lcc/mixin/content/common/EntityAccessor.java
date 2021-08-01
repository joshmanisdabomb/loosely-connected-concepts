package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {

    @Accessor("lcc_fullVelocity")
    Vec3d getFullVelocity();

}
