package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.state.property.Properties.LIT
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class NuclearFiredGeneratorBlock(settings: Settings) : AbstractFiredGeneratorBlock(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(LIT, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(HORIZONTAL_FACING) }

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun getSteam(world: BlockView, pos: BlockPos, state: BlockState) = (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.steam ?: 0f

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NuclearFiredGeneratorBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomName()) (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.customName = stack.name
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (state[LIT]) return
        if (Direction.values().filter { it != state[HORIZONTAL_FACING] }.maxOf { world.getStrongRedstonePower(pos.offset(it), it.opposite) } > 0) {
            (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.activate(null)
        }
    }

    override fun emitsRedstonePower(state: BlockState) = state[LIT]

    override fun getWeakRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction): Int {
        if (!state[LIT]) return 0
        return (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.level ?: 0
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (state.isOf(newState.block) || newState.isOf(LCCBlocks.failing_nuclear_generator)) return
        ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
        world.updateComparators(pos, this)
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.nuclear_generator, NuclearFiredGeneratorBlockEntity::serverTick) else null

    companion object {
        val shape_top = createCuboidShape(0.0, 13.0, 0.0, 16.0, 16.0, 16.0)
        val shape_bottom = createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0)
        val shape_shielding_1 = createCuboidShape(1.0, 3.0, 1.0, 4.0, 13.0, 4.0)
        val shape_shielding_2 = createCuboidShape(12.0, 3.0, 1.0, 15.0, 13.0, 4.0)
        val shape_shielding_3 = createCuboidShape(1.0, 3.0, 12.0, 4.0, 13.0, 15.0)
        val shape_shielding_4 = createCuboidShape(12.0, 3.0, 12.0, 15.0, 13.0, 15.0)
        val shape_core = createCuboidShape(6.0, 3.0, 6.0, 10.0, 13.0, 10.0)

        val shape = VoxelShapes.union(shape_top, shape_bottom, shape_shielding_1, shape_shielding_2, shape_shielding_3, shape_shielding_4, shape_core)
    }

}
