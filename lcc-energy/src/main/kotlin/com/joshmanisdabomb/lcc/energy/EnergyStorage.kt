package com.joshmanisdabomb.lcc.energy

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

interface EnergyStorage : EnergyHandler {

    val providing get() = rawEnergy == null

    var rawEnergy: Float?
    val rawEnergyMultiplier get() = 1f
    val rawEnergyBounds: ClosedRange<Float>?

    fun getEnergy(unit: EnergyUnit, side: Direction?) = (rawEnergy!! / rawEnergyMultiplier) * unit.multiplier

    fun setEnergy(amount: Float, unit: EnergyUnit, from: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        val before = rawEnergy!!
        rawEnergy = ((amount / unit.multiplier) * rawEnergyMultiplier)
        rawEnergyBounds?.apply { rawEnergy = rawEnergy!!.coerceIn(this) }
        return if (rawEnergy == before || onEnergyChanged(side, before)) (rawEnergy!! / rawEnergyMultiplier) * unit.multiplier else before
    }

    override fun addEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        val get = getEnergy(unit, side)
        val set = setEnergy(get + amount, unit, target, world, home, away, side)
        return set - get
    }

    override fun removeEnergy(amount: Float, unit: EnergyUnit, from: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        val get = getEnergy(unit, side)
        val set = setEnergy(get - amount, unit, from, world, home, away, side)
        return get - set
    }

    fun onEnergyChanged(side: Direction?, before: Float) = true

}