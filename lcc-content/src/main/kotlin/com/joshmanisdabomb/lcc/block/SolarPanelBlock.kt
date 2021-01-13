package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.POWERED
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.LightType
import net.minecraft.world.ModifiableWorld
import net.minecraft.world.World
import java.util.*

class SolarPanelBlock(settings: Settings, val energy: Float = 3f) : Block(settings), EnergyHandler {

    val network = FullBlockNetwork({ _, _, state, _ -> state.isOf(this) }, { _, _, state, _ -> if (state.isOf(this) && state[POWERED]) arrayOf("powered") else emptyArray() }, 64)

    override fun addEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?) = 0f

    override fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        if (world !is ModifiableWorld || home == null) return 0f
        val state = world.getBlockState(home)
        if (target == this && amount >= 0f) {
            world.setBlockState(home, state.with(POWERED, false), 7)
            return this.energy
        }
        if (side != null && side != Direction.DOWN) return 0f

        var total = 0f
        network.discover(world, home).nodes.get("powered")?.forEach {
            if (total >= amount) return total
            total += this.removeEnergy(this.energy, unit, this, world, it, home, null)
        }
        return total
    }

    init {
        defaultState = stateManager.defaultState.with(POWERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(POWERED).let {}

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super.onBlockAdded(state, world, pos, oldState, notify)
        world.blockTickScheduler.schedule(pos, this, 1)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
        world.blockTickScheduler.schedule(pos, this, 1)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val current = state[POWERED]
        val to = world.getLightLevel(LightType.SKY, pos) - world.ambientDarkness == 15
        if (current != to) world.setBlockState(pos, state.with(POWERED, to), 7)
        world.blockTickScheduler.schedule(pos, this, 1)
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    companion object {
        val shape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0)
    }

}