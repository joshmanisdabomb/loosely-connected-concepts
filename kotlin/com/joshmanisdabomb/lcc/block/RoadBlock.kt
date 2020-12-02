package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.SideShapeType
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess

class RoadBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(SHAPE, RoadShape.PATH).with(MARKINGS, RoadMarkings.NONE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(SHAPE, MARKINGS) }

    override fun hasSidedTransparency(state: BlockState)= state.get(SHAPE) != RoadShape.FULL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = when (state.get(SHAPE)) {
        RoadShape.HALF -> HALF_SHAPE
        RoadShape.PATH, null -> PATH_SHAPE
        RoadShape.FULL -> VoxelShapes.fullCube()
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        if (state.get(SHAPE) != RoadShape.HALF && direction == Direction.UP) {
            return state.with(SHAPE, if (world.getBlockState(posFrom).isSideSolid(world, posFrom, Direction.DOWN, SideShapeType.FULL)) RoadShape.FULL else RoadShape.PATH)
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom)
    }

    companion object {
        val SHAPE = EnumProperty.of("shape", RoadShape::class.java)
        val MARKINGS = EnumProperty.of("markings", RoadMarkings::class.java)

        val HALF_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0)
        val PATH_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)

        enum class RoadShape : StringIdentifiable {
            FULL,
            PATH,
            HALF;

            override fun asString() = name.toLowerCase()
        }

        enum class RoadMarkings : StringIdentifiable {
            NONE,
            WHITE,
            ORANGE,
            MAGENTA,
            LIGHT_BLUE,
            YELLOW,
            LIME,
            PINK,
            GRAY,
            LIGHT_GRAY,
            CYAN,
            PURPLE,
            BLUE,
            BROWN,
            GREEN,
            RED,
            BLACK,
            CINNABAR,
            MAROON,
            BRICK,
            TAN,
            GOLD,
            LIGHT_GREEN,
            MINT,
            TURQUOISE,
            NAVY,
            INDIGO,
            LAVENDER,
            LIGHT_PURPLE,
            HOT_PINK,
            BURGUNDY,
            ROSE,
            DARK_GRAY;

            override fun asString() = name.toLowerCase()
        }
    }

}