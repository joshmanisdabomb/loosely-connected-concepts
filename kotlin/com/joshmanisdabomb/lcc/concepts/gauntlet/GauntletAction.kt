package com.joshmanisdabomb.lcc.concepts.gauntlet

import com.joshmanisdabomb.lcc.NBT_INT
import com.joshmanisdabomb.lcc.concepts.hearts.HeartType
import com.joshmanisdabomb.lcc.directory.LCCTrackers
import com.joshmanisdabomb.lcc.entity.data.EntityDataManager
import com.joshmanisdabomb.lcc.replaceVelocity
import com.joshmanisdabomb.lcc.toInt
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.entity.vehicle.MinecartEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.MathHelper.*
import net.minecraft.util.math.Vec3d
import kotlin.math.pow

enum class GauntletAction(val actorManager: EntityDataManager<CompoundTag>, val targetManager: EntityDataManager<CompoundTag>? = null) : StringIdentifiable {

    UPPERCUT(EntityDataManager("gauntlet_uppercut", LCCTrackers.gauntletUppercut), EntityDataManager("gauntlet_uppercutee", LCCTrackers.gauntletUppercutee)) { //rising stone?
        val actorSpeedV = 1.2
        val actorSpeedH = 0.5
        override val actorCooldown = 130
        override val actorCast = 13
        override val actorFallBreak = 9.0
        override val targetTimer = 10
        val targetSpeedV = 1.9
        val damageRange = 3f..16f
        val hitbox = Vec3d(1.1, 3.0, 1.1)
        val healthCalcPow = 1f/2.3f

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
            player.velocityDirty = true
            player.fallDistance = 0f
            markFallHandler(player, true)
        }

