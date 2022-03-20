package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.EnhancingChamberBlockEntity
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.extensions.directionalFacePlacement
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.*
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class EnhancingChamberBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(FACING, rot.rotate(state[FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[FACING]))

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = EnhancingChamberBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape[state[FACING]]

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = cull_shape[state[FACING]]

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        val be = world.getBlockEntity(pos) as? EnhancingChamberBlockEntity ?: return ActionResult.PASS
        if (stack.isIn(LCCItemTags.enhancing_pyre)) {
            be.enhance(stack)
            return ActionResult.SUCCESS
        } else if (stack.item !is BlockItem) {
            val contents = be.inventory.getStack(0)
            if (ItemStack.canCombine(stack, contents)) return ActionResult.SUCCESS
            be.inventory.setStack(0, stack.split(1))
            if (!contents.isEmpty) player.giveItemStack(contents)
            be.markDirty()
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? EnhancingChamberBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    companion object {
        val shape = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), createCuboidShape(2.0, 1.0, 2.0, 14.0, 16.0, 14.0), BooleanBiFunction.ONLY_FIRST).rotatable(Direction.UP)
        val cull_shape = VoxelShapes.combineAndSimplify(createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), createCuboidShape(2.0, 1.0, 2.0, 14.0, 16.0, 14.0), BooleanBiFunction.ONLY_FIRST).rotatable(Direction.UP)
    }

}
