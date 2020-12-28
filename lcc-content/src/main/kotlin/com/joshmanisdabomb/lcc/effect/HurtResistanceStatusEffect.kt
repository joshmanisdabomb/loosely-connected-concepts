package com.joshmanisdabomb.lcc.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

open class HurtResistanceStatusEffect(type: StatusEffectType, color: Int, private val resistance: (source: DamageSource, amplifier: Int) -> Float) : StatusEffect(type, color) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = false

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) = Unit

    fun getResistanceMultiplier(source: DamageSource, amplifier: Int) = resistance(source, amplifier)

}