package com.joshmanisdabomb.lcc.abstracts.nuclear

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity
import net.minecraft.util.math.MathHelper.ceil
import net.minecraft.world.World

object NuclearUtil {

    const val maxUranium = 20
    val minRadius = getExplosionRadiusFromUranium(1)
    val minLifetime = getExplosionLifetimeFromUranium(1)
    val maxRadius = getExplosionRadiusFromUranium(maxUranium)
    val maxLifetime = getExplosionLifetimeFromUranium(maxUranium)

    fun getExplosionRadiusFromUranium(amount: Int) = (amount*6)+20
    fun getUraniumFromExplosionRadius(radius: Int) = (radius-20)/6f

    fun getExplosionLifetimeFromUranium(amount: Int) = ceil(amount.div(3.5)) + 5

    fun getWinterIncreaseFromUranium(amount: Int) = amount.toFloat().div(maxUranium).let { it.times(it) }.times(2.9f).plus(0.1f)
    fun getWinterIncreaseFromRadius(radius: Int) = getWinterIncreaseFromUranium(getUraniumFromExplosionRadius(radius).toInt())

    fun getLightModifierFromWinter(winter: Float) = 1f.minus(winter.minus(4f).coerceAtLeast(0f).times(0.6f))

    fun strike(world: World, entity: NuclearExplosionEntity) {
        LCCComponents.nuclear.maybeGet(world).orElse(null)?.strike(entity)
    }

}