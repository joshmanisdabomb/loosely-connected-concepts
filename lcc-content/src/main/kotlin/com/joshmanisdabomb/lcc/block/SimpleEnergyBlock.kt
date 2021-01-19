package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.ModifiableWorld
import net.minecraft.world.World
import java.util.*

abstract class SimpleEnergyBlock(settings: Settings) : Block(settings), EnergyHandler {

    abstract val network: FullBlockNetwork

    override fun addEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?) = 0f

    override fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        if (world !is ModifiableWorld || home == null) return 0f
        val state = world.getBlockState(home)
        if (target == this && amount >= 0f) {
            extractEnergy(world, home, state)
            return getEnergy(world, home)
        }

        var total = 0f
        network.discover(world, home).nodes.get("powered")?.forEach {
            if (total >= amount) return total
            total += this.removeEnergy(getEnergy(world, it), unit, this, world, it, home, null)
        }
        return total
    }

    abstract fun getEnergy(world: BlockView, pos: BlockPos): Float

    open fun extractEnergy(world: ModifiableWorld, pos: BlockPos, state: BlockState) {
        world.setBlockState(pos, state.with(Properties.POWERED, false), 7)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super.onBlockAdded(state, world, pos, oldState, notify)
        world.blockTickScheduler.schedule(pos, this, 1)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
        world.blockTickScheduler.schedule(pos, this, 1)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        world.blockTickScheduler.schedule(pos, this, 1)
    }

}
