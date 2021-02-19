package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.NBT_BYTE
import com.joshmanisdabomb.lcc.extensions.NBT_INT
import com.joshmanisdabomb.lcc.extensions.replaceVelocity
import com.joshmanisdabomb.lcc.extensions.toInt
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.entity.vehicle.MinecartEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.UseAction
import net.minecraft.util.math.MathHelper.*
import net.minecraft.util.math.Vec3d
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.pow

enum class GauntletAction : StringIdentifiable {

    UPPERCUT { //rising stone?
        val actorSpeedV = 1.2
        val actorSpeedH = 0.5
        val targetSpeedV = 1.9
        val damageRange = 3f..16f
        val hitbox = Vec3d(1.34, 3.5, 1.34)
        val healthCalcPow = 1f/2.3f

        override fun getActorCooldown(player: PlayerEntity, tag: CompoundTag) = 130
        override fun getActorCast(player: PlayerEntity, tag: CompoundTag) = 13
        override fun getTargetTimer(entity: Entity, tag: CompoundTag): Int? = 10
        override val actorFallBreak = 9.0
        override val targetFallBreak = 7.0

        override fun castInitial(player: PlayerEntity, tag: CompoundTag) {
            val yaw = wrapDegrees(player.yaw)
            val s = -sin(yaw * (Math.PI.toFloat() / 180f))
            val c = cos(yaw * (Math.PI.toFloat() / 180f))
            val f = sqrt(s * s + c * c)
            val xspeed = s * (actorSpeedH / f)
            val zspeed = c * (actorSpeedH / f)
            tag.putFloat("sin", s)
            tag.putFloat("cos", c)

            player.setVelocity(xspeed, actorSpeedV.pow(2.7), zspeed)
            confirmVelocity(player)
            player.fallDistance = 0f
            markFallHandler(player, true)
        }

        override fun castTick(player: PlayerEntity, tag: CompoundTag) {
            player.replaceVelocity(y = actorSpeedV * (1 - castPercent(player, tag.duration)!!).pow(2.7))
            confirmVelocity(player)
            if (tag.duration < 8) {
                val s = tag.getFloat("sin")
                val c = tag.getFloat("cos")
                val f = sqrt(s * s + c * c)
                val entities = player.world.getOtherEntities(player, player.boundingBox.expand(hitbox.x, hitbox.y, hitbox.z).offset(s * (0.7 / f), -0.75, c * (0.7 / f))) { GauntletAction.isPunchable(it) && !isTarget(it, player) }
                if (entities.any()) {
                    //TODO play custom gauntlet sound for all to hear
                    if (!player.world.isClient) player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F)

                    val mobHealths = tag.getFloat("mobHealths").plus(entities.sumOf { (it as? LivingEntity)?.maxHealth?.toDouble() ?: 0.0 }.toFloat())
                    val mobHealthsCalc = kotlin.math.floor(mobHealths.pow(healthCalcPow))
                    val diff = mobHealthsCalc - tag.getFloat("mobHealthsCalc")
                    HeartType.TEMPORARY.addHealth(player, diff)
                    tag.putFloat("mobHealths", mobHealths)
                    tag.putFloat("mobHealthsCalc", mobHealthsCalc)

                    for (entity in entities) {
                        damageReady(entity)
                        entity.damage(LCCDamage.gauntletUppercut(player), (entity as? LivingEntity)?.maxHealth?.times(0.8f)?.coerceIn(damageRange) ?: damageRange.start)
                        if (!player.world.isClient) {
                            entity.velocity = player.velocity.add(s * (actorSpeedH / f), 0.0, c * (actorSpeedH / f))
                            entity.replaceVelocity(y = targetSpeedV)
                            confirmVelocity(entity)
                        }
                        (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(LCCEffects.stun, 12, 0, false, false, true))
                        target(entity, player, tag)
                    }
                }
            }
        }

        override fun targetInitial(entity: Entity, player: PlayerEntity, tag: CompoundTag) {
            entity.replaceVelocity(y = targetSpeedV.pow(1.2))
            confirmVelocity(entity)
            entity.fallDistance = 0f
            if (entity is LivingEntity) {
                markFallHandler(entity, true)
            }
        }

