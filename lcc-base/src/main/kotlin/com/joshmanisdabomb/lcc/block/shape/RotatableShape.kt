package com.joshmanisdabomb.lcc.block.shape

import net.minecraft.util.BlockRotation
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import kotlin.math.max
import kotlin.math.min

class RotatableShape(original: VoxelShape, x: BlockRotation, y: BlockRotation, z: BlockRotation) {

    constructor(original: VoxelShape, default: Direction = Direction.UP) : this(original, when (default) {
        Direction.UP -> BlockRotation.CLOCKWISE_180
        Direction.DOWN -> BlockRotation.NONE
        else -> BlockRotation.COUNTERCLOCKWISE_90
    },
    when (default) {
        Direction.EAST -> BlockRotation.CLOCKWISE_90
        Direction.SOUTH -> BlockRotation.CLOCKWISE_180
        Direction.WEST -> BlockRotation.COUNTERCLOCKWISE_90
        else -> BlockRotation.NONE
    }, BlockRotation.NONE)

    private val bakedShapes = arrayOfNulls<VoxelShape>(64).apply { this[0] = calculate(x, y, z, original) }

    val original get() = this[BlockRotation.NONE, BlockRotation.NONE, BlockRotation.NONE]

    val up get() = this[Direction.UP]
    val down get() = this[Direction.DOWN]
    val north get() = this[Direction.NORTH]
    val east get() = this[Direction.EAST]
    val south get() = this[Direction.SOUTH]
    val west get() = this[Direction.WEST]

    operator fun get(x: BlockRotation, y: BlockRotation, z: BlockRotation): VoxelShape {
        val key: Int = x.ordinal + y.ordinal.times(4) + z.ordinal.times(16)
        bakedShapes[key] = bakedShapes[key] ?: calculate(x, y, z)
        return bakedShapes[key]!!
    }

    operator fun get(direction: Direction) = when (direction) {
        Direction.UP -> get(BlockRotation.NONE, BlockRotation.NONE, BlockRotation.NONE)
        Direction.DOWN -> get(BlockRotation.NONE, BlockRotation.CLOCKWISE_180, BlockRotation.NONE)
        Direction.NORTH -> get(BlockRotation.CLOCKWISE_90, BlockRotation.NONE, BlockRotation.NONE)
        Direction.EAST -> get(BlockRotation.CLOCKWISE_90, BlockRotation.COUNTERCLOCKWISE_90, BlockRotation.NONE)
        Direction.SOUTH -> get(BlockRotation.CLOCKWISE_90, BlockRotation.CLOCKWISE_180, BlockRotation.NONE)
        Direction.WEST -> get(BlockRotation.CLOCKWISE_90, BlockRotation.CLOCKWISE_90, BlockRotation.NONE)
    }

    private fun calculate(x: BlockRotation, y: BlockRotation, z: BlockRotation, shape: VoxelShape = original): VoxelShape {
        var ret = VoxelShapes.empty()
        for (box in shape.boundingBoxes) {
            var minX = box.minX
            var minY = box.minY
            var minZ = box.minZ
            var maxX = box.maxX
            var maxY = box.maxY
            var maxZ = box.maxZ

            //x
            var a = minY
            var b = minZ
            var c = maxY
            var d = maxZ
            minY = rotate1(a, b, x)
            minZ = rotate2(a, b, x)
            maxY = rotate1(c, d, x)
            maxZ = rotate2(c, d, x)
            //y
            a = minX
            b = minZ
            c = maxX
            d = maxZ
            minX = rotate1(a, b, y)
            minZ = rotate2(a, b, y)
            maxX = rotate1(c, d, y)
            maxZ = rotate2(c, d, y)
            //z
            a = minX
            b = minY
            c = maxX
            d = maxY
            minX = rotate1(a, b, z)
            minY = rotate2(a, b, z)
            maxX = rotate1(c, d, z)
            maxY = rotate2(c, d, z)

            ret = VoxelShapes.union(ret, VoxelShapes.cuboid(min(minX, maxX), min(minY, maxY), min(minZ, maxZ), max(minX, maxX), max(minY, maxY), max(minZ, maxZ)))
        }
        return ret
    }

    companion object {
        private fun rotate1(a: Double, b: Double, r: BlockRotation, center: Double = 0.5): Double {
            val angle = r.ordinal * 0.5f * Math.PI.toFloat()
            return MathHelper.cos(angle) * (a - 0.5) - MathHelper.sin(angle) * (b - 0.5) + center
        }

        private fun rotate2(a: Double, b: Double, r: BlockRotation, center: Double = 0.5): Double {
            val angle = r.ordinal * 0.5f * Math.PI.toFloat()
            return MathHelper.sin(angle) * (a - 0.5) - MathHelper.cos(angle) * (b - 0.5) + center
        }

        val VoxelShape.rotatable get() = RotatableShape(this)
        fun VoxelShape.rotatable(direction: Direction) = RotatableShape(this, direction)
        fun VoxelShape.rotatable(x: BlockRotation, y: BlockRotation, z: BlockRotation) = RotatableShape(this, x, y, z)
    }

}