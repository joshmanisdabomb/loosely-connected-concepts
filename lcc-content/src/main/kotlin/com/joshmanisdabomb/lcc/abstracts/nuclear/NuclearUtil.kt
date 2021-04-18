package com.joshmanisdabomb.lcc.abstracts.nuclear

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.MathHelper.ceil
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

    fun getExplosionRadiusFromUranium(amount: Int) = (amount*6)+20
    fun getUraniumFromExplosionRadius(radius: Int) = (radius-20)/6f

    fun getExplosionLifetimeFromUranium(amount: Int) = ceil(amount.div(2.5)) + 5

    fun getWinterIncreaseFromUranium(amount: Int) = 0.5f + amount.toFloat().div(maxUranium).pow(1.5f).times(2.9f).plus(0.1f)
    fun getWinterIncreaseFromRadius(radius: Int) = getWinterIncreaseFromUranium(getUraniumFromExplosionRadius(radius).toInt())

    fun getWinterLevel(winter: Float) = winter.toInt().coerceIn(0, 5)

    fun getLightModifierFromWinter(winterLevel: Int) = when (winterLevel) {
        5 -> 0.4
        4 -> 0.6
        3 -> 0.85
        else -> 1.0
    }

    fun getWeatherSpeedChangeFromWinter(winterLevel: Int, random: Random): Int {
        return winterLevel + random.nextFloat().times(4f).times(winterLevel.minus(1)).toInt()
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

}