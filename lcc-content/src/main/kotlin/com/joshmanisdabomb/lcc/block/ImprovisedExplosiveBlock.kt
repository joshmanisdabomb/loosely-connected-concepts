package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.entity.projectile.thrown.ThrownEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion
import java.util.*

class ImprovisedExplosiveBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(ie_state, ImprovisedExplosiveState.INACTIVE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(ie_state).let {}

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (oldState.isOf(state.block)) return
        react(state, world, pos)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        react(state, world, pos)
    }

    override fun onDestroyedByExplosion(world: World, pos: BlockPos, explosion: Explosion) {
        world.setBlockState(pos, defaultState.with(ie_state, ImprovisedExplosiveState.IMMINENT))
        world.blockTickScheduler.schedule(pos, this, world.random.nextInt(20).plus(10))
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (state[ie_state] != ImprovisedExplosiveState.INACTIVE) explode(world, pos)
    }

    override fun onProjectileHit(world: World, state: BlockState, hit: BlockHitResult, projectile: ProjectileEntity) {
        if (world.isClient) return
        if (projectile !is ThrownEntity) {
            react(state, world, hit.blockPos, true)
        }
    }

    protected fun react(state: BlockState, world: World, pos: BlockPos, redstone: Boolean = world.isReceivingRedstonePower(pos)) {
        if (state[ie_state] == ImprovisedExplosiveState.INACTIVE && redstone) {
            world.setBlockState(pos, state.with(ie_state, ImprovisedExplosiveState.ACTIVE))
            world.blockTickScheduler.schedule(pos, this, world.random.nextInt(3).plus(3).times(20))
        } else if (state[ie_state] == ImprovisedExplosiveState.ACTIVE && !redstone) {
            explode(world, pos)
        }
    }

    protected fun explode(world: World, pos: BlockPos) {
        world.removeBlock(pos, false)
        world.createExplosion(null, null, null, pos.x.plus(0.5), pos.y.plus(0.5), pos.z.plus(0.5), 6.0F, true, Explosion.DestructionType.DESTROY)
    }

    override fun shouldDropItemsOnExplosion(explosion: Explosion) = false

    companion object {
        val ie_state = EnumProperty.of("state", ImprovisedExplosiveState::class.java)
    }

    enum class ImprovisedExplosiveState : StringIdentifiable {
        INACTIVE,
        ACTIVE,
        IMMINENT;

        override fun asString() = name.toLowerCase()
    }

}