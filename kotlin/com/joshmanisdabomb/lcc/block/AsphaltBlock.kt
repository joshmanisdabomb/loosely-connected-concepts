package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.FlowableFluid.FALLING
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.AGE_7
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*
import net.minecraft.fluid.FlowableFluid.LEVEL as FLUID_LEVEL

class AsphaltBlock(fluid: FlowableFluid, settings: Settings) : FluidBlock(fluid, settings), LCCExtendedBlock {

    init {
        defaultState = stateManager.defaultState.with(LEVEL, 0).with(AGE_7, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(AGE_7) }

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType) = false

    override fun onEntitySingleCollision(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        entity.slowMovement(states.first(), Vec3d(0.16, 0.2, 0.16))
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        world.fluidTickScheduler.schedule(pos, state.fluidState.fluid, fluid.getTickRate(world).times(state.get(AGE_7).times(2).plus(1)))
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        if (world.getBlockState(posFrom).isOf(LCCBlocks.road)) return state
        if (state.fluidState.isStill || newState.fluidState.isStill) {
            world.fluidTickScheduler.schedule(pos, state.fluidState.fluid, fluid.getTickRate(world).times(state.get(AGE_7).times(2).plus(1)))
        }
        return state
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (world.getBlockState(fromPos).isOf(LCCBlocks.road)) return
        world.fluidTickScheduler.schedule(pos, state.fluidState.fluid, fluid.getTickRate(world).times(state.get(AGE_7).times(2).plus(1)))
    }

    override fun hasRandomTicks(state: BlockState) = true

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (state.get(AGE_7) == 7) {
            if (world.getBlockState(pos.down()).isOf(this)) return

            val level = state.level
            val surrounding = Direction.values().filter { it.horizontal > -1 }.mapNotNull { world.getBlockState(pos.offset(it)).apply { if (!this.isOf(this@AsphaltBlock) || this.fluidState.get(FALLING)) return@mapNotNull null }.level }.toIntArray().min()

            if (surrounding == null || surrounding >= level) {
                world.setBlockState(pos, LCCBlocks.road.inner(world, LCCBlocks.road.defaultState.with(RoadBlock.SHAPE, RoadBlock.Companion.RoadShape.PATH), pos))
                LCCBlocks.road.updateRoads(world, pos)
                val posU = pos.up()
                val stateU = world.getBlockState(posU)
                if (stateU.isOf(this) && stateU.level != 8 && !stateU.fluidState.get(FALLING)) {
                    world.setBlockState(posU, LCCBlocks.road.inner(world, LCCBlocks.road.defaultState.with(RoadBlock.SHAPE, RoadBlock.Companion.RoadShape.HALF), posU))
                    LCCBlocks.road.updateRoads(world, posU)
                }
            }
        } else {
            if (random.nextInt(4) != 0) return
            world.setBlockState(pos, state.cycle(AGE_7), 2)
            val age = state.get(AGE_7)
            Direction.values().forEach {
                val pos2 = pos.offset(it)
                val state2 = world.getBlockState(pos2).apply { if (!this.isOf(this@AsphaltBlock)) return@forEach }
                val age2 = state2.get(AGE_7)
                if (age2 < age) world.setBlockState(pos2, state2.with(AGE_7, age2.plus(random.nextInt(3)).coerceAtMost(age)), 2)
            }
        }
    }



    private val BlockState.level get() = if (this.fluidState.isStill) 8 else this.fluidState.get(FLUID_LEVEL)

}