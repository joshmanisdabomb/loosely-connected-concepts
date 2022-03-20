package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.abstracts.Temperature
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.tags.LCCEntityTags
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.thrown.ThrownEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.LEVEL_3
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*

class SaltBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(LEVEL_3, 1)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(LEVEL_3).let {}

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        world.createAndScheduleBlockTick(pos, this, world.random.nextInt(35).plus(5))
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val level = state.get(LEVEL_3)
        val pos2 = BlockPos.Mutable()
        var temp: Temperature? = null
        for (i in -1..1) {
            for (j in -1..1) {
                for (k in -1..1) {
                    if (i == 0 && j == 0 && k == 0) continue
                    pos2.set(pos, i, j, k)
                    val state2 = world.getBlockState(pos2)
                    val temp2 = (state2.block as? LCCContentBlockTrait)?.lcc_content_getTemperature(world, state2, pos2)
                    if (temp2 != null && (temp == null || temp2 >= temp)) {
                        temp = temp2
                    }
                }
            }
        }
        if (temp != null) {
            when (level) {
                3 -> if (temp >= Temperature.RED_HOT) world.setBlockState(pos, state.with(LEVEL_3, level.minus(1)))
                2 -> if (temp >= Temperature.SCALDING) world.setBlockState(pos, state.with(LEVEL_3, level.minus(1)))
                else -> {
                    world.setBlockState(pos, Blocks.AIR.defaultState)
                    return
                }
            }
        }
        world.createAndScheduleBlockTick(pos, this, world.random.nextInt(15).plus(5))
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shapes[state[LEVEL_3].minus(1)]

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return if (context is EntityShapeContext && context.entity is ThrownEntity) {
            shapes[2]
        } else {
            VoxelShapes.empty()
        }
    }

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity.type.isIn(LCCEntityTags.salt_weakness)) {
            entity.damage(LCCDamage.salt, state[LEVEL_3].div(3f))
        }
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val down = pos.down()
        val below = world.getBlockState(down)
        return isFaceFullSquare(below.getCollisionShape(world, down), Direction.UP)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (direction != Direction.DOWN) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
        return if (!state.canPlaceAt(world, pos)) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun canReplace(state: BlockState, context: ItemPlacementContext): Boolean {
        val level = state.get(LEVEL_3)
        if (context.stack.isOf(asItem())) {
            if (level > 3) return true
            if (context.canReplaceExisting()) {
                return context.side == Direction.UP
            }
        }
        return true
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val state = ctx.world.getBlockState(ctx.blockPos)
        if (state.isOf(this)) {
            return state.with(LEVEL_3, state.get(LEVEL_3).plus(1).coerceAtMost(3))
        } else {
            return super.getPlacementState(ctx)
        }
    }

    companion object {
        val shapes = arrayOf(
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0)
        )
    }

}