        override fun targetTick(entity: Entity, tag: CompoundTag) {
            entity.replaceVelocity(y = targetSpeedV * (1 - (targetPercentage(entity, tag) ?: 0.0)).pow(1.2))
            confirmVelocity(entity)
        }
    },
    PUNCH {
        val actorSpeedH = 1.7
        val targetSpeedH = 2.2
        val damageRange = 3f..16f
        val initialHitbox = Vec3d(1.2, 1.9, 1.2)
        val shockHitbox = Vec3d(1.5, 0.5, 1.5)
        val stepHeightMap = mutableMapOf<PlayerEntity, Float>()

        override fun getActorCooldown(player: PlayerEntity, tag: CompoundTag) = 184
        override fun getActorCast(player: PlayerEntity, tag: CompoundTag): Int = ceil(chargePercentage(player, tag.remaining, 0)?.times(9) ?: 0.0)
        override fun getTargetTimer(entity: Entity, tag: CompoundTag) = 12
        override val actorFallBreak = 5.0
        override val targetFallBreak = 4.0
        override val chargeMaxTime = 45
        override val chargeBiteTime = 25
        val healthCalcPow = 1f/2.9f

        override fun castInitial(player: PlayerEntity, tag: CompoundTag) {
            val yaw = wrapDegrees(player.yaw)
            val s = -sin(yaw * (Math.PI.toFloat() / 180f))
            val c = cos(yaw * (Math.PI.toFloat() / 180f))
            val f = sqrt(s * s + c * c)
            val xspeed = s * (actorSpeedH / f)
            val zspeed = c * (actorSpeedH / f)
            tag.putFloat("sin", s)
            tag.putFloat("cos", c)
            tag.putFloat("sqrt", f)

            player.setVelocity(xspeed, 0.0, zspeed)
            confirmVelocity(player)
            player.fallDistance = 0f
            markFallHandler(player, true)

            if (player.world.isClient) {
                stepHeightMap[player] = player.stepHeight
                player.stepHeight = 1.2F
            }
        }

        override fun castTick(player: PlayerEntity, tag: CompoundTag) {
            val s = tag.getFloat("sin")
            val c = tag.getFloat("cos")
            val f = tag.getFloat("sqrt")
            val vec = if (tag.duration == 0) Vec3d(2.0, 3.0, 2.0) else initialHitbox
            val entitiesInit = player.world.getOtherEntities(player, player.boundingBox.expand(vec.x, vec.y, vec.z).offset(0.0, -0.5, 0.0)) { GauntletAction.isPunchable(it) && !isTarget(it, player) }
            if (entitiesInit.any()) {
                val entities = entitiesInit.flatMap { it.world.getOtherEntities(player, it.boundingBox.expand(shockHitbox.x, shockHitbox.y, shockHitbox.z)) { GauntletAction.isPunchable(it) && !isTarget(it, player) } }

                //TODO play custom gauntlet sound for all to hear
                if (!player.world.isClient) player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F)

                HeartType.TEMPORARY.addHealth(player, kotlin.math.floor(entities.sumOf { (it as? LivingEntity)?.maxHealth?.toDouble() ?: 0.0 }.toFloat()).pow(healthCalcPow).times(1.4f))

                for (entity in entities) {
                    damageReady(entity)
                    entity.damage(LCCDamage.gauntletPunch(player), ((entity as? LivingEntity)?.maxHealth?.times(0.8f)?.coerceIn(damageRange) ?: damageRange.start).times((chargePercent(tag.remaining)?.coerceAtLeast(0.3) ?: 0.3).toFloat()))
                    if (!player.world.isClient) {
                        entity.setVelocity(s * (targetSpeedH / f), 0.0, c * (targetSpeedH / f))
                        confirmVelocity(entity)
                    }
                    (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(LCCEffects.stun, floor((chargePercent(tag.remaining)?.coerceAtLeast(0.4) ?: 0.4).times(13)), 0, false, false, true))
                    target(entity, player, tag)
                }

                //FIXME sometimes only server sees entities in range, so step height not reset for client, probably packet
                return rebound(player, tag, s, c, f)
            }
            val thrust = actorSpeedH.times(chargePercent(tag.remaining)?.coerceAtLeast(0.3) ?: 0.3)
            if (player.horizontalCollision) {
                val vel = player.velocity
                if (vel.x == 0.0) {
                    if (vel.z.absoluteValue < thrust * 0.5) {
                        return rebound(player, tag, s, c, f)
                    }
                } else if (vel.z == 0.0) {
                    if (vel.x.absoluteValue < thrust * 0.5) {
                        return rebound(player, tag, s, c, f)
                    }
                }
            }
            player.setVelocity(s * (thrust / f), 0.0, c * (thrust / f))
            confirmVelocity(player)
            if (player.world.isClient && tag.duration >= getActorCast(player, tag) - 2) player.stepHeight = stepHeightMap.getOrDefault(player, 0.6f)
        }

        override fun targetTick(entity: Entity, tag: CompoundTag) {
            val s = tag.getFloat("sin")
            val c = tag.getFloat("cos")
            val f = tag.getFloat("sqrt")
            val thrust = targetSpeedH.times(chargePercent(tag.remaining)?.coerceAtLeast(0.3) ?: 0.3)
            val vel = entity.velocity
            val x = abs(s * (thrust / f) * 0.1)
            val z = abs(c * (thrust / f) * 0.1)
            if ((x > 0.01 && vel.x.absoluteValue < x) || (z > 0.01 && vel.z.absoluteValue < z)) {
                //if (entity.world.isClient || person isnt online) damage without getting person attributed
                damageReady(entity)
                entity.damage(LCCDamage.gauntlet_punch_wall, 18f.times((chargePercent(tag.remaining)?.coerceAtLeast(0.3) ?: 0.3).toFloat()))
                return rebound(entity, tag, s, c, f)
            }
            entity.setVelocity(s * (thrust / f), 0.0, c * (thrust / f))
            confirmVelocity(entity)
        }

        override fun forceStep(player:  PlayerEntity, tag: CompoundTag) = true

        override fun chargeTick(player: PlayerEntity, remaining: Int) {
            val chargePercent = chargePercent(remaining) ?: 1.0
            if (chargePercent < 1) {
                if (chargePercent < 0.6) player.fallDistance = 0f
                player.replaceVelocity(y = player.velocity.y * 0.66.plus(chargePercent.times(0.34)))
            }
        }

        private fun rebound(player: PlayerEntity, tag: CompoundTag, s: Float = tag.getFloat("sin"), c: Float = tag.getFloat("cos"), f: Float = tag.getFloat("sqrt")) {
            player.setVelocity(-s * (0.34 / f), 0.5, -c * (0.34 / f))
            confirmVelocity(player)
            if (player.world.isClient) player.stepHeight = stepHeightMap.getOrDefault(player, 0.6f)
            tag.duration = getActorCast(player, tag) + 1
        }

        private fun rebound(entity: Entity, tag: CompoundTag, s: Float = tag.getFloat("sin"), c: Float = tag.getFloat("cos"), f: Float = tag.getFloat("sqrt")) {
            entity.setVelocity(-s * (0.34 / f), 0.5, -c * (0.34 / f))
            confirmVelocity(entity)
            tag.duration = getTargetTimer(entity, tag) + 1
        }
    }, //jet stone?
    /*STOMP(2), //tremor stone?
    BEAM(2)*/; //beam stone

    abstract fun getActorCooldown(player: PlayerEntity, tag: CompoundTag = CompoundTag()/*actorManager.fromTracker(player)*/): Int
    abstract fun getActorCast(player: PlayerEntity, tag: CompoundTag = CompoundTag()/*actorManager.fromTracker(player)*/): Int?
    open val actorFallBreak: Double? = null
    open fun getTargetTimer(entity: Entity, tag: CompoundTag): Int? = null
    open val targetFallBreak: Double? = null

    open val chargeMaxTime = 0
    open val chargeBiteTime = 0
    open val chargeAction = UseAction.BOW
    open val chargeCancelCooldown = 0.4

    override fun asString() = name.toLowerCase()

    @JvmOverloads
    fun isActing(player: PlayerEntity, tick: Int = 0/*actorManager.fromTracker(player).duration*/) = tick > -1

    fun act(player: PlayerEntity, remaining: Int = 0, only: Boolean = !isActing(player)): Boolean {
        if (only) {
            val tag = CompoundTag().also { it.duration = 0; it.remaining = remaining }
            this.castInitial(player, tag)
            //actorManager.toTracker(player, tag)
            return true
        }
        return false
    }

    fun isTarget(entity: Entity) = false//targetManager?.fromTracker(entity)?.keys?.any() ?: false

    fun isTarget(entity: Entity, player: PlayerEntity) = false//targetManager?.fromTracker(entity)?.keys?.any { it == player.uuidAsString } ?: false

    fun canTarget(): Boolean = false//targetManager != null

    fun target(entity: Entity, player: PlayerEntity, tag: CompoundTag = CompoundTag()): Boolean {
        val new = tag.copy().also { it.duration = 0; it.putUuid("by", player.uuid) }
        this.targetInitial(entity, player, new)
        //targetManager?.modifyTracker(entity) { it.copy().also { it.put(player.uuidAsString, new) } }
        //if (entity is ServerPlayerEntity && entity.deathTime <= 0) entity.networkHandler.sendPacket(EntityTrackerUpdateS2CPacket(entity.entityId, entity.dataTracker, true))
        return true
    }

    abstract fun castInitial(player: PlayerEntity, tag: CompoundTag)

    abstract fun castTick(player: PlayerEntity, tag: CompoundTag)

    open fun chargeTick(player: PlayerEntity, remaining: Int) = Unit

    open fun targetInitial(entity: Entity, player: PlayerEntity, tag: CompoundTag) = Unit

    open fun targetTick(entity: Entity, tag: CompoundTag) = Unit

    fun cooldown(player: PlayerEntity): Int {
        //if (actorManager.fromTracker(player).getBoolean("cancelled")) return if (!player.isCreative) ceil(getActorCooldown(player).times(chargeCancelCooldown)) else cooldownCreative(player, true)
        return if (!player.isCreative) getActorCooldown(player) else max(getActorCast(player) ?: -1, cooldownCreative(player, false))
    }

    open fun cooldownCreative(player: PlayerEntity, cancelled: Boolean) = isChargeable().toInt(40.div(cancelled.toInt(1, 2)), -1)

    protected fun castPercent(player: PlayerEntity, tick: Int): Double? {
        val actorCast = this.getActorCast(player)
        if (actorCast == null || actorCast <= 0) return null
        val percent = tick.toDouble() / actorCast
        if (percent !in 0.0..1.0) return null
        return percent
    }

    fun castPercentage(player: PlayerEntity, offset: Int = 0) = 0.0//castPercent(player, actorManager.fromTracker(player).duration.plus(offset).coerceAtLeast(0))

    fun isCasting(player: PlayerEntity, tick: Int = 0/*actorManager.fromTracker(player).duration*/) = castPercent(player, tick) != null

    protected fun cooldownPercent(player: PlayerEntity, tick: Int): Double? {
        val effectiveCooldown = this.cooldown(player) - (this.getActorCast(player) ?: 0)
        if (effectiveCooldown <= 0) return null
        val percent = (tick.toDouble() - (this.getActorCast(player) ?: 0)) / effectiveCooldown
        if (percent !in 0.0..1.0) return null
        return percent
    }

    fun cooldownPercentage(player: PlayerEntity, offset: Int = 0) = 0.0//cooldownPercent(player, actorManager.fromTracker(player).duration.plus(offset).coerceAtLeast(0))

    fun isCooldown(player: PlayerEntity, tick: Int = 0/*actorManager.fromTracker(player).duration*/) = cooldownPercent(player, tick) != null

    fun isChargeable() = chargeMaxTime > 0

    protected fun chargePercent(remaining: Int): Double? {
        if (!this.isChargeable()) return null
        return ((chargeMaxTime - remaining) / chargeBiteTime.toDouble()).coerceIn(0.0, 1.0)
    }

    fun chargePercentage(player: PlayerEntity, remaining: Int = player.itemUseTimeLeft, offset: Int = 0) = chargePercent(remaining.plus(offset).coerceAtLeast(0))

    protected fun targetPercentage(entity: Entity, tag: CompoundTag, tick: Int = tag.duration): Double? {
        val targetTimer = this.getTargetTimer(entity, tag)
        if (targetTimer == null || targetTimer <= 0) return null
        val percent = tick.toDouble() / targetTimer
        if (percent !in 0.0..1.0) return null
        return percent
    }

    fun baseActorTick(player: PlayerEntity, tag: CompoundTag): CompoundTag {
        if (tag.duration >= cooldown(player)) return CompoundTag()
        if (isCasting(player, tag.duration)) {
            this.castTick(player, tag)
        }
        return tag.also { it.duration++ }
    }

    fun baseTargetTick(entity: Entity, tag: CompoundTag): CompoundTag {
        val key = tag.keys.minByOrNull { tag.getCompound(it).duration } ?: return tag
        val last = tag.getCompound(key)
        this.targetTick(entity, last)
        tag.put(key, last)
        return tag.also { it.keys.forEach { if (tag.getCompound(it).duration++ >= getTargetTimer(entity, tag.getCompound(it)) ?: -1) tag.remove(it) } }
    }

    fun handleFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, actor: Boolean): Int? = if (actor) handleActorFall(entity, fallDistance, damageMultiplier, actor) else handleTargetFall(entity, fallDistance, damageMultiplier, actor)

    open fun handleActorFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, actor: Boolean): Int? = if (actorFallBreak == null) null else ceil((fallDistance - 3 - actorFallBreak!! - (entity.getStatusEffect(StatusEffects.JUMP_BOOST)?.amplifier?.plus(1) ?: 0)) * damageMultiplier)

    open fun handleTargetFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, actor: Boolean): Int? = if (targetFallBreak == null) null else ceil((fallDistance - 3 - targetFallBreak!! - (entity.getStatusEffect(StatusEffects.JUMP_BOOST)?.amplifier?.plus(1) ?: 0)) * damageMultiplier)

    fun markFallHandler(entity: LivingEntity, actor: Boolean) = Unit//entity.dataTracker.set(LCCTrackers.gauntletFallHandler, (ordinal + 1 + actor.toInt()).toByte())

    fun cancel(player: PlayerEntity) = Unit//actorManager.toTracker(player, CompoundTag().also { it.duration = (getActorCast(player) ?: 0) + 1; it.putBoolean("cancelled", true) })

    open fun forceStep(player: PlayerEntity, tag: CompoundTag) = false

    protected fun confirmVelocity(entity: Entity) { entity.velocityModified = true; entity.velocityDirty = true }

    protected fun damageReady(entity: Entity) {
        if (entity is LivingEntity) {
            entity.hurtTime = 0
            entity.timeUntilRegen = 0
        }
    }

    companion object {
        @JvmStatic
        fun handleFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, handler: Int): Int? {
            return values().getOrNull((handler - 1) / 2)?.handleFall(entity, fallDistance, damageMultiplier, handler % 2 == 0)
        }

        @JvmStatic
        fun isPunchable(entity: Entity): Boolean {
            when (entity) {
                is LivingEntity -> return entity.deathTime <= 0 && (entity !is PlayerEntity || (!entity.isCreative && !entity.isSpectator))
                is BoatEntity -> return true
                is MinecartEntity -> return true
            }
            return false
        }

        var CompoundTag.ability get() = if (contains("lcc_gauntlet_ability", NBT_BYTE)) values()[getByte("lcc_gauntlet_ability").toInt().minus(1)] else UPPERCUT; set(v) = putByte("lcc_gauntlet_ability", v.ordinal.plus(1).toByte())
    }

    var CompoundTag.duration get() = if (contains("duration", NBT_INT)) getInt("duration") else -1; set(v) = putInt("duration", v)
    var CompoundTag.remaining get() = if (contains("remaining", NBT_INT)) getInt("remaining") else -1; set(v) = putInt("remaining", v)

}