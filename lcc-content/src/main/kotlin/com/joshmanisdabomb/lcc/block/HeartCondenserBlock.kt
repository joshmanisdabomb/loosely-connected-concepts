package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.block.entity.HeartCondenserBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class HeartCondenserBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(type, HeartState.NONE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(type).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = HeartCondenserBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? HeartCondenserBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this)
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomName()) (world.getBlockEntity(pos) as? HeartCondenserBlockEntity)?.customName = stack.name
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.heart_condenser, HeartCondenserBlockEntity::serverTick) else null

    enum class HeartState(val type: HeartType?) : StringIdentifiable {
        NONE(null),
        RED(HeartType.RED),
        IRON(HeartType.IRON),
        CRYSTAL(HeartType.CRYSTAL);

        override fun asString() = name.lowercase()

        companion object {
            val all = values().drop(1).toTypedArray()

            fun find(name: String) = all.find { it.asString().equals(name, ignoreCase = true) }
        }
    }

    companion object {
        val shape_center = createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)
        val shape_top1 = createCuboidShape(0.0, 14.0, 1.0, 16.0, 16.0, 15.0)
        val shape_top2 = createCuboidShape(1.0, 14.0, 0.0, 15.0, 16.0, 16.0)
        val shape_base1 = createCuboidShape(0.0, 1.0, 1.0, 16.0, 13.0, 15.0)
        val shape_base2 = createCuboidShape(1.0, 1.0, 0.0, 15.0, 13.0, 16.0)

        val shape = VoxelShapes.union(shape_center, shape_top1, shape_top2, shape_base1, shape_base2)

        val type = EnumProperty.of("type", HeartState::class.java)
    }

}
