package com.joshmanisdabomb.lcc.mixin.hooks.common;

import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;
import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityAccessor {

    @Invoker("<init>")
    static FallingBlockEntity create(World world, double x, double y, double z, BlockState block) {
        throw new AssertionError();
    }

}
