package com.joshmanisdabomb.lcc.energy.world

import com.joshmanisdabomb.lcc.energy.base.EnergyHandler

interface WorldEnergyHandler : AbstractWorldEnergyHandler, EnergyHandler<WorldEnergyContext> {

    override val handler: EnergyHandler<WorldEnergyContext> get() = this

}