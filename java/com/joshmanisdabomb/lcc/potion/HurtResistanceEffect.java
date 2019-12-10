package com.joshmanisdabomb.lcc.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import java.util.function.ToDoubleBiFunction;

public class HurtResistanceEffect extends Effect {

    private final ToDoubleBiFunction<DamageSource, Integer> multiplier;

    public HurtResistanceEffect(EffectType type, int color, ToDoubleBiFunction<DamageSource, Integer> multiplier) {
        super(type, color);
        this.multiplier = multiplier;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {}

    public double getResistanceMultiplier(DamageSource source, int amplifier) {
        return this.multiplier.applyAsDouble(source, amplifier);
    }

}
