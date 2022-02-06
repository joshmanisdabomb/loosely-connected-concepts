package com.joshmanisdabomb.lcc.trait

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.CreeperEntity

interface LCCEffectTrait {

    fun lcc_canIncludeInExplosion(effect: StatusEffectInstance, creeper: CreeperEntity) = true

}