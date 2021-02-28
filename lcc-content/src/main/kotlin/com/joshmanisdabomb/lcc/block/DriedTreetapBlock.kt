package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

class DriedTreetapBlock(settings: Settings) : AbstractTreetapBlock(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(liquid, TreetapLiquid.LATEX).with(container, TreetapContainer.BOWL)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(liquid, container) }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = state[container].shape[state[HORIZONTAL_FACING]]

    override fun incrementLiquidLevel(state: BlockState, world: World, pos: BlockPos, liquid: TreetapLiquid) = null

    override fun decrementLiquidLevel(state: BlockState, world: World, pos: BlockPos) = null

    override fun hasRandomTicks(state: BlockState) = false

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) = Unit

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        return ActionResult.PASS
    }

    override fun getLiquidFromState(state: BlockState) = state.get(liquid)
    override fun getContainerFromState(state: BlockState) = state.get(container)

    companion object {
        val liquid = EnumProperty.of("liquid", TreetapLiquid::class.java) { it.dryAge != null }
        val container = EnumProperty.of("container", TreetapContainer::class.java)
    }

}
