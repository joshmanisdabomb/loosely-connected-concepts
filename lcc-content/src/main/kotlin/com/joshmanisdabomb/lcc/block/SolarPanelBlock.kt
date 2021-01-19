package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.extensions.isHorizontal
import com.joshmanisdabomb.lcc.network.FullBlockNetwork
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.LightType
import java.util.*

class SolarPanelBlock(settings: Settings, val energy: Float = 3f) : SimpleEnergyBlock(settings) {

    override val network = FullBlockNetwork({ _, _, state, side -> if (side.isHorizontal) state.isOf(this) else false }, { _, _, state, side -> if (side?.isHorizontal != false && state.isOf(this) && state[Properties.POWERED]) arrayOf("powered") else emptyArray() }, 64)

    init {
        defaultState = stateManager.defaultState.with(Properties.POWERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(Properties.POWERED).let {}

    override fun getEnergy(world: BlockView, pos: BlockPos) = energy

    override fun removeEnergy(amount: Float, unit: EnergyUnit, target: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?): Float {
        if (side != null && side != Direction.DOWN) return 0f
        return super.removeEnergy(amount, unit, target, world, home, away, side)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val current = state[Properties.POWERED]
        val to = world.getLightLevel(LightType.SKY, pos) - world.ambientDarkness == 15
        if (current != to) world.setBlockState(pos, state.with(Properties.POWERED, to), 7)
        super.scheduledTick(state, world, pos, random)
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    companion object {
        val shape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0)
    }

}