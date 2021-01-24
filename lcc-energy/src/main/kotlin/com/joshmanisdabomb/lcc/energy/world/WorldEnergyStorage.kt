package com.joshmanisdabomb.lcc.energy.world

import com.joshmanisdabomb.lcc.energy.base.EnergyStorage

interface WorldEnergyStorage : WorldEnergyHandler, EnergyStorage<WorldEnergyContext> {

    var rawEnergy: Float?
    val rawEnergyMinimum: Float get() = 0f
    val rawEnergyMaximum: Float?
    val rawEnergyMultiplier: Float get() = 1f

    override fun getRawEnergy(context: WorldEnergyContext) = rawEnergy

    override fun setRawEnergy(context: WorldEnergyContext, amount: Float) { rawEnergy = amount }

    override fun getRawEnergyMinimum(context: WorldEnergyContext) = rawEnergyMinimum
    override fun getRawEnergyMaximum(context: WorldEnergyContext) = rawEnergyMaximum
    override fun getRawEnergyMultiplier(context: WorldEnergyContext) = rawEnergyMultiplier

}