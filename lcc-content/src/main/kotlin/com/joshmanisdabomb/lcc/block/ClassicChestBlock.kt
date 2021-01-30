package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.ClassicChestBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.enums.ChestType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.PiglinBrain
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.CHEST_TYPE
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class ClassicChestBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(CHEST_TYPE, ChestType.SINGLE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, CHEST_TYPE).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = ClassicChestBlockEntity(pos, state)

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        return if (world.isClient) {
            ActionResult.SUCCESS
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
            PiglinBrain.onGuardedBlockInteracted(player, true)
            ActionResult.CONSUME
        }
    }

    fun getDirectionToAttached(state: BlockState): Direction {
        val direction = state[HORIZONTAL_FACING]
        return if (state[CHEST_TYPE] === ChestType.LEFT) direction.rotateYClockwise() else direction.rotateYCounterclockwise()
    }

    fun getDirectionToAttach(context: ItemPlacementContext, side: Direction): Direction? {
        val blockstate = context.world.getBlockState(context.blockPos.offset(side))
        return if (blockstate.block === this && blockstate[CHEST_TYPE] === ChestType.SINGLE) blockstate[HORIZONTAL_FACING] else null
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, stateFrom: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        if (stateFrom.block === this && direction.axis.isHorizontal) {
            val chesttype = stateFrom[CHEST_TYPE]
            if (state[CHEST_TYPE] === ChestType.SINGLE && chesttype !== ChestType.SINGLE && state[HORIZONTAL_FACING] === stateFrom[HORIZONTAL_FACING] && this.getDirectionToAttached(stateFrom) === direction.opposite) {
                return state.with(CHEST_TYPE, chesttype.opposite)
            }
        } else if (this.getDirectionToAttached(state) === direction) {
            return state.with(CHEST_TYPE, ChestType.SINGLE)
        }

        return super.getStateForNeighborUpdate(state, direction, stateFrom, world, pos, posFrom)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        var chesttype = ChestType.SINGLE
        var direction = ctx.playerFacing.opposite
        val flag = ctx.shouldCancelInteraction()
        val direction1 = ctx.side
        if (direction1.axis.isHorizontal && flag) {
            val direction2 = getDirectionToAttach(ctx, direction1.opposite)
            if (direction2 != null && direction2.axis !== direction1.axis) {
                direction = direction2
                chesttype = if (direction2.rotateYCounterclockwise() === direction1.opposite) ChestType.RIGHT else ChestType.LEFT
            }
        }

        if (chesttype === ChestType.SINGLE && !flag) {
            if (direction === getDirectionToAttach(ctx, direction.rotateYClockwise())) {
                chesttype = ChestType.LEFT
            } else if (direction === getDirectionToAttach(ctx, direction.rotateYCounterclockwise())) {
                chesttype = ChestType.RIGHT
            }
        }

        return defaultState.with(HORIZONTAL_FACING, direction).with(CHEST_TYPE, chesttype)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (itemStack.hasCustomName()) (world.getBlockEntity(pos) as? ClassicChestBlockEntity)?.customName = itemStack.name
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? ClassicChestBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this)
            if (state[CHEST_TYPE] != ChestType.SINGLE) {
                val other = getDirectionToAttached(state)
                if (world.getBlockState(pos.offset(other)).isOf(this)) world.updateComparators(pos.offset(other), this)
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

}