package com.joshmanisdabomb.lcc.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import java.lang.Math.pow

class RadiationStatusEffect(type: StatusEffectType, color: Int) : StatusEffect(type, color) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = false

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) = Unit

    fun modifyExposure(exposure: Float, entity: LivingEntity, amplifier: Int) = exposure.plus(pow(amplifier.plus(1).toDouble(), 2.5).toFloat().times(0.00027f))

}
