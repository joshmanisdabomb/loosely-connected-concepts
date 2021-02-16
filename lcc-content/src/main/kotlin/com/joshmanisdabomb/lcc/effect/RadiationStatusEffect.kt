package com.joshmanisdabomb.lcc.effect

import com.joshmanisdabomb.lcc.directory.LCCComponents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import kotlin.math.pow

class RadiationStatusEffect(type: StatusEffectType, color: Int) : StatusEffect(type, color) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        LCCComponents.radiation.maybeGet(entity).ifPresent { it.exposure = it.exposure.plus(amplifier.plus(1).toDouble().pow(2.5).toFloat().times(0.00027f)) }
    }

}
