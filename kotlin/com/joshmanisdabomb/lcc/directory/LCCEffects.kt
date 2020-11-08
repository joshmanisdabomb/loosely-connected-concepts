package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.effect.FlammableStatusEffect
import com.joshmanisdabomb.lcc.effect.HurtResistanceStatusEffect
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.entity.effect.StatusEffectType.*
import net.minecraft.util.registry.Registry
import kotlin.math.pow

object LCCEffects : RegistryDirectory<StatusEffect, Unit>() {

    override val _registry by lazy { Registry.STATUS_EFFECT }

    //TODO val stun =
    val vulnerable by create { HurtResistanceStatusEffect(HARMFUL, 0x282457) { source, amplifier -> 0.9f.pow(amplifier.plus(1)) } }
    val flammable by create { FlammableStatusEffect(HARMFUL, 0x825933) }
    //TODO val radiation =

}