package com.joshmanisdabomb.lcc.trait;

import com.joshmanisdabomb.lcc.abstracts.Temperature
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.SlimeEntity

interface LCCContentEntityTrait {

    @JvmDefault
    fun lcc_content_getTemperature() = Temperature.values().filter { (this as Entity).type.isIn(it.entityTag ?: return@filter false) }.maxOrNull()

    @JvmDefault
    fun lcc_content_getTemperatureEnergy(temperature: Temperature) = when (this) {
        is SlimeEntity -> temperature.entityEnergy.times(size)
        else -> temperature.entityEnergy
    }

    @JvmDefault
    fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float) = after

    @JvmDefault
    fun lcc_content_isCombatResistant(against: LivingEntity, effectivity: ToolEffectivity) = (this as Entity).type.isIn(effectivity.resistant)

    @JvmDefault
    fun lcc_content_isCombatEffective(against: LivingEntity, effectivity: ToolEffectivity) = (this as Entity).type.isIn(effectivity.combat)

}