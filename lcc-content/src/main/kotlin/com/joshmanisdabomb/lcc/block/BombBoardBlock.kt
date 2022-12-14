package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.perpendiculars
import com.joshmanisdabomb.lcc.extensions.pillarPlacement
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties.AXIS
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.explosion.Explosion

class BombBoardBlock(settings: Settings) : Block(settings), LCCContentBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(AXIS, Direction.Axis.Y).with(mine_state, 9)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(mine_state, AXIS).let {}

    override fun getPlacementState(ctx: ItemPlacementContext) = pillarPlacement(ctx)

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        val mstate = if (neighborState.isOf(this)) neighborState[mine_state] else mystery
        if (state[mine_state] == mine) return state
        val mines = getAdjacentMines(world, state, pos)
        if (mstate == 0) return state.with(mine_state, mines)

        if (mstate != mystery) {
            val perpendiculars = Direction.from(state[AXIS], Direction.AxisDirection.POSITIVE).perpendiculars
            val i = perpendiculars.indexOf(direction)
            if (i != -1) {
                val state2 = world.getBlockState(neighborPos.offset(perpendiculars[MathHelper.floorMod(i - 1, 4)]))
                if (state2.isOf(this) && state2[mine_state] == 0) return state.with(mine_state, mines)
                val state3 = world.getBlockState(neighborPos.offset(perpendiculars[MathHelper.floorMod(i + 1, 4)]))
                if (state3.isOf(this) && state3[mine_state] == 0) return state.with(mine_state, mines)
            }
        }

        if (state[mine_state] > 8) return state
        return state.with(mine_state, mines)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        if (player.isSneaking) {
            if (state[mine_state] <= 8 && stack.isEmpty) {
                world.setBlockState(pos, state.with(mine_state, mystery))
                return ActionResult.SUCCESS
            }
        } else if (stack.isOf(LCCBlocks.explosive_paste.asItem())) {
            if (state[mine_state] != mine) {
                world.setBlockState(pos, state.with(mine_state, mine))
                if (player.isSurvival) stack.decrement(1)
            }
            return ActionResult.SUCCESS
        } else {
            if (state[mine_state] == mystery) {
                if (stack.isEmpty) {
                    world.setBlockState(pos, state.with(mine_state, getAdjacentMines(world, state, pos)))
                    return ActionResult.SUCCESS
                }
            } else if (state[mine_state] == mine) {
                if (stack.isEmpty) {
                    if (!world.isClient) world.createExplosion(null, hit.pos.x, hit.pos.y, hit.pos.z, 5.0f, Explosion.DestructionType.NONE)
                    world.setBlockState(pos, state.with(mine_state, getAdjacentMines(world, state, pos)))
                    updateAdjacentBoard(world, state, pos)
                    return ActionResult.SUCCESS
                }
            }
        }
        return ActionResult.PASS
    }

    fun getAdjacentMines(world: WorldAccess, state: BlockState, pos: BlockPos): Int {
        val bp = BlockPos.Mutable()
        val others = Direction.Axis.VALUES.filter { it != state[AXIS] }.map { Direction.from(it, Direction.AxisDirection.POSITIVE) }
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val state2 = world.getBlockState(bp.set(pos).move(others[0], i).move(others[1], j))
                if (state2.isOf(this) && state2[mine_state] == mine) count++
            }
        }
        return count
    }

    fun updateAdjacentBoard(world: WorldAccess, state: BlockState, pos: BlockPos) {
        val bp = BlockPos.Mutable()
        val others = Direction.Axis.VALUES.filter { it != state[AXIS] }.map { Direction.from(it, Direction.AxisDirection.POSITIVE) }
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val state2 = world.getBlockState(bp.set(pos).move(others[0], i).move(others[1], j))
                if (state2.isOf(this) && state2[mine_state] <= 8) {
                    world.setBlockState(bp, state2.with(mine_state, getAdjacentMines(world, state2, bp)), 3)
                }
            }
        }
    }

    override fun lcc_content_hideStateFromDebug(state: BlockState, player: PlayerEntity, hit: BlockHitResult) = if (player.isSurvival && state[mine_state] > 8) "No cheating!" else null

    companion object {
        val mystery = 9
        val mine = 10

        val mine_state = IntProperty.of("state", 0, 10)
    }

}