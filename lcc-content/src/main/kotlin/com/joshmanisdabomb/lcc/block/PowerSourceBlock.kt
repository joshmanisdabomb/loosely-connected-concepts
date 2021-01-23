package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyHandler
import net.minecraft.block.Block

class PowerSourceBlock(settings: Settings) : Block(settings), WorldEnergyHandler {

    override fun addEnergyDirect(amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    override fun removeEnergyDirect(amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = amount

}
