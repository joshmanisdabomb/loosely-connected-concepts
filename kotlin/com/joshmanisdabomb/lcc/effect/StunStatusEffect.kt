package com.joshmanisdabomb.lcc.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation.MULTIPLY_TOTAL
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

class StunStatusEffect(type: StatusEffectType, color: Int) : StatusEffect(type, color) {

    init {
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "2ea94220-39e7-11e9-b210-50fabd873d93", -1.0, MULTIPLY_TOTAL)
    }

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) = Unit

    override fun adjustModifierAmount(amplifier: Int, modifier: EntityAttributeModifier) = modifier.value

}
