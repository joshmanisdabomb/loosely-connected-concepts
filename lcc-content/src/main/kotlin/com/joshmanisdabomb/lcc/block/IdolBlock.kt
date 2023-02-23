package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.insertStack
import com.joshmanisdabomb.lcc.extensions.rotationPlacement
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties.ROTATION
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class IdolBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(ROTATION, 0).with(pedestal, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(ROTATION, pedestal).let {}

    override fun getPlacementState(context: ItemPlacementContext): BlockState {
        val state = rotationPlacement(context)
        val replace = context.world.getBlockState(context.blockPos)
        if (replace.isOf(LCCBlocks.bifrost_pedestal)) {
            return state.with(pedestal, true)
        }
        return state
    }

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(ROTATION, rot.rotate(state.get(ROTATION), 16))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16))

    override fun canReplace(state: BlockState, context: ItemPlacementContext): Boolean {
        return super.canReplace(state, context)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val sworld = world as? ServerWorld ?: return ActionResult.SUCCESS
        val result = getDroppedStacks(state.with(pedestal, false), sworld, pos, null)
        result.forEach(player::insertStack)
        world.setBlockState(pos, if (state[pedestal]) LCCBlocks.bifrost_pedestal.defaultState else Blocks.AIR.defaultState)
        return ActionResult.SUCCESS
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        val rotated = state[ROTATION].mod(4) == 0
        val pedestal = state[pedestal]
        return when {
            rotated && pedestal -> shape_rot_pedestal
            pedestal -> shape_pedestal
            rotated -> shape_rot
            else -> shape
        }
    }

    companion object {
        val shape = createCuboidShape(4.0, 0.0, 4.0, 10.0, 9.0, 10.0)
        val shape_rot = createCuboidShape(5.0, 0.0, 5.0, 11.0, 9.0, 11.0)
        val shape_pedestal = VoxelShapes.union(shape.offset(0.0, 0.4375, 0.0), IdolPedestalBlock.shape)
        val shape_rot_pedestal = VoxelShapes.union(shape_rot.offset(0.0, 0.4375, 0.0), IdolPedestalBlock.shape)

        val pedestal = BooleanProperty.of("pedestal")
    }

}
