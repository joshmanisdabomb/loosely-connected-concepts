package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class SapBurstBlock(val liquid: AbstractTreetapBlock.TreetapLiquid, val transform: BlockState, settings: Settings, val rate: (random: Random) -> Int) : Block(settings) {

    val server_random by lazy { Random.create() }

    init {
        defaultState = stateManager.defaultState.with(sap, 7)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(sap).let {}

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (!world.isClient) world.createAndScheduleBlockTick(pos, this, rate(server_random))
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        horizontalDirections.forEach {
            val pos2 = pos.down().offset(it)
            val state2 = world.getBlockState(pos2)
            val block = state2.block
            if (block !is AbstractTreetapBlock) return@forEach
            block.incrementLiquidLevel(state2, world, pos2, liquid)?.also { world.setBlockState(pos2, it) }
        }
        when (state[sap]) {
            1 -> world.setBlockState(pos, transform)
            else -> {
                world.setBlockState(pos, state.with(sap, state[sap] - 1))
                world.createAndScheduleBlockTick(pos, this, rate(random))
            }
        }
    }

    companion object {
        val sap = IntProperty.of("sap", 1, 7)
    }

}