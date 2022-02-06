package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.effect.*
import com.joshmanisdabomb.lcc.trait.LCCEffectTrait
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory.HARMFUL
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.util.registry.Registry
import kotlin.math.pow

object LCCEffects : BasicDirectory<StatusEffect, Unit>(), RegistryDirectory<StatusEffect, Unit, Unit> {

    override val registry by lazy { Registry.STATUS_EFFECT }

    override fun regId(path: String) = LCC.id(path)

    val stun by entry(::initialiser) { StunStatusEffect(HARMFUL, 0x00ffd8) }
    val vulnerable by entry(::initialiser) { HurtResistanceStatusEffect(HARMFUL, 0x282457) { source, amplifier -> 0.9f.pow(amplifier.plus(1)) } }
    val flammable by entry(::initialiser) { FlammableStatusEffect(HARMFUL, 0x825933) }
    val radiation by entry(::initialiser) { RadiationStatusEffect(HARMFUL, 0xc3db9a) }
    val fear by entry(::initialiser) { FearStatusEffect(HARMFUL, 0xd8bae8) }
    val bleeding: NoopStatusEffect by entry(::initialiser) { object : NoopStatusEffect(HARMFUL, 0x550022), LCCEffectTrait {
        override fun lcc_canIncludeInExplosion(effect: StatusEffectInstance, creeper: CreeperEntity) = false
    } }

    override fun defaultProperties(name: String) = Unit

}