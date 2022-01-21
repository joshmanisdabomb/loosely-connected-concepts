package com.joshmanisdabomb.lcc.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory

class NoopStatusEffect(type: StatusEffectCategory, color: Int) : StatusEffect(type, color) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = false

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) = Unit

}