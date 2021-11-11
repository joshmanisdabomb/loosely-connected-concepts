package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.replaceVelocity
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FlowableFluid
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.tag.BlockTags
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.GameRules
import net.minecraft.world.World
import java.util.*

class OilBlock(fluid: FlowableFluid, settings: Settings) : FluidBlock(fluid, settings), LCCBlockTrait {

    companion object {
        val GEYSER: BooleanProperty = BooleanProperty.of("geyser")
    }

    init {
        defaultState = stateManager.defaultState.with(LEVEL, 0).with(GEYSER, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = super.appendProperties(builder).also { builder.add(GEYSER) }

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType) = false

    override fun lcc_onEntityCollisionGroupedByBlock(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        if (states.none { it[GEYSER] }) {
            entity.slowMovement(states.first(), Vec3d(0.38, 0.4, 0.38))
        } else {
            entity.replaceVelocity(y = entity.velocity.y.plus(0.25).coerceAtMost(0.65))
        }

        if (!entity.isAlive) return
        if ((entity as? PlayerEntity)?.isSurvival != false) {
            (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(LCCEffects.flammable, 400, 0))
        }
    }

    private fun react(world: World, pos: BlockPos, state: BlockState): Boolean {
        val flag = true
        for (d in Direction.values()) {
            val bp = pos.offset(d)
            val fluid = world.getFluidState(bp)
            if (fluid.isIn(FluidTags.LAVA)) {
                val fire = AbstractFireBlock.getState(world, pos).run { if (this == Blocks.FIRE) with(FireBlock.AGE, world.random.nextInt(2) + 13) else this }
                world.setBlockState(pos, fire, 18)
                world.createAndScheduleBlockTick(pos, fire.block, world.random.nextInt(5) + 2)
                return false
            }
        }
        return flag
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        world.createAndScheduleBlockTick(pos, this, world.random.nextInt(5) + 2)
        if (!react(world, pos, state)) return
        super.onBlockAdded(state, world, pos, oldState, isMoving)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        world.createAndScheduleBlockTick(pos, this, world.random.nextInt(5) + 2)
        if (!react(world, pos, state)) return
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (Direction.values().none { world.getBlockState(pos.offset(it)).isIn(BlockTags.FIRE) }) return

        val fire = AbstractFireBlock.getState(world, pos).run { if (this == Blocks.FIRE) with(FireBlock.AGE, world.random.nextInt(2) + 13) else this }
        world.setBlockState(pos, fire, 18)
        world.createAndScheduleBlockTick(pos, fire.block, world.random.nextInt(5) + 2)

        if (world.gameRules.getBoolean(GameRules.DO_FIRE_TICK)) return
        Direction.values().forEach {
            if (world.random.nextInt(3) == 0) return@forEach
            val pos2 = pos.offset(it)
            val fire2 = AbstractFireBlock.getState(world, pos2).run { if (this == Blocks.FIRE) with(FireBlock.AGE, world.random.nextInt(2) + 13) else this }
            world.setBlockState(pos2, fire2, 18)
            world.createAndScheduleBlockTick(pos2, fire2.block, world.random.nextInt(5) + 2)
        }
    }

}