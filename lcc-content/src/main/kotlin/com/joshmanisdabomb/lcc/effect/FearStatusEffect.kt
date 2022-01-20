package com.joshmanisdabomb.lcc.effect

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.trait.LCCContentEffectTrait
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.MathHelper

class FearStatusEffect(type: StatusEffectCategory, color: Int) : StatusEffect(type, color), LCCContentEffectTrait {

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        if ((entity as? PlayerEntity)?.isSurvival != false) {
            val target = LCCComponents.targeted_effects.maybeGet(entity).orElse(null)?.getFirstAliveTarget(this)?.takeIf { it.isAlive } ?: return entity.removeStatusEffect(this).let {}
            val d = target.x - entity.x
            val e = target.eyeY - entity.eyeY
            val f = target.z - entity.z
            val g = MathHelper.sqrt((d * d + f * f).toFloat()).toDouble()
            val h = MathHelper.wrapDegrees((-(MathHelper.atan2(e, g) * 57.2957763671875)).toFloat())
            val i = MathHelper.wrapDegrees((MathHelper.atan2(f, d) * 57.2957763671875).toFloat() - 90.0f)
            entity.yaw = i
            entity.pitch = h
            val pd = target.prevX - entity.prevX
            val pe = target.prevY.plus(target.standingEyeHeight) - entity.prevY.plus(target.standingEyeHeight)
            val pf = target.prevZ - entity.prevZ
            val pg = MathHelper.sqrt((pd * pd + pf * pf).toFloat()).toDouble()
            val ph = MathHelper.wrapDegrees((-(MathHelper.atan2(pe, pg) * 57.2957763671875)).toFloat())
            val pi = MathHelper.wrapDegrees((MathHelper.atan2(pf, pd) * 57.2957763671875).toFloat() - 90.0f)
            entity.prevYaw = pi
            entity.prevPitch = ph
        }
    }

    @Environment(EnvType.CLIENT)
    override fun lcc_content_modifyLookSpeed(player: ClientPlayerEntity, deltaX: Double, deltaY: Double): Array<Double>? = if (player.isSurvival) arrayOf(0.0, 0.0) else null

}
