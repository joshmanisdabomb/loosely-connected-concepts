package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.block.enums.WireConnection
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.LIT
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import net.minecraft.world.explosion.Explosion
import java.util.*
import net.minecraft.block.RedstoneWireBlock.DIRECTION_TO_WIRE_CONNECTION_PROPERTY as directionProperties

class ExplosivePasteBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(LIT, false).apply { directionProperties.values.forEach { this.with(it, WireConnection.NONE) } }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(LIT).run { directionProperties.values.forEach { this.add(it) } }

    override fun prepare(state: BlockState, world: WorldAccess, pos: BlockPos, flags: Int, maxUpdateDepth: Int) {
        val mutable = BlockPos.Mutable()
        for (d in Direction.Type.HORIZONTAL) {
            val wire = state[directionProperties[d]]
            if (wire != WireConnection.NONE && !world.getBlockState(mutable.set(pos, d)).isOf(this)) {
                mutable.move(Direction.DOWN)
                val blockState = world.getBlockState(mutable)
                if (!blockState.isOf(Blocks.OBSERVER)) {
                    val blockPos = mutable.offset(d.opposite)
                    val blockState2 = blockState.getStateForNeighborUpdate(d.opposite, world.getBlockState(blockPos), world, mutable, blockPos)
                    replace(blockState, blockState2, world, mutable, flags, maxUpdateDepth)
                }

                mutable.set(pos, d).move(Direction.UP)
                val blockState3 = world.getBlockState(mutable)
                if (!blockState3.isOf(Blocks.OBSERVER)) {
                    val blockPos2 = mutable.offset(d.opposite)
                    val blockState4 = blockState3.getStateForNeighborUpdate(d.opposite, world.getBlockState(blockPos2), world, mutable, blockPos2)
                    replace(blockState3, blockState4, world, mutable, flags, maxUpdateDepth)
                }
            }
        }
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (oldState.isOf(state.block) || world.isClient) return
    }

    override fun onDestroyedByExplosion(world: World, pos: BlockPos, explosion: Explosion) {
        if (world.isClient) return
        world.setBlockState(pos, getWireState(world, defaultState.with(LIT, true), pos))
        if (!world.blockTickScheduler.isScheduled(pos, this)) {
            world.blockTickScheduler.schedule(pos, this, world.random.nextInt(10).plus(10))
        }
        (explosion.causingEntity as? ServerPlayerEntity)?.also { LCCCriteria.explosive_paste.trigger(it, pos) }
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (world.isClient) return
        if (state[LIT]) return
        if (!state.canPlaceAt(world, pos)) {
            world.blockTickScheduler.schedule(pos, this, world.random.nextInt(10).plus(10))
        }
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (state[LIT]) {
            world.removeBlock(pos, false)
            world.createExplosion(null, pos.x.plus(0.5), pos.y.plus(0.5), pos.z.plus(0.5), directionProperties.values.count { state[it].isConnected }.times(0.9f).plus(2.5f), Explosion.DestructionType.DESTROY)
        } else {
            dropStacks(state, world, pos)
            world.removeBlock(pos, false)
        }
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.union(dot, *directionProperties.mapNotNull { (k, v) ->
        when (state[v]) {
            WireConnection.UP -> up[k]
            WireConnection.SIDE -> side[k]
            else -> null
        }
    }.toTypedArray())

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val pos2 = pos.down()
        return canRunOn(world.getBlockState(pos2), world, pos2)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (state[LIT]) return state

        if (direction == Direction.DOWN) return state
        if (direction == Direction.UP) return getWireState(world, state, pos)

        return state.with(directionProperties[direction], getConnection(world, pos, direction))
    }

    override fun getPlacementState(ctx: ItemPlacementContext) = getWireState(ctx.world, defaultState, ctx.blockPos)

    fun getWireState(world: BlockView, state: BlockState, pos: BlockPos): BlockState {
        val uncovered = !world.getBlockState(pos.up()).isSolidBlock(world, pos)
        var state2 = state
        for (d in Direction.Type.HORIZONTAL) {
            state2 = state2.with(directionProperties[d], getConnection(world, pos, d, uncovered))
        }
        return state2
    }

    private fun getConnection(world: BlockView, pos: BlockPos, side: Direction, uncovered: Boolean = !world.getBlockState(pos.up()).isSolidBlock(world, pos)): WireConnection {
        val pos2 = pos.offset(side)
        val state2 = world.getBlockState(pos2)
        if (uncovered) {
            if (world.getBlockState(pos2.up()).isOf(this) && canRunOn(state2, world, pos2)) {
                return if (state2.isSideSolidFullSquare(world, pos2, side.opposite)) WireConnection.UP else WireConnection.SIDE
            }
        }

        return if (!state2.isOf(this) && (state2.isSolidBlock(world, pos2) || !world.getBlockState(pos2.down()).isOf(this))) WireConnection.NONE else WireConnection.SIDE
    }

    private fun canRunOn(state: BlockState, world: BlockView, pos: BlockPos) = (state.isSideSolidFullSquare(world, pos, Direction.UP) || state.isOf(Blocks.HOPPER))

    override fun shouldDropItemsOnExplosion(explosion: Explosion) = false

    companion object {
        val dot = createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0)
        val side = createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0).rotatable(Direction.NORTH)
        val up = VoxelShapes.union(createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0), createCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)).rotatable(Direction.NORTH)
    }

}