package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.replaceVelocity
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import kotlin.math.pow
import kotlin.properties.Delegates

object UppercutGauntletAction : GauntletAction2<UppercutGauntletAction.UppercutGauntletActorInstance>(), TargetableGauntletAction<UppercutGauntletAction.UppercutGauntletActorInstance, UppercutGauntletAction.UppercutGauntletTargetInstance> {

    override fun newActorInstance(actor: PlayerEntity) = UppercutGauntletActorInstance(actor)
    override fun initActorInstance(instance: UppercutGauntletActorInstance) = Unit

    override fun newTargetInstance(target: Entity) = UppercutGauntletTargetInstance(target)
    override fun initTargetInstance(instance: UppercutGauntletTargetInstance, actor: PlayerEntity, info: UppercutGauntletActorInstance) = Unit

    override fun actorFall(actor: PlayerEntity, distance: Float, multiplier: Float) = handleFall(actor, distance, multiplier, 9f)
    override fun targetFall(target: LivingEntity, distance: Float, multiplier: Float) = handleFall(target, distance, multiplier, 6f)

    class UppercutGauntletActorInstance(actor: PlayerEntity) : GauntletActorInstance(actor) {

        override val action: GauntletAction2<*> = UppercutGauntletAction

        val speedH = 0.5
        val speedV = 1.2
        val damageRange = 3f..16f
        val damageMultiplier = 0.8f
        val hitbox = Vec3d(1.34, 3.5, 1.34)
        val health = TemporaryHealthCalculator(1/1.8)

        var sin by Delegates.notNull<Float>()
        var cos by Delegates.notNull<Float>()
        val f by lazy { MathHelper.sqrt(sin * sin + cos * cos) }

        override fun initial(remaining: Int, cancelled: Boolean): Boolean {
            val yaw = MathHelper.wrapDegrees(actor.yaw)
            val s = -MathHelper.sin(yaw * (Math.PI.toFloat() / 180f))
            val c = MathHelper.cos(yaw * (Math.PI.toFloat() / 180f))
            val f = MathHelper.sqrt(s * s + c * c)
            val xspeed = s * (speedH / f)
            val zspeed = c * (speedH / f)
            sin = s
            cos = c

            actor.setVelocity(xspeed, speedV.pow(2.7), zspeed)
            confirmVelocity(actor)
            actor.fallDistance = 0f
            markFallHandler(actor, true)
            return false
        }

        override fun tick(): Boolean {
            actor.replaceVelocity(y = speedV * (1 - castPercent).pow(2.7))
            confirmVelocity(actor)
            if (cast < 8) {
                val entities = actor.world.getOtherEntities(actor, actor.boundingBox.expand(hitbox.x, hitbox.y, hitbox.z).offset(sin * (0.7 / f), -0.75, cos * (0.7 / f))) { canBeTarget(it, actor) }
                if (entities.any()) {
                    //TODO play custom gauntlet sound for all to hear
                    if (!actor.world.isClient) actor.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F)

                    health.add(actor, entities.sumOf { (it as? LivingEntity)?.maxHealth?.toDouble() ?: 0.0 })

                    for (entity in entities) {
                        damageReady(entity)
                        entity.damage(LCCDamage.gauntletUppercut(actor), cappedDamage(entity, damageMultiplier, damageRange))
                        (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(LCCEffects.stun, 12, 0, false, false, true))
                        target(entity, actor, this)
                    }
                }
            }
            return false
        }

        override fun getMaxCooldownTime(cancelled: Boolean) = if (actor.isCreative) 0 else 130

        override fun getMaxCastTime(cancelled: Boolean) = 13

        override fun readFromNbt(tag: CompoundTag) {
            sin = tag.getFloat("Sin")
            cos = tag.getFloat("Cos")
            health.readFromNbt(tag)
        }

        override fun writeToNbt(tag: CompoundTag) {
            tag.putFloat("Sin", sin)
            tag.putFloat("Cos", cos)
            health.writeToNbt(tag)
        }

    }

    class UppercutGauntletTargetInstance(entity: Entity) : GauntletTargetInstance<UppercutGauntletActorInstance>(entity) {

        override val action = UppercutGauntletAction

        val speedH = 0.5
        val speedV = 1.9

        var sin by Delegates.notNull<Float>()
        var cos by Delegates.notNull<Float>()
        val f by lazy { MathHelper.sqrt(sin * sin + cos * cos) }

        override fun initial(actor: PlayerEntity, info: UppercutGauntletActorInstance): Boolean {
            sin = info.sin
            cos = info.cos
            entity.velocity = actor.velocity.add(sin * (speedH / f), 0.0, cos * (speedH / f))
            entity.replaceVelocity(y = speedV.pow(1.2))
            confirmVelocity(entity)
            entity.fallDistance = 0f
            if (entity is LivingEntity) markFallHandler(entity, false)
            return false
        }

        override fun tick(): Boolean {
            entity.replaceVelocity(y = speedV.pow(1.2) * (1 - percent).pow(5))
            confirmVelocity(entity)
            return false
        }

        override fun getTime(actor: PlayerEntity, info: UppercutGauntletActorInstance) = 10

        override fun readFromNbt(tag: CompoundTag) {
            sin = tag.getFloat("Sin")
            cos = tag.getFloat("Cos")
        }

        override fun writeToNbt(tag: CompoundTag) {
            tag.putFloat("Sin", sin)
            tag.putFloat("Cos", cos)
        }

    }

}