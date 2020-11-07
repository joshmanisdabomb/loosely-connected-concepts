package com.joshmanisdabomb.lcc.block

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FlowableFluid
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.BlockTags
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.GameRules
import net.minecraft.world.World
import java.util.*

class OilBlock(fluid: FlowableFluid, settings: Settings) : FluidBlock(fluid, settings) {

    init {
        arrayOf(Blocks.FIRE).forEach { FlammableBlockRegistry.getInstance(it).add(this, 3000, 300) }
    }

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType) = false

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        entity.slowMovement(state, Vec3d(0.38, 0.38, 0.38))
        if (!entity.isAlive) return
        if ((entity as? PlayerEntity)?.isCreative != true) {
            (entity as? LivingEntity)?.addStatusEffect(StatusEffectInstance(StatusEffects.GLOWING/* TODO LCCEffects.flammable*/, 400, 0))
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
                world.blockTickScheduler.schedule(pos, fire.block, world.random.nextInt(5) + 2)
                return false
            }
        }
        return flag
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        world.blockTickScheduler.schedule(pos, this, world.random.nextInt(5) + 2)
        if (!react(world, pos, state)) return
        super.onBlockAdded(state, world, pos, oldState, isMoving)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        world.blockTickScheduler.schedule(pos, this, world.random.nextInt(5) + 2)
        if (!react(world, pos, state)) return
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (Direction.values().none { world.getBlockState(pos.offset(it)).isIn(BlockTags.FIRE) }) return

        val fire = AbstractFireBlock.getState(world, pos).run { if (this == Blocks.FIRE) with(FireBlock.AGE, world.random.nextInt(2) + 13) else this }
        world.setBlockState(pos, fire, 18)
        world.blockTickScheduler.schedule(pos, fire.block, world.random.nextInt(5) + 2)

        if (world.gameRules.getBoolean(GameRules.DO_FIRE_TICK)) return
        Direction.values().forEach {
            if (world.random.nextInt(3) == 0) return@forEach
            val pos2 = pos.offset(it)
            val fire2 = AbstractFireBlock.getState(world, pos2).run { if (this == Blocks.FIRE) with(FireBlock.AGE, world.random.nextInt(2) + 13) else this }
            world.setBlockState(pos2, fire2, 18)
            world.blockTickScheduler.schedule(pos2, fire2.block, world.random.nextInt(5) + 2)
        }
    }

}