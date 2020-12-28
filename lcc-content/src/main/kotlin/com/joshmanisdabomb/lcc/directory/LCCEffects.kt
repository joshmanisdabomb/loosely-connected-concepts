package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.effect.FlammableStatusEffect
import com.joshmanisdabomb.lcc.effect.HurtResistanceStatusEffect
import com.joshmanisdabomb.lcc.effect.StunStatusEffect
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType.HARMFUL
import net.minecraft.util.registry.Registry
import kotlin.math.pow

object LCCEffects : RegistryDirectory<StatusEffect, Unit>() {

    override val _registry by lazy { Registry.STATUS_EFFECT }

    override fun id(path: String) = LCC.id(path)

    val stun by create { StunStatusEffect(HARMFUL, 0x00ffd8) }
    val vulnerable by create { HurtResistanceStatusEffect(HARMFUL, 0x282457) { source, amplifier -> 0.9f.pow(amplifier.plus(1)) } }
    val flammable by create { FlammableStatusEffect(HARMFUL, 0x825933) }
    //TODO val radiation =

}