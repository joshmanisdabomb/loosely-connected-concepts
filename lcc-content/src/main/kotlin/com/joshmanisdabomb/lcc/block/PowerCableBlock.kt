package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class PowerCableBlock(settings: Settings) : CableBlock(settings, { world, state, pos, side, state2, pos2 -> state2.block is PowerCableBlock || EnergyHandler.getHandlerAt(world, pos2) != null }), EnergyHandler {

    val network = FullBlockNetwork({ world, pos, state, side -> state.block is PowerCableBlock }, { world, pos, state, side -> if (EnergyHandler.getHandlerAt(world, pos) != null && state.block !is PowerCableBlock) arrayOf("all", side.toString()) else emptyArray() }, 64)

    override fun addEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        return energy(world, home, away, amount, unit, EnergyHandler::addEnergy)
    }

    override fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        return energy(world, home, away, amount, unit, EnergyHandler::removeEnergy)
    }

    private fun energy(world: BlockView?, home: BlockPos?, away: BlockPos?, amount: Float, unit: EnergyUnit, action: EnergyHandler.(Float, EnergyUnit, EnergyHandler?, BlockView?, BlockPos?, BlockPos?, Direction?) -> Float): Float {
        val nodes = network.discover(world ?: return 0f, home ?: return 0f).nodes
        var change = 0f
        nodes.get("all")?.filter { it != away }?.forEach { p ->
            val handler = EnergyHandler.getHandlerAt(world, p) ?: return@forEach
            val direction = Direction.values().filter { nodes.get(it.toString())?.contains(p) == true }.firstOrNull() ?: return@forEach
            val cable = p.offset(direction.opposite)
            change += handler.action(amount - change, unit, this, world, p, cable, direction.opposite)
            if (change >= amount) return change
        }
        return change
    }

}