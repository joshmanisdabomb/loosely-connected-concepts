package com.joshmanisdabomb.lcc.trait

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

interface LCCBlockTrait {

    @JvmDefault
    fun lcc_onEntityCollisionGroupedByBlock(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) = Unit

    @JvmDefault
    fun lcc_onEntityCollisionGroupedByClass(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) = Unit

    @JvmDefault
    fun lcc_onEntityJumpGroupedByBlock(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: LivingEntity) = false

    @JvmDefault
    fun lcc_isPlantable(state: BlockState, world: BlockView, pos: BlockPos, plant: Block): Boolean? = null

    @JvmDefault
    fun lcc_isSoil(state: BlockState): Boolean? = null

    @JvmDefault
    fun lcc_spawnOn(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, yaw: Float, spawnPointSet: Boolean, alive: Boolean): Optional<Vec3d>? = null

    @JvmDefault
    fun lcc_spawnSet(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, oldWorld: ServerWorld?, oldState: BlockState?, oldPos: BlockPos?, yaw: Float, spawnPointSet: Boolean, alive: Boolean) = Unit

    @JvmDefault
    fun lcc_spawnRemoved(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, newWorld: ServerWorld?, newState: BlockState?, newPos: BlockPos?, yaw: Float, spawnPointSet: Boolean, alive: Boolean) = Unit

    @JvmDefault
    fun lcc_spawnAfter(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, yaw: Float, spawnPointSet: Boolean, alive: Boolean) = Unit

    @JvmDefault
    fun lcc_overrideBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity): Boolean? = null

    @JvmDefault
    fun lcc_onEntityNearby(world: World, state: BlockState, pos: BlockPos, entity: Entity, distSq: Double) = Unit

}