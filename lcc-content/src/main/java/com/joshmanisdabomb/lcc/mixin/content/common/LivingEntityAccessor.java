package com.joshmanisdabomb.lcc.mixin.content.common;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Invoker("getNextAirUnderwater")
    int getAirChangeUnderwater(int air);

    @Invoker("getNextAirOnLand")
    int getAirChangeOnLand(int air);

}