        override fun castTick(player: PlayerEntity, tag: CompoundTag) {
            player.replaceVelocity(y = actorSpeedV * (1 - castPercentage(tag.duration)!!).pow(2.7))
            player.velocityDirty = true
            if (tag.duration < 8) {
                val s = tag.getFloat("sin")
                val c = tag.getFloat("cos")
                val f = sqrt(s * s + c * c)
                val entities = player.world.getOtherEntities(player, player.boundingBox.expand(hitbox.x, hitbox.y, hitbox.z)/*.offset(s * (0.95 / f), -1.0, c * (0.95 / f))*/) { GauntletAction.isPunchable(it) && !isTarget(it, player) }
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
                        entity.damage(DamageSource.player(player), (entity as? LivingEntity)?.maxHealth?.times(0.8f)?.coerceIn(damageRange) ?: damageRange.start)
                        if (!player.world.isClient) {
                            entity.velocity = player.velocity.add(s * (actorSpeedH / f), 0.0, c * (actorSpeedH / f))
                            entity.replaceVelocity(y = targetSpeedV)
                            entity.velocityDirty = true
                        }
                        UPPERCUT.target(entity, player, tag)
                    }
                }
            }
        }

        override fun targetInitial(entity: Entity, player: PlayerEntity, tag: CompoundTag) {
            entity.replaceVelocity(y = targetSpeedV.pow(1.2))
            entity.velocityDirty = true
            entity.fallDistance = 0f
            if (entity is LivingEntity) markFallHandler(entity, true)
        }

        override fun targetTick(entity: Entity, tag: CompoundTag) {
            entity.replaceVelocity(y = targetSpeedV * (1 - targetPercentage(tag.duration)!!).pow(1.2))
            entity.velocityDirty = true
        }
    }/*,
    PUNCH(2), //jet stone?
    STOMP(2), //tremor stone?
    BEAM(2)*/; //beam stone

    abstract val actorCooldown: Int
    abstract val actorCast: Int?
    open val actorFallBreak: Double? = null
    abstract val targetTimer: Int?
    open val targetFallBreak: Double? = null

    override fun asString() = name.toLowerCase()

    fun isActing(player: PlayerEntity) = actorManager.fromTracker(player).duration > -1

    fun act(player: PlayerEntity, only: Boolean = !isActing(player)): Boolean {
        if (only) {
            val tag = CompoundTag().also { it.duration = 0 }
            this.castInitial(player, tag)
            actorManager.toTracker(player, tag)
            return true
        }
        return false
    }

    fun isTarget(entity: Entity) = targetManager?.fromTracker(entity)?.keys?.any() ?: false

    fun isTarget(entity: Entity, player: PlayerEntity) = targetManager?.fromTracker(entity)?.keys?.any { it == player.uuidAsString } ?: false

    fun canTarget(): Boolean = targetManager != null

    fun target(entity: Entity, player: PlayerEntity, tag: CompoundTag = CompoundTag()): Boolean {
        val new = tag.copy().also { it.duration = 0 }
        this.targetInitial(entity, player, new)
        targetManager?.modifyTracker(entity) { it.copy().also { it.put(player.uuidAsString, new) } }
        if (entity is ServerPlayerEntity && entity.deathTime <= 0) entity.networkHandler.sendPacket(EntityTrackerUpdateS2CPacket(entity.getEntityId(), entity.getDataTracker(), true))
        return true
    }

    abstract fun castInitial(player: PlayerEntity, tag: CompoundTag)

    abstract fun castTick(player: PlayerEntity, tag: CompoundTag)

    open fun targetInitial(entity: Entity, player: PlayerEntity, tag: CompoundTag) = Unit

    open fun targetTick(entity: Entity, tag: CompoundTag) = Unit

    fun cooldown(player: PlayerEntity): Int = if (!player.isCreative) 130 else (actorCast ?: -1)

    fun castPercentage(tick: Int): Double? {
        if (this.actorCast == null || this.actorCast!! <= 0) return null
        val percent = tick.toDouble() / this.actorCast!!
        if (percent !in 0.0..1.0) return null
        return percent
    }

    fun isCasting(tick: Int): Boolean = castPercentage(tick) != null

    fun cooldownPercentage(player: PlayerEntity, tick: Int): Double? {
        val effectiveCooldown = this.cooldown(player) - (this.actorCast ?: 0)
        if (effectiveCooldown <= 0) return null
        val percent = (tick.toDouble() - (this.actorCast ?: 0)) / effectiveCooldown
        if (percent !in 0.0..1.0) return null
        return percent
    }

    fun isCooldown(player: PlayerEntity, tick: Int): Boolean = cooldownPercentage(player, tick) != null

    fun targetPercentage(tick: Int): Double? {
        if (this.targetTimer == null || this.targetTimer!! <= 0) return null
        val percent = tick.toDouble() / this.targetTimer!!
        if (percent !in 0.0..1.0) return null
        return percent
    }

    fun baseActorTick(player: PlayerEntity, tag: CompoundTag): CompoundTag {
        if (tag.duration >= cooldown(player)) return CompoundTag()
        if (isCasting(tag.duration)) {
            this.castTick(player, tag)
        }
        return tag.also { it.duration++ }
    }

    fun baseTargetTick(entity: Entity, tag: CompoundTag): CompoundTag {
        val key = tag.keys.minByOrNull { tag.getCompound(it).duration } ?: return tag
        val last = tag.getCompound(key)
        this.targetTick(entity, last)
        tag.put(key, last)
        return tag.also { it.keys.forEach { if (tag.getCompound(it).duration++ >= targetTimer ?: -1) tag.remove(it) } }
    }

    fun handleFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, actor: Boolean): Int? = if (actor) handleActorFall(entity, fallDistance, damageMultiplier, actor) else handleTargetFall(entity, fallDistance, damageMultiplier, actor)

    open fun handleActorFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, actor: Boolean): Int? = if (actorFallBreak == null) null else ceil((fallDistance - 3 - actorFallBreak!! - (entity.getStatusEffect(StatusEffects.JUMP_BOOST)?.amplifier?.plus(1) ?: 0)) * damageMultiplier)

    open fun handleTargetFall(entity: LivingEntity, fallDistance: Float, damageMultiplier: Float, actor: Boolean): Int? = if (targetFallBreak == null) null else ceil((fallDistance - 3 - targetFallBreak!! - (entity.getStatusEffect(StatusEffects.JUMP_BOOST)?.amplifier?.plus(1) ?: 0)) * damageMultiplier)

    fun markFallHandler(entity: LivingEntity, actor: Boolean) = entity.dataTracker.set(LCCTrackers.gauntletFallHandler, (ordinal + 1 + actor.toInt()).toByte())

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
    }

    var CompoundTag.duration: Int get() = if (contains("duration", NBT_INT)) getInt("duration") else -1; set(v) = putInt("duration", v)

}