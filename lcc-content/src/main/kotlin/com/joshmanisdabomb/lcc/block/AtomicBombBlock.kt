package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.AtomicBombBlockEntity
import com.joshmanisdabomb.lcc.block.shape.RotatableShape
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity
import net.minecraft.block.*
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper.floor
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class AtomicBombBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(segment, AtomicBombSegment.MIDDLE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, segment).let {}

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = if (state[segment] == AtomicBombSegment.MIDDLE) AtomicBombBlockEntity(pos, state) else null

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val middle = pos.offset(state[HORIZONTAL_FACING], -state[segment].offset)
        if (world.getBlockEntity(middle) !is AtomicBombBlockEntity) return ActionResult.SUCCESS
        player.openHandledScreen(state.createScreenHandlerFactory(world, middle) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = when (state[segment]) {
        AtomicBombSegment.HEAD -> head[state[HORIZONTAL_FACING]]
        AtomicBombSegment.TAIL -> tail[state[HORIZONTAL_FACING]]
        else -> body[state[HORIZONTAL_FACING]]
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val direction = ctx.playerFacing.rotateYClockwise()
        val state = defaultState.with(HORIZONTAL_FACING, direction)
        val p1 = ctx.blockPos.offset(direction)
        val p2 = ctx.blockPos.offset(direction.opposite)
        if (!ctx.world.getBlockState(p1).canReplace(ctx) || !ctx.world.canPlace(state, p1, ShapeContext.absent())) return null
        if (!ctx.world.getBlockState(p2).canReplace(ctx) || !ctx.world.canPlace(state, p2, ShapeContext.absent())) return null
        return state
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        super.onPlaced(world, pos, state, placer, itemStack)
        if (!world.isClient) {
            val direction = state[HORIZONTAL_FACING]
            world.setBlockState(pos.offset(direction), state.with(segment, AtomicBombSegment.HEAD), 3)
            world.setBlockState(pos.offset(direction.opposite), state.with(segment, AtomicBombSegment.TAIL), 3)
            world.updateNeighborsAlways(pos, Blocks.AIR)
            state.prepare(world, pos, 3)
        }
        if (itemStack.hasCustomName()) (world.getBlockEntity(pos) as? AtomicBombBlockEntity)?.customName = itemStack.name
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? AtomicBombBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        world.blockTickScheduler.schedule(pos, this, 2)
        super.onBlockAdded(state, world, pos, oldState, notify)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, state2: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        val current = state[HORIZONTAL_FACING]
        when (state[segment]) {
            AtomicBombSegment.HEAD -> if (current == direction.opposite && !isSegment(state2, direction, AtomicBombSegment.MIDDLE)) return Blocks.AIR.defaultState
            AtomicBombSegment.TAIL -> if (current == direction && !isSegment(state2, direction, AtomicBombSegment.MIDDLE)) return Blocks.AIR.defaultState
            else -> {
                if (current == direction && !isSegment(state2, direction, AtomicBombSegment.HEAD)) return Blocks.AIR.defaultState
                if (current == direction.opposite && !isSegment(state2, direction.opposite, AtomicBombSegment.TAIL)) return Blocks.AIR.defaultState
            }
        }
        world.blockTickScheduler.schedule(pos, this, 2)
        return state
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (world.isClient) return
        if (world.isReceivingRedstonePower(pos)) {
            (world.getBlockEntity(pos.offset(state[HORIZONTAL_FACING], -state[segment].offset)) as? AtomicBombBlockEntity)?.detonate(null)
        } else {
            fall(state, world, pos.offset(state[HORIZONTAL_FACING], -state[segment].offset))
        }
    }

    protected fun fall(state: BlockState, world: World, pos: BlockPos) {
        val facing = state[HORIZONTAL_FACING]
        if (world.isClient) return
        if (pos.y <= world.bottomY || !canFall(world, pos) || !canFall(world, pos.offset(facing)) || !canFall(world, pos.offset(facing.opposite))) return
        (world.getBlockEntity(pos) as? AtomicBombBlockEntity)?.also {
            AtomicBombEntity(world, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, facing, it).also(world::spawnEntity)
            world.removeBlockEntity(pos)

            world.setBlockState(pos.offset(facing), Blocks.AIR.defaultState, 18)
            world.setBlockState(pos.offset(facing.opposite), Blocks.AIR.defaultState, 18)
            world.setBlockState(pos, Blocks.AIR.defaultState, 18)
        }
    }

    private fun canFall(world: World, pos: BlockPos) = pos.down().run { world.isAir(this) || FallingBlock.canFallThrough(world.getBlockState(this)) }

    private fun isSegment(state: BlockState, facing: Direction, segment: AtomicBombSegment) = state.block === this && state[HORIZONTAL_FACING] == facing && state[Companion.segment] == segment

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (world.isReceivingRedstonePower(pos)) {
            (world.getBlockEntity(pos.offset(state[HORIZONTAL_FACING], -state[segment].offset)) as? AtomicBombBlockEntity)?.detonate(null)
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos.offset(state[HORIZONTAL_FACING], -state[segment].offset)))
    }

    companion object {

        val segment = EnumProperty.of("segment", AtomicBombSegment::class.java)

        private val head: RotatableShape = VoxelShapes.union(
            createBodyShape(4.6863, 0.0, 12.0).offset(0.0, 0.0, 0.25),
            createBodyShape(5.1005, 1.0, 1.0).offset(0.0, 0.0, 0.1875),
            createBodyShape(5.5147, 2.0, 1.0).offset(0.0, 0.0, 0.125),
            createBodyShape(5.9289, 3.0, 1.0).offset(0.0, 0.0, 0.0625),
            createBodyShape(6.3431, 4.0, 1.0)
        ).rotatable(Direction.WEST)
        private val body: RotatableShape = createBodyShape(4.6863, 0.0, 16.0).rotatable(Direction.WEST)
        private val tail_1: VoxelShape = createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0)
        private val tail_2: VoxelShape = createCuboidShape(3.0, 3.0, 0.0, 13.0, 13.0, 8.0)
        private val tail: RotatableShape = VoxelShapes.union(tail_1, tail_2).rotatable(Direction.WEST)

        private fun createBodyShape(minH: Double, minV: Double, depth: Double): VoxelShape {
            val bodyWidth = 16.0 - minH.times(2.0)
            val points = floor(bodyWidth)
            var ret = VoxelShapes.empty()
            for (i in 0..points) {
                val j = i.toDouble().div(points)
                val k = 1 - j
                ret = VoxelShapes.union(ret, createCuboidShape(minH.times(k) + minV.times(j), minH.times(j) + minV.times(k), 0.0, 16.0 - (minH.times(k) + minV.times(j)), 16.0 - (minH.times(j) + minV.times(k)), depth))
            }
            return ret
        }

    }

    enum class AtomicBombSegment : StringIdentifiable {
        HEAD,
        MIDDLE,
        TAIL;

        override fun asString() = name.toLowerCase()

        val offset = 1-ordinal
    }

}