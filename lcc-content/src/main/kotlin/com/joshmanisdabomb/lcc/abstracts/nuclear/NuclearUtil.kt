package com.joshmanisdabomb.lcc.abstracts.nuclear

import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.entity.EntityData
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.MathHelper.ceil
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import net.minecraft.world.level.ServerWorldProperties
import java.util.*
import kotlin.math.max
import kotlin.math.pow

object NuclearUtil {

    const val maxUranium = 20
    val minRadius = getExplosionRadiusFromUranium(1)
    val minLifetime = getExplosionLifetimeFromUranium(1)
    val maxRadius = getExplosionRadiusFromUranium(maxUranium)
    val maxLifetime = getExplosionLifetimeFromUranium(maxUranium)

    const val maxWinter = 5

    fun getExplosionRadiusFromUranium(amount: Int) = (amount*6)+20
    fun getUraniumFromExplosionRadius(radius: Int) = (radius-20)/6f

    fun getExplosionLifetimeFromUranium(amount: Int) = ceil(amount.div(2.5)) + 5

    fun getWinterIncreaseFromUranium(amount: Int) = amount.toFloat().div(maxUranium).pow(1.5f).times(1.4f).plus(0.5f)
    fun getWinterIncreaseFromRadius(radius: Int) = getWinterIncreaseFromUranium(getUraniumFromExplosionRadius(radius).toInt())

    fun getWinterLevel(winter: Float) = winter.toInt().coerceIn(0, maxWinter)

    fun getLightModifierFromWinter(winterLevel: Int) = when (winterLevel) {
        5 -> 0.4
        4 -> 0.6
        3 -> 0.85
        else -> 1.0
    }

    fun getWeatherSpeedChangeFromWinter(winterLevel: Int, random: Random): Int {
        return winterLevel + random.nextFloat().times(4f).times(winterLevel.minus(1)).toInt()
    }

    fun getMobCapIncrease(winterLevel: Int, group: SpawnGroup) = when (group) {
        SpawnGroup.MONSTER -> winterLevel.minus(2).times(1.5f).coerceAtLeast(1f)
        else -> 1f.minus(winterLevel.toFloat().div(maxWinter))
    }

    fun strike(world: World, entity: NuclearExplosionEntity) {
        LCCComponents.nuclear.maybeGet(world).orElse(null)?.strike(entity)
    }

    fun addRadiation(entity: LivingEntity, duration: Int, amplifier: Int) {
        if ((entity as? PlayerEntity)?.isSurvival == false) return
        if (entity.world.isClient) return
        val previous = entity.getStatusEffect(LCCEffects.radiation)
        val effect = StatusEffectInstance(LCCEffects.radiation, duration.plus(previous?.duration ?: 0), max(amplifier, previous?.amplifier ?: -1))
        if (entity.canHaveStatusEffect(effect)) {
            entity.removeStatusEffectInternal(LCCEffects.radiation)
            entity.addStatusEffect(effect)
        }
    }

    fun tick(world: ServerWorld, winterLevel: Int) {
        val properties = world.levelProperties as? ServerWorldProperties ?: return
        if (properties.clearWeatherTime > 0) {
            properties.clearWeatherTime = properties.clearWeatherTime.minus(NuclearUtil.getWeatherSpeedChangeFromWinter(winterLevel, world.random)).coerceAtLeast(0)
            if (properties.clearWeatherTime <= 0) {
                properties.rainTime = world.random.nextInt(168000) + 12000
                properties.thunderTime = world.random.nextInt(168000) + 24000
                properties.isRaining = true
                properties.isThundering = true
                return
            }
        }
        if (properties.thunderTime > 0) {
            properties.thunderTime = properties.thunderTime.minus(NuclearUtil.getWeatherSpeedChangeFromWinter(winterLevel, world.random)).coerceAtLeast(0)
            if (properties.thunderTime <= 0) {
                properties.isThundering = false
                return
            }
        }
        if (properties.rainTime > 0) {
            properties.rainTime = properties.rainTime.minus(NuclearUtil.getWeatherSpeedChangeFromWinter(winterLevel, world.random)).coerceAtLeast(0)
            if (properties.rainTime <= 0) {
                properties.isRaining = false
                properties.clearWeatherTime = world.random.nextInt(168000.div(winterLevel.coerceAtLeast(1))) + 12000
                return
            }
        }
    }

    fun mobSpawned(entity: MobEntity, world: ServerWorldAccess, winterLevel: Int, difficulty: LocalDifficulty, spawnReason: SpawnReason, entityData: EntityData?, entityNbt: NbtCompound?) {
        if (winterLevel <= 2) return
        if (entity !is HostileEntity || entity is CreeperEntity) return
        if (world.random.nextInt(22.minus(winterLevel.times(3))) == 0) entity.addStatusEffect(StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, world.random.nextInt(winterLevel.minus(2))))
        if (world.random.nextInt(22.minus(winterLevel.times(3))) == 0) entity.addStatusEffect(StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, world.random.nextInt(winterLevel.minus(2))))
        if (world.random.nextInt(22.minus(winterLevel.times(3))) == 0) entity.addStatusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, world.random.nextInt(winterLevel.minus(2))))
        if (world.random.nextInt(22.minus(winterLevel.times(3))) == 0) entity.addStatusEffect(StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, world.random.nextInt(winterLevel.minus(2).times(3))))
        if (world.random.nextInt(22.minus(winterLevel.times(3))) == 0) entity.addStatusEffect(StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0))
        if (world.random.nextInt(22.minus(winterLevel.times(3))) == 0) entity.addStatusEffect(StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, world.random.nextInt(winterLevel.minus(2))))
    }

}