package com.joshmanisdabomb.lcc.energy.stack

import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.base.EnergyStorage

interface StackEnergyHandler : EnergyHandler<StackEnergyContext>

interface StackEnergyStorage : StackEnergyHandler, EnergyStorage<StackEnergyContext> {

    override fun getRawEnergy(context: StackEnergyContext) = context.stack.getSubTag("lcc-energy")?.getFloat("amount")

    override fun setRawEnergy(context: StackEnergyContext, amount: Float) { context.stack.getSubTag("lcc-energy")?.putFloat("amount", amount) }

}