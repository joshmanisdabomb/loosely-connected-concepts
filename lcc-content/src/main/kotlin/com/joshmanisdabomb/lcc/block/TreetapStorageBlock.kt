package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.TreetapBlock.Companion.tap
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

abstract class TreetapStorageBlock(settings: Settings) : AbstractTreetapBlock(settings) {

    abstract val container: TreetapContainer

    private lateinit var p: IntProperty
    val progress get() = p
    val max get() = container.amount.plus(1) + TreetapLiquid.values().maxOf { it.dryAge ?: 0 }

    init {
        defaultState = stateManager.defaultState.with(liquid, TreetapLiquid.LATEX).with(progress, 1)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        p = IntProperty.of("progress", 1, max)
        super.appendProperties(builder).also { builder.add(liquid, progress) }
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = container.shape[state[HORIZONTAL_FACING]]

    override fun incrementLiquidLevel(state: BlockState, world: World, pos: BlockPos, liquid: TreetapLiquid): BlockState? {
        if (state[progress] <= container.amount) return state.with(progress, state[progress] + 1)
        return null
    }

    override fun decrementLiquidLevel(state: BlockState, world: World, pos: BlockPos): BlockState? {
        if (state[progress] > 1) return state.with(progress, state[progress].minus(1).coerceAtMost(container.amount))
        else return LCCBlocks.treetap.defaultState.with(HORIZONTAL_FACING, state[HORIZONTAL_FACING]).with(tap, TreetapBlock.TreetapState.values().first { it.container == container })
    }

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val liquid = getLiquidFromState(state)
        if (state[progress] > container.amount) {
            if (!liquid.canDry) return
            world.setBlockState(pos, if (state[progress] >= container.amount.plus(1).plus(liquid.dryAge)) LCCBlocks.dried_treetap.defaultState.with(HORIZONTAL_FACING, state[HORIZONTAL_FACING]).with(DriedTreetapBlock.liquid, liquid) else state.cycle(progress))
        } else {
            super.randomTick(state, world, pos, random)
        }
    }

    override fun getLiquidFromState(state: BlockState) = state[liquid]
    override fun getContainerFromState(state: BlockState) = container

    companion object {
        val liquid = EnumProperty.of("liquid", TreetapLiquid::class.java)
    }

}
