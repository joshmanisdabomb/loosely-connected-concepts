package com.joshmanisdabomb.lcc.effect

import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.AttributeContainer
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectType
import net.minecraft.tag.BlockTags
import kotlin.math.pow

class FlammableStatusEffect(type: StatusEffectType, color: Int) : HurtResistanceStatusEffect(type, color, { source, amplifier -> if (source.isFire) 0.9f.pow(amplifier.plus(1)) else 1f }) {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        val fireTicks = entity.fireTicks
        val fireLength = (amplifier + 5) * 30

        if (fireTicks < fireLength - 10 && entity.world.method_29556(entity.boundingBox.contract(0.001)).anyMatch { it.isIn(BlockTags.FIRE) || it.isOf(Blocks.LAVA) }) {
            entity.fireTicks = fireLength
        }
        if (fireTicks % 20 != 0 && fireTicks % Math.max(20 / (amplifier + 2), 1) == 0) {
            entity.damage(DamageSource.ON_FIRE, 1.0f)
        }
    }

    override fun onApplied(entity: LivingEntity, attributes: AttributeContainer, amplifier: Int) {
        if (entity.isFireImmune) {
            entity.removeStatusEffect(this)
            return
        }
        super.onApplied(entity, attributes, amplifier)
    }

}
