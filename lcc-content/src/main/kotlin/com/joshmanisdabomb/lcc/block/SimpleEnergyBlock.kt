package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyHandler
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.ModifiableWorld
import net.minecraft.world.World
import java.util.*

abstract class SimpleEnergyBlock(settings: Settings) : Block(settings), WorldEnergyHandler {

    abstract val network: FullBlockNetwork

    override fun addEnergyDirect(amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    override fun removeEnergyDirect(amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        val world = context.world ?: return 0f
        val home = context.home ?: return 0f
        val state = context.state ?: return 0f
        extractEnergy(world as? ModifiableWorld ?: return 0f, home, state)
        return getEnergy(world, home)
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        val world = context.world ?: return 0f
        if (world !is ModifiableWorld) return 0f
        val home = context.home ?: return 0f
        if (target == this && amount >= 0f) {
            return removeEnergyDirect(amount, unit, context)
        }

        network.discover(world, home).nodes.get("powered")?.also {
            return EnergyTransaction().includeAll(it.map { p -> { this.removeEnergy(this, getEnergy(world, p), unit, context.copy(home = p, away = home, side = null)) } }).run(amount)
        }
        return 0f
    }

    abstract fun getEnergy(world: BlockView, pos: BlockPos): Float

    open fun extractEnergy(world: ModifiableWorld, pos: BlockPos, state: BlockState) {
        world.setBlockState(pos, state.with(Properties.POWERED, false), 7)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super.onBlockAdded(state, world, pos, oldState, notify)
        world.createAndScheduleBlockTick(pos, this, 1)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
        world.createAndScheduleBlockTick(pos, this, 1)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        world.createAndScheduleBlockTick(pos, this, 1)
    }

}
