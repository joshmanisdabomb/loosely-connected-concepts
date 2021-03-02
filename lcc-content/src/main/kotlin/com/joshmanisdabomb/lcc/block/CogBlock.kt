package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.perpendiculars
import com.joshmanisdabomb.lcc.extensions.setThrough
import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.network.BlockNetwork
import com.joshmanisdabomb.lcc.network.CogNetwork
import com.joshmanisdabomb.lcc.subblock.Subblock
import com.joshmanisdabomb.lcc.subblock.SubblockSystem
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.SideShapeType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.event.GameEvent

class CogBlock(settings: Settings) : Block(settings), LCCExtendedBlock, SubblockSystem {

    init {
        defaultState = stateManager.defaultState.run { cog_states.values.setThrough(this) { with(it, CogState.NONE) } }.with(cog_states[Direction.DOWN], CogState.INACTIVE)
    }

    val empty = defaultState.with(cog_states[Direction.DOWN], CogState.NONE)
    val network = CogNetwork()

    val cog_parts = Direction.values().map { it to Subblock(LCC.id("cog_${it.asString().toLowerCase()}"), shape[it], empty.with(cog_states[it], CogState.INACTIVE)) }.toMap()

    private fun getPartDirection(subblock: Subblock) = cog_parts.filterValues { it == subblock }.keys.first()

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(*cog_states.values.toTypedArray()).let {}

    override fun getSubblocks(state: BlockState, world: BlockView, pos: BlockPos) = cog_states.filterValues { state[it].exists }.map { (k, v) -> cog_parts[k]!! }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        val face = context.side
        val pos = context.blockPos
        val posFrom = context.blockPos.offset(face.opposite)
        val state = context.world.getBlockState(pos)
        val from = context.world.getBlockState(posFrom)
        val newState = if (state.block === this) state else empty

        if (!context.canReplaceExisting() && from.block === this) {
            val subblock = getSubblockFromTrace(from, context.world, posFrom, context.hitPos) ?: return null
            val face2 = getPartDirection(subblock)
            return if (face.opposite === face2) {
                null
            } else {
                if (!this.valid(face2, context.world, pos)) null else newState.with(cog_states[face2], CogState.INACTIVE)
            }
        }

