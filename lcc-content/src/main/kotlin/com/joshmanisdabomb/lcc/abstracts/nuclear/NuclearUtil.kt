package com.joshmanisdabomb.lcc.abstracts.nuclear

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.MathHelper.ceil
import net.minecraft.world.World
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

    fun strike(world: World, entity: NuclearExplosionEntity) {
        LCCComponents.nuclear.maybeGet(world).orElse(null)?.strike(entity)
    }

    fun addRadiation(entity: LivingEntity, duration: Int, amplifier: Int) {
        if ((entity as? PlayerEntity)?.isCreative == true) return
        val previous = entity.getStatusEffect(LCCEffects.radiation)
        entity.applyStatusEffect(StatusEffectInstance(LCCEffects.radiation, duration.plus(previous?.duration ?: 0), max(amplifier, previous?.amplifier ?: -1)))
    }

}