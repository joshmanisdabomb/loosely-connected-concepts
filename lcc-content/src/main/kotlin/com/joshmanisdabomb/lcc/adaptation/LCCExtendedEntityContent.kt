package com.joshmanisdabomb.lcc.adaptation;

import com.joshmanisdabomb.lcc.abstracts.Temperature
import net.minecraft.entity.Entity
import net.minecraft.entity.mob.SlimeEntity

interface LCCExtendedEntityContent {

    @JvmDefault
    fun lcc_content_getTemperature() = Temperature.values().filter { (this as Entity).type.isIn(it.entityTag ?: return@filter false) }.maxOrNull()

    @JvmDefault
    fun lcc_content_getTemperatureEnergy(temperature: Temperature) = when (this) {
        is SlimeEntity -> temperature.entityEnergy.times(size)
        else -> temperature.entityEnergy
    }

}