        return if (!this.valid(face.opposite, context.world, pos)) null else newState.with(cog_states[face.opposite], CogState.INACTIVE)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, state2: BlockState, world: WorldAccess, pos: BlockPos, pos2: BlockPos): BlockState {
        if (state[cog_states[direction]].exists && !valid(direction, world, state2, pos2)) {
            if (Direction.values().count { state[cog_states[it]].exists } <= 1) {
                world.breakBlock(pos, true)
                return state.fluidState.blockState
            } else {
                val cog = empty.with(cog_states[direction], CogState.INACTIVE)
                world.syncWorldEvent(2001, pos, getRawIdFromState(cog))
                if (world is World) dropStacks(cog, world, pos, null, null, ItemStack.EMPTY)
                world.emitGameEvent(null, GameEvent.BLOCK_DESTROY, pos)
                return state.with(cog_states[direction], CogState.NONE)
            }
        }
        return state
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (placer != null) {
            val subblock = getSubblockFromTrace(state, world, pos, placer) ?: return updateNetwork(world, pos, null).let {}
            val face = getPartDirection(subblock)
            updateNetwork(world, pos, face)
        } else {
            updateNetwork(world, pos, null)
        }
        super.onPlaced(world, pos, state, placer, stack)
    }

    override fun canReplace(state: BlockState, context: ItemPlacementContext) = context.stack.item == this.asItem() && !context.canReplaceExisting()

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val subblock = breakSubblock(state, world, pos, player) { w, p, s, pl -> super.onBreak(w, p, s, pl) } ?: return
        val face = getPartDirection(subblock)
        val state2 = state.with(cog_states[face], CogState.NONE)
        if (state2 != empty) {
            if (!world.isClient) world.setBlockState(pos, state2, 3)
        } else {
            world.removeBlock(pos, false)
        }
        updateNeighbouringNetworks(state, world, pos, face)
        if (player.isSurvival) afterBreak(world, player, pos, subblock.single, null, player.mainHandStack.copy())
    }

    override fun getDroppedStacks(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack> {
        return Direction.values().filter { state[cog_states[it]].exists }.flatMap { super.getDroppedStacks(state, builder) }.toMutableList()
    }

    override fun lcc_overrideBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) = true

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        val vec = fromPos.subtract(pos)
        val side = Direction.fromVector(vec.x, vec.y, vec.z) ?: return
        if (!state[cog_states[side]!!].exists) return
        updateNetwork(world, pos, side)
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
    }

    override fun emitsRedstonePower(state: BlockState) = true

    override fun getStrongRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction) = 0

    override fun getWeakRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction) = direction.perpendiculars.any { state[cog_states[it]].active }.toInt(15)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        if (Direction.values().none { state[cog_states[it]].exists }) return VoxelShapes.fullCube()
        return getSubblockOutlineShape(state, world, pos, context)
    }

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.empty()

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = VoxelShapes.empty()

    override fun getVisualShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = getSubblockVisualShape(state, world, pos)

    protected fun valid(d: Direction, world: BlockView, pos: BlockPos): Boolean {
        val from = pos.offset(d)
        return valid(d, world, world.getBlockState(from), from)
    }

    protected fun valid(d: Direction, world: BlockView, from: BlockState, fromPos: BlockPos) = from.isSideSolid(world, fromPos, d, SideShapeType.FULL)

    fun isPowered(state: BlockState, world: BlockView, pos: BlockPos, side: Direction): Boolean? {
        if (!state[cog_states[side]!!].exists) return null
        if ((world as? World)?.getStrongRedstonePower(pos.offset(side, 2), side) ?: 0 > 0) return true
        return false
    }

    protected fun updateNetwork(world: World, pos: BlockPos, side: Direction?): BlockNetwork<Pair<BlockPos, Direction?>>.NetworkResult {
        val result = network.discover(world, pos to side)
        val nodes = result.nodes
        if (nodes["powered"]?.size ?: 0 > 0) {
            result.traversablesAssoc.forEach { (k, v) ->
                val state2 = world.getBlockState(k)
                var state3 = state2
                v.forEach cont@{ if (it.second == null) return@cont; state3 = state3.with(cog_states[it.second], if (nodes["ccw"]?.contains(it) == true) CogState.CCW else CogState.CW) }
                if (state3 != state2) world.setBlockState(k, state3)
            }
        } else {
            result.traversablesAssoc.forEach { (k, v) ->
                val state2 = world.getBlockState(k)
                var state3 = state2
                v.forEach cont@{ if (it.second == null) return@cont; state3 = state3.with(cog_states[it.second], CogState.INACTIVE) }
                if (state3 != state2) world.setBlockState(k, state3)
            }
        }
        return result
    }

    protected fun updateNeighbouringNetworks(state: BlockState, world: World, pos: BlockPos, face: Direction) {
        val positions = mutableSetOf<Pair<BlockPos, Direction?>>()
        val excludes = mutableSetOf<Pair<BlockPos, Direction?>>()
        for (d2 in face.perpendiculars) {
            if (state[cog_states[d2]].exists) {
                val pair = pos to d2
                positions.add(pair)
            }

            val p1 = pos.offset(d2)
            val c1 = world.getBlockState(p1)
            if (c1.block is CogBlock && c1[cog_states[face]].exists) {
                val pair = p1 to face
                positions.add(pair)
            }

            if (!c1.isSideSolid(world, pos.offset(d2), face.opposite, SideShapeType.FULL)) {
                val p2 = pos.offset(d2).offset(face)
                val c2 = world.getBlockState(p2)
                if (c2.block is CogBlock && c2[cog_states[d2.opposite]].exists) {
                    val pair = p2 to d2.opposite
                    positions.add(pair)
                }
            }
        }

        for (p in positions) {
            if (!excludes.contains(p)) {
                val result = updateNetwork(world, p.first, p.second)
                excludes.addAll(result.traversables)
            }
        }
    }

    companion object {
        val shape = createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0).rotatable

        val cog_states = Direction.values().map { it to EnumProperty.of(it.asString().toLowerCase(), CogState::class.java) }.toMap()
    }

    enum class CogState(active: Boolean?) : StringIdentifiable {
        NONE(null),
        INACTIVE(false),
        CW(true),
        CCW(true);

        val active = active == true
        val exists = active != null
        val inactive = active == false

        override fun asString() = name.toLowerCase()
    }

}