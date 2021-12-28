package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.setThrough
import net.minecraft.block.Block
import net.minecraft.block.Block.createCuboidShape
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess

open class BooleanCableBlock(settings: Settings, val connector: (world: WorldAccess, state: BlockState, pos: BlockPos, direction: Direction, state2: BlockState, pos2: BlockPos) -> Boolean) : AbstractCableBlock<Boolean, Boolean, Boolean>(settings) {

    override val topProperty get() = Properties.UP
    override val sideProperties get() = horizontalDirections.associateWith { it.booleanProperty }
    override val bottomProperty get() = Properties.DOWN

    val properties = sideProperties.plus(mapOf(Direction.UP to topProperty, Direction.DOWN to bottomProperty))

    override fun getTopConnection(world: WorldAccess, state: BlockState, pos: BlockPos, state2: BlockState, pos2: BlockPos) = connector(world, state, pos, Direction.UP, state2, pos2)

    override fun getSideConnection(world: WorldAccess, state: BlockState, pos: BlockPos, side: Direction, state2: BlockState, pos2: BlockPos) = connector(world, state, pos, side, state2, pos2)

    override fun getBottomConnection(world: WorldAccess, state: BlockState, pos: BlockPos, state2: BlockState, pos2: BlockPos) = connector(world, state, pos, Direction.DOWN, state2, pos2)

    override fun getTopDefault() = false
    override fun getSideDefault() = false
    override fun getBottomDefault() = false

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.union(cable_shape, *properties.filterValues { state[it] }.map { (k, v) -> cable_shapes[k] }.toTypedArray())

    companion object {
        val cable_shape = createCuboidShape(6.0, 6.0, 6.0, 10.0, 10.0, 10.0)
        val cable_shapes = createCuboidShape(6.0, 10.0, 6.0, 10.0, 16.0, 10.0).rotatable
    }

}