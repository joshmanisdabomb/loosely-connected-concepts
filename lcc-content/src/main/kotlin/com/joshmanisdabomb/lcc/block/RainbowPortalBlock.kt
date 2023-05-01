package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RainbowGateBlockEntity
import com.joshmanisdabomb.lcc.component.PortalChargeComponent
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.transform
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties.HORIZONTAL_AXIS
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.*

class RainbowPortalBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(y, 0).with(HORIZONTAL_AXIS, Direction.Axis.X).with(middle, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(y, HORIZONTAL_AXIS, middle).let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = (state[HORIZONTAL_AXIS] == Direction.Axis.X).transform(x, z)

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
        if (entity.canUsePortals() && entity.boundingBox.intersects(shape.offset(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()).boundingBox)) {
            val charge = LCCComponents.portal_charge.maybeGet(entity).orElseThrow()
            val ticks = charge.get(PortalChargeComponent.PortalType.RAINBOW)
            if (ticks >= 80) {
                if (world.isClient) return
                val sworld = world as? ServerWorld ?: return
                val destinations = LCCComponents.portal_destinations.maybeGet(world.levelProperties).orElseThrow()
                val gate = getGate(state, world, pos) ?: return
                val code = gate.code ?: return
                val positions = destinations.getPositions(code)
                val position = Util.getRandom(positions, world.random)
                val destWorld = sworld.server.getWorld(position.dimension) ?: return
                FabricDimensions.teleport(entity, destWorld, TeleportTarget(Vec3d.ofBottomCenter(position.pos), Vec3d.ZERO, entity.yaw, entity.pitch))
            } else {
                charge.tick(PortalChargeComponent.PortalType.RAINBOW)
            }
        }
        super.onEntityCollision(state, world, pos, entity)
    }

    private fun getGate(state: BlockState, world: World, pos: BlockPos): RainbowGateBlockEntity? {
        val y = state[y]
        val mb = BlockPos.Mutable()
        val direction = Direction.get(Direction.AxisDirection.POSITIVE, state[HORIZONTAL_AXIS])
        for (i in -3..3) {
            for (j in -y..2-y) {
                val gate = world.getBlockEntity(mb.set(pos).move(direction, i).move(Direction.UP, j)) as? RainbowGateBlockEntity ?: continue
                if (!gate.isMain) continue
                if (!gate.withinStructure(pos)) continue
                return gate
            }
        }
        return null
    }

    companion object {
        val x = createCuboidShape(0.0, 0.0, 7.0, 16.0, 16.0, 9.0)
        val z = createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 16.0)
        val y = IntProperty.of("y", 0, 2)
        val middle = BooleanProperty.of("middle")
    }

}
