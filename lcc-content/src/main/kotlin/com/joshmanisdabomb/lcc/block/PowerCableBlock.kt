package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.world.AbstractWorldEnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyHandler
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.util.math.Direction

class PowerCableBlock(settings: Settings) : CableBlock(settings, { world, state, pos, side, state2, pos2 -> state2.block is PowerCableBlock || AbstractWorldEnergyHandler.getHandlerAt(world, pos2) != null }), WorldEnergyHandler {

    val network = FullBlockNetwork({ world, pos, state, side -> state.block is PowerCableBlock }, { world, pos, state, side -> if (AbstractWorldEnergyHandler.getHandlerAt(world, pos) != null && state.block !is PowerCableBlock) arrayOf("all", side.toString()) else emptyArray() }, 64)

    override fun addEnergyDirect(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        return energy(context, amount, unit, EnergyHandler<WorldEnergyContext>::addEnergy)
    }

    override fun removeEnergyDirect(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        return energy(context, amount, unit, EnergyHandler<WorldEnergyContext>::removeEnergy)
    }

    private fun energy(context: WorldEnergyContext, amount: Float, unit: EnergyUnit, action: EnergyHandler<WorldEnergyContext>.(target: EnergyHandler<WorldEnergyContext>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) -> Float): Float {
        val nodes = network.discover(context.world ?: return 0f, context.home ?: return 0f).nodes
        var change = 0f
        nodes.get("all")?.filter { it != context.away }?.forEach { p ->
            val handler = AbstractWorldEnergyHandler.getHandlerAt(context.world!!, p) ?: return@forEach
            val direction = Direction.values().filter { nodes.get(it.toString())?.contains(p) == true }.firstOrNull() ?: return@forEach
            val cable = p.offset(direction.opposite)
            change += handler.handler.action(this, amount - change, unit, context.copy(home = p, away = cable, side = direction.opposite))
            if (change >= amount) return change
        }
        return change
    }

}