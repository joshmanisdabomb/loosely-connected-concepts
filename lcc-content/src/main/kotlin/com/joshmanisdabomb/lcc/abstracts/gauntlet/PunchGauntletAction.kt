package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.replaceVelocity
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.properties.Delegates

object PunchGauntletAction : GauntletAction<PunchGauntletAction.PunchGauntletActorInstance>(), TargetableGauntletAction<PunchGauntletAction.PunchGauntletActorInstance, PunchGauntletAction.PunchGauntletTargetInstance> {

    override fun newActorInstance(actor: PlayerEntity) = PunchGauntletActorInstance(actor)
    override fun initActorInstance(instance: PunchGauntletActorInstance) = Unit

    override fun newTargetInstance(target: Entity) = PunchGauntletTargetInstance(target)
    override fun initTargetInstance(instance: PunchGauntletTargetInstance, actor: PlayerEntity, info: PunchGauntletActorInstance) = Unit

    override fun actorFall(actor: PlayerEntity, distance: Float, multiplier: Float) = handleFall(actor, distance, multiplier, 5f)
    override fun targetFall(target: LivingEntity, distance: Float, multiplier: Float) = handleFall(target, distance, multiplier, 4f)

    override val maxChargeTime = 45
    override val biteChargeTime = 25

    override fun chargeTick(player: PlayerEntity, remaining: Int) {
        val chargePercent = chargePercent(remaining)
        if (chargePercent < 1.0) {
            if (chargePercent < 0.6) player.fallDistance = 0f
            player.replaceVelocity(y = player.velocity.y * 0.66.plus(chargePercent.times(0.34)))
        }
    }

    fun rebound(entity: Entity, sin: Float, cos: Float, f: Float): Boolean {
        entity.setVelocity(-sin * (0.34 / f), 0.5, -cos * (0.34 / f))
        confirmVelocity(entity)
        return true
    }

    class PunchGauntletActorInstance(actor: PlayerEntity) : GauntletActorInstance(actor) {

        override val action: GauntletAction<*> = PunchGauntletAction

        val speedH = 2.6
        val damageRange = 3f..10f
        val damageMultiplier = 0.9f
        val initialHitbox = Vec3d(1.2, 1.9, 1.2)
        val shockHitbox = Vec3d(1.5, 0.5, 1.5)
        val health = TemporaryHealthCalculator(0.5)

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

            actor.setVelocity(xspeed, 0.001, zspeed)
            confirmVelocity(actor)
            actor.fallDistance = 0f
            markFallHandler(actor, true)

            //TODO step height
            return false
        }

        override fun tick(): Boolean {
            val vec = if (cast == 0) Vec3d(2.0, 3.0, 2.0) else initialHitbox
            val entitiesInit = actor.world.getOtherEntities(actor, actor.boundingBox.expand(vec.x, vec.y, vec.z).offset(0.0, -0.5, 0.0)) { canBeTarget(it, actor) }
            if (entitiesInit.any()) {
                val entities = entitiesInit.flatMap { it.world.getOtherEntities(actor, it.boundingBox.expand(shockHitbox.x, shockHitbox.y, shockHitbox.z)) { canBeTarget(it, actor) } }

                //TODO play custom gauntlet sound for all to hear
                if (!actor.world.isClient) actor.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F)

                health.add(actor, entities.sumOf { (it as? LivingEntity)?.maxHealth?.toDouble() ?: 0.0 })

                for (entity in entities) {
                    damageReady(entity)
                    entity.damage(LCCDamage.gauntletPunch(actor), cappedDamage(entity, damageMultiplier, damageRange).times(chargePercent.coerceAtLeast(0.3)).toFloat())
                    (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(LCCEffects.stun, MathHelper.floor(chargePercent.coerceAtLeast(0.4).times(13)), 0, false, false, true))
                    target(entity, actor, this)
                }

                //FIXME sometimes only server sees entities in range, so step height not reset for client, probably packet
                return rebound(actor, sin, cos, f)
            }
            val thrust = speedH.times(chargePercent.coerceAtLeast(0.3)).times(1-castPercent.times(0.5)).times((cast >= maxCast - 1).transform(0.5f, 1f))
            if (actor.horizontalCollision) {
                val vel = actor.velocity
                if (vel.x == 0.0) {
                    if (vel.z.absoluteValue < thrust * 0.5) {
                        return rebound(actor, sin, cos, f)
                    }
                } else if (vel.z == 0.0) {
                    if (vel.x.absoluteValue < thrust * 0.5) {
                        return rebound(actor, sin, cos, f)
                    }
                }
            }
            actor.setVelocity(sin * (thrust / f), 0.001, cos * (thrust / f))
            confirmVelocity(actor)
            //TODO step height
            return false
        }

        override fun getMaxCooldownTime(cancelled: Boolean) = cancelled.transformInt(10, if (actor.isSurvival) 184 else 0)

        override fun getMaxCastTime(cancelled: Boolean) = cancelled.transformInt(-1, MathHelper.ceil(chargePercent.times(7)) + 2)

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

    class PunchGauntletTargetInstance(entity: Entity) : GauntletTargetInstance<PunchGauntletActorInstance>(entity) {

        override val action = PunchGauntletAction

        val speedH = 2.2

        var sin by Delegates.notNull<Float>()
        var cos by Delegates.notNull<Float>()
        val f by lazy { MathHelper.sqrt(sin * sin + cos * cos) }
        var charge by Delegates.notNull<Double>()

        override fun initial(actor: PlayerEntity, info: PunchGauntletActorInstance): Boolean {
            sin = info.sin
            cos = info.cos
            charge = info.chargePercent
            if (!actor.world.isClient) {
                entity.setVelocity(sin * (speedH / f), 0.0, cos * (speedH / f))
                confirmVelocity(entity)
            }
            if (entity is LivingEntity) markFallHandler(entity, false)
            return false
        }

        override fun tick(): Boolean {
            val thrust = speedH.times(charge.coerceAtLeast(0.3)).times(1-percent.times(0.5)).times((ticks >= maxTicks - 1).transform(0.5f, 1f))
            val vel = entity.velocity
            val x = abs(sin * (thrust / f) * 0.1)
            val z = abs(cos * (thrust / f) * 0.1)
            if ((x > 0.01 && vel.x.absoluteValue < x) || (z > 0.01 && vel.z.absoluteValue < z)) {
                //TODO if (entity.world.isClient || person isnt online) damage without getting person attributed
                damageReady(entity)
                entity.damage(LCCDamage.gauntlet_punch_wall, 18f.times(charge.coerceAtLeast(0.3)).toFloat())
                return rebound(entity, sin, cos, f)
            }
            entity.setVelocity(sin * (thrust / f), 0.0, cos * (thrust / f))
            confirmVelocity(entity)
            return false
        }

        override fun getTime(actor: PlayerEntity, info: PunchGauntletActorInstance) = 12

        override fun readFromNbt(tag: CompoundTag) {
            sin = tag.getFloat("Sin")
            cos = tag.getFloat("Cos")
            charge = tag.getDouble("Charge")
        }

        override fun writeToNbt(tag: CompoundTag) {
            tag.putFloat("Sin", sin)
            tag.putFloat("Cos", cos)
            tag.putDouble("Charge", charge)
        }

    }

}