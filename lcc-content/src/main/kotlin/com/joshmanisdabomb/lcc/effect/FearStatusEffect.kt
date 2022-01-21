package com.joshmanisdabomb.lcc.effect

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.entity.PsychoPigEntity
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.trait.LCCContentEffectTrait
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.MathHelper

class FearStatusEffect(type: StatusEffectCategory, color: Int) : StatusEffect(type, color), LCCContentEffectTrait {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        if (!entity.world.isClient && (entity as? PlayerEntity)?.isSurvival != false) {
            lookAtTarget(entity, 0.0f)
        }
    }

    override fun lcc_content_canTarget(entity: LivingEntity, target: Entity) = entity.isAlive && target.isAlive && (target as? PsychoPigEntity)?.aggroTarget == entity

    @Environment(EnvType.CLIENT)
    override fun lcc_content_modifyLookSpeed(player: ClientPlayerEntity, deltaX: Double, deltaY: Double): Array<Double>? {
        if (player.isSurvival) {
            lookAtTarget(player, MinecraftClient.getInstance().tickDelta)
            return arrayOf(0.0, 0.0)
        }
        return null
    }

    private fun lookAtTarget(entity: LivingEntity, lerp: Float = 1.0f) {
        val target = LCCComponents.targeted_effects.maybeGet(entity).orElse(null)?.getFirstTarget(this) ?: return entity.removeStatusEffect(this).let {}
        var h = Float.NaN
        var i = Float.NaN
        var ph = Float.NaN
        var pi = Float.NaN
        if (lerp > 0.0f) {
            val d = target.x - entity.x
            val e = target.eyeY - entity.eyeY
            val f = target.z - entity.z
            val g = MathHelper.sqrt((d * d + f * f).toFloat()).toDouble()
            h = MathHelper.wrapDegrees((-(MathHelper.atan2(e, g) * 57.2957763671875)).toFloat())
            i = MathHelper.wrapDegrees((MathHelper.atan2(f, d) * 57.2957763671875).toFloat() - 90.0f)
            if (lerp >= 1.0f) {
                entity.yaw = i
                entity.pitch = h
                entity.prevYaw = i
                entity.prevPitch = h
                return
            }
        }
        if (lerp < 1.0f) {
            val pd = target.prevX - entity.prevX
            val pe = target.prevY.plus(target.standingEyeHeight) - entity.eyeY
            val pf = target.prevZ - entity.prevZ
            val pg = MathHelper.sqrt((pd * pd + pf * pf).toFloat()).toDouble()
            ph = MathHelper.wrapDegrees((-(MathHelper.atan2(pe, pg) * 57.2957763671875)).toFloat())
            pi = MathHelper.wrapDegrees((MathHelper.atan2(pf, pd) * 57.2957763671875).toFloat() - 90.0f)
            if (lerp <= 0.0f) {
                entity.yaw = pi
                entity.pitch = ph
                entity.prevYaw = pi
                entity.prevPitch = ph
                return
            }
        }
        entity.yaw = MathHelper.lerpAngleDegrees(lerp, pi, i)
        entity.pitch = MathHelper.lerpAngleDegrees(lerp, ph, h)
        entity.prevYaw = MathHelper.lerpAngleDegrees(lerp, pi, i)
        entity.prevPitch = MathHelper.lerpAngleDegrees(lerp, ph, h)
    }

}
