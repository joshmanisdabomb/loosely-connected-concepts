package com.joshmanisdabomb.lcc.energy.world

import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

interface WorldEnergyHandler : EnergyHandler<WorldEnergyContext> {

    fun insertEnergySided(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float? {
        val pos2 = (context.pos ?: return null).offset(context.side ?: return null)
        getHandlerAt(context.world ?: return null, pos2)?.also { return this.insertEnergy(it, amount, unit, context) }
        return null
    }

    fun extractEnergySided(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float? {
        val pos2 = (context.pos ?: return null).offset(context.side ?: return null)
        getHandlerAt(context.world ?: return null, pos2)?.also { return this.extractEnergy(it, amount, unit, context) }
        return null
    }

    fun sendEnergy(context: WorldEnergyContext, amount: Float, unit: EnergyUnit, vararg sides: Direction): Float {
        return EnergyTransaction().includeAll(sides.map { d -> { insertEnergySided(it, unit, context.copy(side = d, away = context.home?.offset(d))) } }).run(amount)
    }

    fun requestEnergy(context: WorldEnergyContext, amount: Float, unit: EnergyUnit, vararg sides: Direction): Float {
        return EnergyTransaction().includeAll(sides.map { d -> { extractEnergySided(it, unit, context.copy(side = d, away = context.home?.offset(d))) } }).run(amount)
    }

    companion object {
        fun getHandlerAt(world: BlockView, pos: BlockPos): WorldEnergyHandler? {
            val state = world.getBlockState(pos)
            val block = state.block
            if (block is WorldEnergyHandler) return block
            val be = world.getBlockEntity(pos)
            if (be is WorldEnergyHandler) return be
            return null
        }
    }

}