package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.perpendiculars
import com.joshmanisdabomb.lcc.extensions.toInt
import com.joshmanisdabomb.lcc.network.CogNetwork
import com.joshmanisdabomb.lcc.subblock.Subblock
import com.joshmanisdabomb.lcc.subblock.SubblockSystem
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.SideShapeType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class CogBlock(settings: Settings) : Block(settings), SubblockSystem {

    init {
        defaultState = stateManager.defaultState.apply { cog_states.values.forEach { with(it, CogState.NONE) } }.with(cog_states[Direction.DOWN], CogState.INACTIVE)
    }

    val empty = defaultState.with(cog_states[Direction.DOWN], CogState.NONE)
    val network = CogNetwork()

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(*cog_states.values.toTypedArray()).let {}

    override fun getSubblocks(state: BlockState, world: BlockView, pos: BlockPos) = cog_states.filterValues { state[it].exists }.map { (k, v) -> cog_parts[k]!! }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        val vec = fromPos.subtract(pos)
        val side = Direction.fromVector(vec.x, vec.y, vec.z) ?: return
        if (!state[cog_states[side]!!].exists) return
        updateNetwork(state, world, pos, side)
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState? {
        val face = context.side
        val pos = context.blockPos
        val posFrom = context.blockPos.offset(face.opposite)
        val state = context.world.getBlockState(pos)
        val from = context.world.getBlockState(posFrom)
        val newState = if (state.block === this) state else empty

        if (!context.canReplaceExisting() && from.block === this) {
            val subblock = getSubblockFromTrace(from, context.world, posFrom, context.hitPos)
            val face2 = cog_parts.filterValues { it == subblock }.keys.first()
            return if (face.opposite === face2) {
                null
            } else {
                if (!this.valid(face2, context.world, pos)) null else newState.with(cog_states[face2], CogState.INACTIVE)
            }
        }

        return if (!this.valid(face.opposite, context.world, pos)) null else newState.with(cog_states[face.opposite], CogState.INACTIVE)
    }

    override fun canReplace(state: BlockState, context: ItemPlacementContext) = context.stack.item == this.asItem() && !context.canReplaceExisting()

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
        return world.getBlockState(from).isSideSolid(world, from, d, SideShapeType.FULL)
    }

    fun isPowered(state: BlockState, world: World, pos: BlockPos, side: Direction): Boolean? {
        if (!state[cog_states[side]!!].exists) return null
        if (world.getStrongRedstonePower(pos.offset(side, 2), side) > 0) return true
        return false
    }

    protected fun updateNetwork(state: BlockState, world: World, pos: BlockPos, side: Direction) {
        val result = network.discover(world, pos to side)
        val nodes = result.nodes
        if (nodes["powered"]?.size ?: 0 > 0) {
            result.traversablesAssoc.forEach { (k, v) ->
                val state2 = world.getBlockState(k)
                var state3 = state2
                v.forEach { state3 = state3.with(cog_states[it.second], if (nodes["ccw"]?.contains(it) == true) CogState.CCW else CogState.CW) }
                if (state3 != state2) world.setBlockState(k, state3)
            }
        } else {
            result.traversablesAssoc.forEach { (k, v) ->
                val state2 = world.getBlockState(k)
                var state3 = state2
                v.forEach { state3 = state3.with(cog_states[it.second], CogState.INACTIVE) }
                if (state3 != state2) world.setBlockState(k, state3)
            }
        }
    }

    companion object {
        val shape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0).rotatable

        val cog_states = Direction.values().map { it to EnumProperty.of(it.asString().toLowerCase(), CogState::class.java) }.toMap()
        val cog_parts = Direction.values().map { it to Subblock(LCC.id("cog_${it.asString().toLowerCase()}"), shape[it]) }.toMap()
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