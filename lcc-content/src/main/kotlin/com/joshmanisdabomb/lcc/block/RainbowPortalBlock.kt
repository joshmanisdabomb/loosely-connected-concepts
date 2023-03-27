package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RainbowGateBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties.HORIZONTAL_AXIS
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import kotlin.math.absoluteValue

class RainbowPortalBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(y, 0).with(HORIZONTAL_AXIS, Direction.Axis.X).with(middle, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(y, HORIZONTAL_AXIS, middle).let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = (state[HORIZONTAL_AXIS] == Direction.Axis.X).transform(x, z)

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (!state[middle] || state[y] != 0) return
        world.createAndScheduleBlockTick(pos, this, 2)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val axis = state[HORIZONTAL_AXIS]
        if (!state[middle] || state[y] != 0) return
        val middle = Vec3d.ofBottomCenter(pos)
        val length = 4.0
        val depth = 10.0
        val beam = Box.of(middle, if (axis == Direction.Axis.X) length else depth, 0.0, if (axis == Direction.Axis.Z) length else depth).stretch(0.0, 3.0, 0.0)
        val entities = world.getOtherEntities(null, beam)
        for (entity in entities) {
            if (entity is PlayerEntity) {
                if (entity.isSpectator || entity.abilities.flying) continue
            }
            val beam2 = beam.contract(if (axis == Direction.Axis.X) entity.boundingBox.xLength else 0.0, 0.0, if (axis == Direction.Axis.Z) entity.boundingBox.zLength else 0.0)
            if (!entity.boundingBox.intersects(beam2)) continue
            val direction = entity.pos.subtract(middle).withAxis(axis, 0.0).withAxis(Direction.Axis.Y, 0.0)
            val distance = direction.length()
            if (distance.absoluteValue < 0.2) continue
            entity.velocity = entity.velocity.add(direction.normalize().negate().multiply(0.015).multiply(depth.div(2.0).minus(distance)))
            entity.velocityModified = true
            entity.velocityDirty = true
        }
        world.createAndScheduleBlockTick(pos, this, 2)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        val y = state[y]
        val axis = state[HORIZONTAL_AXIS]
        if (direction == Direction.UP && y != 2 && neighborState != state.with(Companion.y, y + 1)) return Blocks.AIR.defaultState
        if (direction == Direction.DOWN && y != 0 && neighborState != state.with(Companion.y, y - 1)) return Blocks.AIR.defaultState
        if (direction == Direction.DOWN && y == 0 && !canPlaceAt(state, world, pos)) return Blocks.AIR.defaultState
        return if (direction.axis == axis && neighborState != state && neighborState != LCCBlocks.rainbow_gate.defaultState) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val down = pos.down()
        val state2 = world.getBlockState(down)
        if (state[y] != 0) {
            return state2.isOf(this) && state2[HORIZONTAL_AXIS] == state[HORIZONTAL_AXIS]
        } else if (state2.isSideSolid(world, down, Direction.UP, SideShapeType.FULL)) {
            return true
        }
        return false
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state[middle] && !newState.isOf(this)) {
            val axis = state[HORIZONTAL_AXIS]
            for (ad in Direction.AxisDirection.values()) {
                val d = Direction.get(ad, axis)
                val pos2 = pos.offset(d)
                val gate = world.getBlockEntity(pos2) as? RainbowGateBlockEntity ?: continue
                if (gate.isMain) gate.portalBroken()
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        val shape = getOutlineShape(state, world, pos, ShapeContext.of(entity))
        if (entity.boundingBox.intersects(shape.offset(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()).boundingBox)) {
            println("hello")
        }
        super.onEntityCollision(state, world, pos, entity)
    }

    companion object {
        val x = createCuboidShape(0.0, 0.0, 7.0, 16.0, 16.0, 9.0)
        val z = createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 16.0)
        val y = IntProperty.of("y", 0, 2)
        val middle = BooleanProperty.of("middle")
    }

}
