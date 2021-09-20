package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.exp
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.AbstractGlassBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*
import kotlin.math.ceil

open class ShatteredGlassBlock(val unbroken: Block, settings: Settings) : AbstractGlassBlock(settings), LCCBlockTrait {

    override fun onProjectileHit(world: World, state: BlockState, hit: BlockHitResult, projectile: ProjectileEntity) {
        smash(world, hit.blockPos)
    }

    override fun onLandedUpon(world: World, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
        smash(world, pos)
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        smashOnWalk(world, pos, entity)
    }

    override fun isSideInvisible(state: BlockState, stateFrom: BlockState, direction: Direction): Boolean {
        return stateFrom.isOf(unbroken) || super.isSideInvisible(state, stateFrom, direction)
    }

    override fun lcc_otherSideInvisible(state: BlockState, state2: BlockState, from: Direction) = if (state2.isOf(unbroken)) true else null

    companion object {
        val serverPositions = mutableMapOf<UUID, Pair<Long, Vec3d>>()

        fun smash(world: World, pos: BlockPos) {
            if (!world.isClient) world.breakBlock(pos, true)
        }

        fun smashOnWalk(world: World, pos: BlockPos, entity: Entity) {
            if (world.isClient) return
            val old = serverPositions[entity.uuid]
            val new = entity.pos
            if (old != null) {
                val speed = (new.x - old.second.x).exp(2) + (new.z - old.second.z).exp(2)
                if (speed > 0.003 && world.random.nextInt(ceil(0.3.div(speed)).toInt().coerceIn(2, 20)) == 0) {
                    smash(world, pos)
                }
                if (world.time <= old.first) return
            }
            serverPositions[entity.uuid] = world.time to new
        }
    }

}