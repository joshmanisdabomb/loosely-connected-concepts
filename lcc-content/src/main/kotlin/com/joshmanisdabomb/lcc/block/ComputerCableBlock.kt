package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.enums.SlabType
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess

class ComputerCableBlock(settings: Settings) : AbstractCableBlock<Boolean, ComputerCableBlock.ComputerCableConnection, Boolean>(settings) {

    override val topProperty get() = Properties.UP
    override val sideProperties get() = connectProperties
    override val bottomProperty get() = Properties.DOWN

    override fun getTopConnection(world: WorldAccess, state: BlockState, pos: BlockPos, state2: BlockState, pos2: BlockPos) = when (state2.block) {
        is ComputerCableBlock -> true
        is TerminalBlock -> true
        is ComputingBlock -> state2[Properties.SLAB_TYPE] != SlabType.TOP
        else -> false
    }

    override fun getSideConnection(world: WorldAccess, state: BlockState, pos: BlockPos, side: Direction, state2: BlockState, pos2: BlockPos) = when (state2.block) {
        is ComputerCableBlock -> ComputerCableConnection.FULL
        is TerminalBlock -> ComputerCableConnection.FULL
        is ComputingBlock -> {
            when (state2[Properties.SLAB_TYPE]) {
                SlabType.TOP -> ComputerCableConnection.TOP
                SlabType.BOTTOM -> ComputerCableConnection.BOTTOM
                else -> ComputerCableConnection.FULL
            }
        }
        else -> ComputerCableConnection.NONE
    }

    override fun getBottomConnection(world: WorldAccess, state: BlockState, pos: BlockPos, state2: BlockState, pos2: BlockPos) = when (state2.block) {
        is ComputerCableBlock -> true
        is TerminalBlock -> true
        is ComputingBlock -> state2[Properties.SLAB_TYPE] != SlabType.BOTTOM
        else -> false
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        val list = mutableListOf<VoxelShape>()
        if (state[topProperty]) {
            list.add(BooleanCableBlock.cable_shapes[Direction.UP])
        } else if (horizontalDirections.any { state[sideProperties[it]] == ComputerCableConnection.TOP }) {
            list.add(cable_elbow_top)
        }
        if (state[bottomProperty]) {
            list.add(BooleanCableBlock.cable_shapes[Direction.DOWN])
        } else if (horizontalDirections.any { state[sideProperties[it]] == ComputerCableConnection.BOTTOM }) {
            list.add(cable_elbow_bottom)
        }
        for (d in horizontalDirections) {
            when (state[sideProperties[d]]) {
                ComputerCableConnection.TOP -> list.add(cable_shapes_top[d])
                ComputerCableConnection.BOTTOM -> list.add(cable_shapes_bottom[d])
                ComputerCableConnection.FULL -> list.add(BooleanCableBlock.cable_shapes[d])
                else -> Unit
            }
        }
        return VoxelShapes.union(BooleanCableBlock.cable_shape, *list.toTypedArray())
    }

    companion object {
        val cable_elbow_top = createCuboidShape(6.0, 10.0, 6.0, 10.0, 14.0, 10.0)
        val cable_elbow_bottom = createCuboidShape(6.0, 2.0, 6.0, 10.0, 6.0, 10.0)
        val cable_shapes_top = createCuboidShape(6.0, 10.0, 2.0, 10.0, 16.0, 6.0).rotatable
        val cable_shapes_bottom = createCuboidShape(6.0, 10.0, 10.0, 10.0, 16.0, 14.0).rotatable

        val connectProperties = horizontalDirections.associateWith { EnumProperty.of(it.name.lowercase(), ComputerCableConnection::class.java) }
    }

    enum class ComputerCableConnection : StringIdentifiable {
        NONE,
        TOP,
        BOTTOM,
        FULL;

        override fun asString() = name.lowercase()
    }

}