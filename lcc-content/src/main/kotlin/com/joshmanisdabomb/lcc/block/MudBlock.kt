package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sin

class MudBlock(settings: Settings) : Block(settings) {

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun getSidesShape(state: BlockState, world: BlockView, pos: BlockPos) = VoxelShapes.fullCube()

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.fullCube()

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType) = false

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (world.getBiome(pos).getTemperature(pos) >= 1.21f && !LCCBlocks.cracked_mud.hardensOnAnySide(world, pos)) {
            world.setBlockState(pos, LCCBlocks.cracked_mud.defaultState)
        }
    }

    override fun getSlipperiness() = sin(System.currentTimeMillis().times(1.8).rem(1000L).div(1000.0).times(PI)).times(0.4).plus(0.41).toFloat()

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (abs(entity.velocity.y) > 0.02) {
            entity.slowMovement(defaultState, Vec3d(0.0, 1.0, 0.0))
            if (entity.velocity.y.sign == -1.0) {
                entity.velocity = entity.velocity.multiply(0.0, 0.0, 0.0)
                entity.velocityDirty = true
            } else {
                entity.velocity = entity.velocity.multiply(0.0, 0.7, 0.0)
                entity.velocityDirty = true
            }
        }
    }

    companion object {
        val shape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 11.0, 16.0)
    }

}