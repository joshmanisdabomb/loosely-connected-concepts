package com.joshmanisdabomb.lcc.energy

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

interface EnergyStorage : EnergyHandler {

    val providing get() = rawEnergy == null

    var rawEnergy: Float?
    val rawEnergyMultiplier get() = 1f
    val rawEnergyMinimum get() = 0f
    val rawEnergyMaximum: Float?
    val rawEnergyBounds: ClosedRange<Float>? get() {
        return rawEnergyMinimum.rangeTo(rawEnergyMaximum ?: return null)
    }

    fun getEnergy(unit: EnergyUnit, side: Direction?): Float? {
        rawEnergy?.also {
            return (it / rawEnergyMultiplier) * unit.multiplier
        }
        return null
    }

    override fun getMinimumEnergy(unit: EnergyUnit) = rawEnergyMinimum.div(rawEnergyMultiplier).times(unit.multiplier)

    override fun getMaximumEnergy(unit: EnergyUnit) = rawEnergyMaximum?.div(rawEnergyMultiplier)?.times(unit.multiplier)

    override fun getEnergyBounds(unit: EnergyUnit): ClosedRange<Float>? {
        return getMinimumEnergy(unit)..(getMaximumEnergy(unit) ?: return null)
    }

    override fun getEnergySpace(unit: EnergyUnit, side: Direction?): Float? {
        return getMaximumEnergy(unit)?.minus(getEnergy(unit, side) ?: return null)
    }

    fun setEnergy(amount: Float, unit: EnergyUnit, from: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float? {
        rawEnergy?.also {
            val before = it
            var after = ((amount / unit.multiplier) * rawEnergyMultiplier)
            rawEnergyBounds?.apply { after = after.coerceIn(this) }
            if (after == before || onEnergyChanged(side, before)) {
                rawEnergy = after
                return (after / rawEnergyMultiplier) * unit.multiplier
            }
            return before
        }
        return null
    }

    override fun addEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        val space = getEnergySpace(unit, side)
        space?.also { if (it <= 0f) return 0f }
        val get = getEnergy(unit, side) ?: return 0f
        val set = setEnergy(get.plus(amount.run { coerceAtMost(space ?: return@run this) }), unit, target, world, home, away, side) ?: return 0f
        return set - get
    }

    override fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        val available = amount - getMinimumEnergy(unit)
        if (available <= 0f) return 0f
        val get = getEnergy(unit, side) ?: return 0f
        val set = setEnergy(get.minus(amount.run { coerceAtLeast(available) }), unit, target, world, home, away, side) ?: return 0f
        return get - set
    }

    fun onEnergyChanged(side: Direction?, before: Float) = true

}