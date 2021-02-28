package com.joshmanisdabomb.lcc.extensions

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f

private val perps = mapOf(
    Direction.UP to listOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST),
    Direction.DOWN to listOf(Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST),
    Direction.NORTH to listOf(Direction.UP, Direction.WEST, Direction.DOWN, Direction.EAST),
    Direction.EAST to listOf(Direction.UP, Direction.NORTH, Direction.DOWN, Direction.SOUTH),
    Direction.SOUTH to listOf(Direction.UP, Direction.EAST, Direction.DOWN, Direction.WEST),
    Direction.WEST to listOf(Direction.UP, Direction.SOUTH, Direction.DOWN, Direction.NORTH)
)

private val properties = mapOf(
    Direction.UP to Properties.UP,
    Direction.DOWN to Properties.DOWN,
    Direction.NORTH to Properties.NORTH,
    Direction.EAST to Properties.EAST,
    Direction.SOUTH to Properties.SOUTH,
    Direction.WEST to Properties.WEST
)

val Direction.perpendiculars get() = perps[this]!!

val Direction.booleanProperty get() = properties[this]!!

val Direction.isHorizontal get() = this.horizontal != -1

val horizontalDirections = arrayOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)

fun Direction.blockEntityTransform(matrices: MatrixStack) {
    when (this) {
        Direction.DOWN -> {
            matrices.translate(0.0, 1.0, 1.0);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0f))
        }
        Direction.NORTH -> {
            matrices.translate(1.0, 1.0, 1.0);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f))
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0f))
        }
        Direction.EAST -> {
            matrices.translate(0.0, 1.0, 1.0);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f))
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90.0f))
        }
        Direction.SOUTH -> {
            matrices.translate(0.0, 1.0, 0.0);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f))
        }
        Direction.WEST -> {
            matrices.translate(1.0, 1.0, 0.0);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f))
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f))
        }
    }
}