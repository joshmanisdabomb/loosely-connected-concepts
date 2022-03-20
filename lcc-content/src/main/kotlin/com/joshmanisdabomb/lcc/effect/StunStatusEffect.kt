package com.joshmanisdabomb.lcc.effect

import com.joshmanisdabomb.lcc.trait.LCCContentEffectTrait
import com.joshmanisdabomb.lcc.trait.LCCEffectTrait
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.input.Input
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation.MULTIPLY_TOTAL
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.entity.mob.MobEntity

class StunStatusEffect(type: StatusEffectCategory, color: Int) : StatusEffect(type, color), LCCContentEffectTrait, LCCEffectTrait {

    init {
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "2ea94220-39e7-11e9-b210-50fabd873d93", -1.0, MULTIPLY_TOTAL)
    }

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = false

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) = Unit

    override fun adjustModifierAmount(amplifier: Int, modifier: EntityAttributeModifier) = modifier.value

    override fun lcc_content_handleMobAi(entity: MobEntity, directMovement: MoveControl): Boolean {
        directMovement.tick()
        (entity as? CreeperEntity)?.fuseSpeed = -1
        return true
    }

    @Environment(EnvType.CLIENT)
    override fun lcc_content_modifyLookSpeed(player: ClientPlayerEntity, deltaX: Double, deltaY: Double): Array<Double> = arrayOf(0.0, 0.0)

    @Environment(EnvType.CLIENT)
    override fun lcc_content_modifyPlayerInput(player: ClientPlayerEntity, input: Input) {
        0.0f.also { input.movementSideways = it; input.movementForward = it; }
        false.also { input.pressingForward = it; input.sneaking = it; input.jumping = it; input.pressingRight = it; input.pressingLeft = it; input.pressingBack = it }
    }

    override fun lcc_canIncludeInExplosion(effect: StatusEffectInstance, creeper: CreeperEntity) = false

}
