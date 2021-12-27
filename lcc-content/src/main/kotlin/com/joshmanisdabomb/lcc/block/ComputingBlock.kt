package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.item.block.ComputingBlockItem
import com.joshmanisdabomb.lcc.subblock.Subblock
import com.joshmanisdabomb.lcc.subblock.SubblockSystem
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.enums.SlabType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.SLAB_TYPE
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockRenderView
import net.minecraft.world.BlockView
import net.minecraft.world.World


class ComputingBlock(settings: Settings) : BlockWithEntity(settings), LCCBlockTrait, SubblockSystem {

    val sbTop = Subblock(LCC.id("computing_top"), shapeTop, defaultState.with(SLAB_TYPE, SlabType.TOP))
    val sbBottom = Subblock(LCC.id("computing_bottom"), shapeBottom, defaultState.with(SLAB_TYPE, SlabType.BOTTOM))

    init {
        defaultState = stateManager.defaultState.with(SLAB_TYPE, SlabType.DOUBLE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(SLAB_TYPE).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = ComputingBlockEntity(pos, state)

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val subblock = getSubblockFromTrace(state, world, pos, hit.pos) ?: return ActionResult.SUCCESS
        val be = world.getBlockEntity(pos) as? ComputingBlockEntity ?: return ActionResult.SUCCESS
        return be.getHalf(subblock == sbTop)?.onUse(state, player, hand, hit) ?: super.onUse(state, world, pos, player, hand, hit)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val state = ctx.world.getBlockState(ctx.blockPos)
        val item = ctx.stack.item as ComputingBlockItem
        if (state.block == this) {
            val be = ctx.world.getBlockEntity(ctx.blockPos) as? ComputingBlockEntity
            be?.setHalf(item.getComputingHalf(be, ctx.stack, ctx.playerFacing.opposite, state[SLAB_TYPE] != SlabType.TOP))
            return state.with(SLAB_TYPE, SlabType.DOUBLE)
        } else {
            return when (ctx.side) {
                Direction.DOWN -> defaultState.with(SLAB_TYPE, SlabType.TOP)
                Direction.UP -> defaultState.with(SLAB_TYPE, SlabType.BOTTOM)
                else -> defaultState.with(SLAB_TYPE, if (ctx.hitPos.y - ctx.blockPos.y <= 0.5) SlabType.BOTTOM else SlabType.TOP)
            }
        }
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        val item = stack.item as ComputingBlockItem
        if (state[SLAB_TYPE] != SlabType.DOUBLE) {
            val top = state[SLAB_TYPE] == SlabType.TOP
            val be = world.getBlockEntity(pos) as? ComputingBlockEntity
            be?.setHalf(item.getComputingHalf(be, stack, placer?.horizontalFacing?.opposite ?: Direction.SOUTH, top))
        }
        super.onPlaced(world, pos, state, placer, stack)
    }

    override fun canReplace(state: BlockState, context: ItemPlacementContext): Boolean {
        if (state[SLAB_TYPE] != SlabType.DOUBLE && context.stack.item is ComputingBlockItem) {
            if (context.canReplaceExisting()) {
                val flag = context.hitPos.y - context.blockPos.y > 0.5
                if (state[SLAB_TYPE] == SlabType.BOTTOM) {
                    return context.side == Direction.UP || flag && context.side.axis.isHorizontal
                } else {
                    return context.side == Direction.DOWN || !flag && context.side.axis.isHorizontal
                }
            }
            return true
        }
        return false
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (state.block !== newState.block) {
            if (!world.isClient) {
                (world.getBlockEntity(pos) as? ComputingBlockEntity)?.getHalves()
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getSubblocks(state: BlockState, world: BlockView, pos: BlockPos) = when (state[SLAB_TYPE]) {
        SlabType.DOUBLE -> listOf(sbTop, sbBottom)
        SlabType.BOTTOM -> listOf(sbBottom)
        else -> listOf(sbTop)
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val subblock = breakSubblock(state, world, pos, player) { w, p, s, pl -> super.onBreak(w, p, s, pl) } ?: return
        val be = world.getBlockEntity(pos) as? ComputingBlockEntity
        if (state[SLAB_TYPE] == SlabType.DOUBLE) {
            if (!world.isClient) {
                be?.getHalf(subblock == sbTop)?.drop(world as ServerWorld)
            }
            be?.removeHalf(subblock == sbTop)
            world.setBlockState(pos, state.with(SLAB_TYPE, (subblock == sbTop).transform(SlabType.BOTTOM, SlabType.TOP)), 7)
        } else {
            world.removeBlock(pos, false)
        }
        if (player.isSurvival) afterBreak(world, player, pos, subblock.single, null, player.mainHandStack.copy())
    }

    override fun lcc_overrideBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) = true

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = getSubblockOutlineShape(state, world, pos, context)

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = getCullingShape(state, world, pos)

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = when (state[SLAB_TYPE]) {
        SlabType.TOP -> shapeTop
        SlabType.BOTTOM -> shapeBottom
        else -> VoxelShapes.fullCube()
    }

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = getSubblockVisualShape(state, world, pos)

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.computing, ComputingBlockEntity::serverTick) else null

    companion object {
        val shapeTop = createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0)
        val shapeBottom = createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)

        fun getTintColor(state: BlockState, world: BlockRenderView?, pos: BlockPos?, tintIndex: Int): Int {
            if (tintIndex == 1 && state[SLAB_TYPE] != SlabType.TOP) return (world?.getBlockEntity(pos) as? ComputingBlockEntity)?.getHalf(false)?.color ?: -1
            if (tintIndex == 2 && state[SLAB_TYPE] != SlabType.BOTTOM) return (world?.getBlockEntity(pos) as? ComputingBlockEntity)?.getHalf(true)?.color ?: -1
            return -1
        }
    }

}