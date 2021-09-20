package com.joshmanisdabomb.lcc.energy.stack

import com.joshmanisdabomb.lcc.energy.base.EnergyStorage

interface StackEnergyStorage : StackEnergyHandler, EnergyStorage<StackEnergyContext> {

    override fun getRawEnergy(context: StackEnergyContext) = context.stack.getSubNbt("lcc-energy")?.getFloat("amount") ?: 0f

    override fun setRawEnergy(context: StackEnergyContext, amount: Float) { context.stack.getSubNbt("lcc-energy")?.putFloat("amount", amount) }

}