package com.joshmanisdabomb.lcc.energy.world

import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

interface AbstractWorldEnergyHandler {

    val handler: EnergyHandler<WorldEnergyContext>

    fun insertEnergySided(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float? {
        val pos2 = (context.pos ?: return null).offset(context.side ?: return null)
        getHandlerAt(context.world ?: return null, pos2)?.also { return handler.insertEnergy(it.handler, amount, unit, context) }
        return null
    }

    fun extractEnergySided(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float? {
        val pos2 = (context.pos ?: return null).offset(context.side ?: return null)
        getHandlerAt(context.world ?: return null, pos2)?.also { return handler.extractEnergy(it.handler, amount, unit, context) }
        return null
    }

    fun sendEnergy(context: WorldEnergyContext, amount: Float, unit: EnergyUnit, vararg sides: Direction): Float {
        var sent = 0f
        for (d in sides) {
            sent += insertEnergySided(amount - sent, unit, context.copy(side = d, away = context.home?.offset(d))) ?: 0f
            if (sent >= amount) break
        }
        return sent
    }

    fun requestEnergy(context: WorldEnergyContext, amount: Float, unit: EnergyUnit, vararg sides: Direction): Float {
        var received = 0f
        for (d in sides) {
            if (received >= amount) break
            received += extractEnergySided(amount - received, unit, context.copy(side = d, away = context.home?.offset(d))) ?: 0f
        }
        return received
    }

    companion object {
        fun getHandlerAt(world: BlockView, pos: BlockPos): AbstractWorldEnergyHandler? {
            val state = world.getBlockState(pos)
            val block = state.block
            if (block is AbstractWorldEnergyHandler) return block
            val be = world.getBlockEntity(pos)
            if (be is AbstractWorldEnergyHandler) return be
            return null
        }
    }

}