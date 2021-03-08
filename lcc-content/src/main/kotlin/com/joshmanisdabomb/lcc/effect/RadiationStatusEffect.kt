package com.joshmanisdabomb.lcc.effect

import com.joshmanisdabomb.lcc.directory.LCCComponents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

class RadiationStatusEffect(type: StatusEffectType, color: Int) : StatusEffect(type, color) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        LCCComponents.radiation.maybeGet(entity).ifPresent { it.exposure = it.exposure.plus(amplifier.plus(1).toDouble().let { it*it }.toFloat().times(0.0005f)) }
    }

}
