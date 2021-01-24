package com.joshmanisdabomb.lcc.item.block

import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import net.minecraft.block.Block
import net.minecraft.item.BlockItem

class PowerSourceBlockItem(block: Block, settings: Settings) : BlockItem(block, settings), StackEnergyHandler {

    override fun addEnergyDirect(amount: Float, unit: EnergyUnit, context: StackEnergyContext) = 0f

    override fun removeEnergyDirect(amount: Float, unit: EnergyUnit, context: StackEnergyContext) = amount

}