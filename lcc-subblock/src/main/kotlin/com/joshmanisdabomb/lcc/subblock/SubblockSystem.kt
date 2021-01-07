package com.joshmanisdabomb.lcc.subblock

import com.joshmanisdabomb.lcc.extensions.to
import net.minecraft.block.BlockState
import net.minecraft.block.EntityShapeContext
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.RaycastContext

interface SubblockSystem {

    fun getSubblocks(state: BlockState, world: BlockView, pos: BlockPos): List<Subblock>

    fun getSubblockOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        val subblocks = getSubblocks(state, world, pos)

        run default@{
            if (context !is EntityShapeContext) return@default
            val hit = getSubblocksFromTrace(state, world, pos, context.entity.orElse(null) ?: return@default, subblocks).map { it.shape }
            if (hit.isEmpty()) return VoxelShapes.empty()
            return VoxelShapes.union(hit.first(), *hit.drop(1).toTypedArray())
        }

        return getSubblockVisualShapeInternal(subblocks)
    }

    fun getSubblockVisualShape(state: BlockState, world: BlockView, pos: BlockPos): VoxelShape {
        val subblocks = getSubblocks(state, world, pos)
        return getSubblockVisualShapeInternal(subblocks)
    }

    fun getSubblocksFromTrace(state: BlockState, world: BlockView, pos: BlockPos, entity: Entity, subblocks: List<Subblock> = getSubblocks(state, world, pos)): List<Subblock> {
        val result = entityRaycast(entity, ((entity as? PlayerEntity)?.isCreative ?: false).to(5.0, 4.5), 1.0f, false)
        return getSubblocksFromTrace(state, world, pos, result.pos, subblocks)
    }

    fun getSubblocksFromTrace(state: BlockState, world: BlockView, pos: BlockPos, hit: Vec3d, subblocks: List<Subblock> = getSubblocks(state, world, pos)) = subblocks.filter { it.shape.boundingBoxes.any { box -> box.expand(0.001).contains(hit.subtract(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())) } }

    fun getSubblockFromTrace(state: BlockState, world: BlockView, pos: BlockPos, entity: Entity, subblocks: List<Subblock> = getSubblocks(state, world, pos)) = getSubblocksFromTrace(state, world, pos, entity, subblocks).firstOrNull()

    fun getSubblockFromTrace(state: BlockState, world: BlockView, pos: BlockPos, hit: Vec3d, subblocks: List<Subblock> = getSubblocks(state, world, pos)) = getSubblocksFromTrace(state, world, pos, hit, subblocks).firstOrNull()

    companion object {
        private fun entityRaycast(entity: Entity, maxDistance: Double, tickDelta: Float, includeFluids: Boolean): HitResult {
            val vec3d: Vec3d = entity.getCameraPosVec(tickDelta)
            val vec3d2: Vec3d = entity.getRotationVec(tickDelta)
            val vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance)
            return entity.world.raycast(RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.VISUAL, if (includeFluids) RaycastContext.FluidHandling.ANY else RaycastContext.FluidHandling.NONE, entity))
        }

        private fun getSubblockVisualShapeInternal(subblocks: List<Subblock>): VoxelShape {
            val all = subblocks.map { it.shape }
            if (all.isEmpty()) return VoxelShapes.empty()
            return VoxelShapes.union(all.first(), *all.drop(1).toTypedArray())
        }
    }

}