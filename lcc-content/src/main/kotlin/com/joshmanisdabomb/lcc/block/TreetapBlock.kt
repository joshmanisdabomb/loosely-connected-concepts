package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

class TreetapBlock(settings: Settings) : AbstractTreetapBlock(settings) {

    init {
        defaultState = stateManager.defaultState.with(tap, TreetapState.NONE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(tap) }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = (getContainerFromState(state)?.shape ?: shape)[state[HORIZONTAL_FACING]]

    override fun incrementLiquidLevel(state: BlockState, world: World, pos: BlockPos, liquid: TreetapLiquid): BlockState? {
        val type = state[tap]
        if (type.liquid != null) return null
        val currentContainer = type.container
        if (currentContainer != null) {
            val storage = currentContainer.storage ?: return null
            return storage.defaultState.with(HORIZONTAL_FACING, state[HORIZONTAL_FACING]).with(TreetapStorageBlock.liquid, liquid).with(storage.progress, 1)
        } else {
            return state.with(tap, TreetapState.OVERFLOW_LATEX)
        }
    }

    override fun decrementLiquidLevel(state: BlockState, world: World, pos: BlockPos): BlockState? {
        val type = state[tap]
        val liquid = type.liquid ?: return null
        return state.with(tap, TreetapState.NONE)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        val type = state[tap]
        val currentLiquid = type.liquid
        val currentContainer = type.container
        val requestedState = TreetapState.values().firstOrNull { stack.item == it.container?.item ?: return@firstOrNull false }
        if (currentContainer == null && currentLiquid == null) {
            //Try add container.
            if (requestedState != null) {
                world.setBlockState(pos, state.with(tap, requestedState))
                if (player.isSurvival) stack.decrement(1)
                //sound effect
                return ActionResult.SUCCESS
            }
            return ActionResult.PASS
        } else if (currentContainer != null && currentLiquid == null && hand == Hand.MAIN_HAND) {
            //Try remove container.
            if (stack.isEmpty) {
                player.setStackInHand(hand, currentContainer.item.defaultStack)
                //maybe sound effect
                world.setBlockState(pos, state.with(tap, TreetapState.NONE))
                return ActionResult.SUCCESS
            } else if (stack.item == currentContainer.item) {
                if (stack.count < stack.maxCount) stack.increment(1)
                else if (!player.inventory.insertStack(currentContainer.item.defaultStack)) player.dropItem(currentContainer.item.defaultStack, false)
                //maybe sound effect
                world.setBlockState(pos, state.with(tap, TreetapState.NONE))
                return ActionResult.SUCCESS
            }
            return ActionResult.PASS
        } else {
            //Try add container with liquid overflow.
            if (requestedState != null && currentLiquid != null) {
                val block = requestedState.container?.storage ?: return ActionResult.PASS
                world.setBlockState(pos, block.defaultState.with(HORIZONTAL_FACING, state[HORIZONTAL_FACING]).with(TreetapStorageBlock.liquid, currentLiquid).with(block.progress, 1))
                if (player.isSurvival) stack.decrement(1)
                //sound effect
                return ActionResult.SUCCESS
            } else {
                return super.onUse(state, world, pos, player, hand, hit)
            }
        }
    }

    override fun getLiquidFromState(state: BlockState) = state[tap].liquid
    override fun getContainerFromState(state: BlockState) = state[tap].container

    companion object {
        val tap = EnumProperty.of("type", TreetapState::class.java)

        val shape = createCuboidShape(6.0, 14.0, 10.0, 10.0, 16.0, 16.0).rotatable(Direction.NORTH)
    }

    enum class TreetapState() : StringIdentifiable {
        NONE,
        BOWL(TreetapContainer.BOWL),
        OVERFLOW_LATEX(TreetapLiquid.LATEX);

        constructor(container: TreetapContainer) : this() { _container = container }
        constructor(liquid: TreetapLiquid) : this() { _liquid = liquid }

        private var _container: TreetapContainer? = null
        private var _liquid: TreetapLiquid? = null
        val container get() = _container
        val liquid get() = _liquid

        val isContainer by lazy { container != null }
        val isLiquid by lazy { liquid != null }

        override fun asString() = name.toLowerCase()
    }

}
