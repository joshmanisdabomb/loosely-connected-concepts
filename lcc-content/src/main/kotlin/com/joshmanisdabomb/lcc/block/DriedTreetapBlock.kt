package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

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
        world.playSound(player, player.x, player.y, player.z, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        state[liquid].dryProduct?.also { dropStack(world, pos, it.copy()) }
        world.setBlockState(pos, LCCBlocks.treetap.defaultState.with(HORIZONTAL_FACING, state[HORIZONTAL_FACING]).with(TreetapBlock.tap, TreetapBlock.TreetapState.values().first { it.container == state[container] }))
        world.emitGameEvent(player, GameEvent.SHEAR, pos);
        return ActionResult.SUCCESS
    }

    override fun getLiquidFromState(state: BlockState) = state.get(liquid)
    override fun getContainerFromState(state: BlockState) = state.get(container)

    companion object {
        val liquid = EnumProperty.of("liquid", TreetapLiquid::class.java) { it.canDry }
        val container = EnumProperty.of("container", TreetapContainer::class.java)
    }

}
