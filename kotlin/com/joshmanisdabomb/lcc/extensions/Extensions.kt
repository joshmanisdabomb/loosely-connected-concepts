package com.joshmanisdabomb.lcc.extensions

import net.minecraft.block.Block
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f
import net.minecraft.util.registry.Registry

fun Boolean.toInt(t: Int = 1, f: Int = 0) = if (this) t else f

fun Item.stack(count: Int = 1) = ItemStack(this, count)

val Block.identifier get() = Registry.BLOCK.getId(this)

val Item.identifier get() = Registry.ITEM.getId(this)

fun Entity.replaceVelocity(x: Double? = null, y: Double? = null, z: Double? = null) {
    val v = this.velocity
    this.setVelocity(x ?: v.x, y ?: v.y, z ?: v.z)
}

fun Entity.replacePosition(x: Double? = null, y: Double? = null, z: Double? = null) {
    val p = this.pos
    this.updatePosition(x ?: p.x, y ?: p.y, z ?: p.z)
}

val Direction.perpendiculars get() = perps[this]!!

val Direction.isHorizontal get() = this.horizontal != -1

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

const val NBT_BYTE = 1
const val NBT_SHORT = 2
const val NBT_INT = 3
const val NBT_LONG = 4
const val NBT_FLOAT = 5
const val NBT_DOUBLE = 6
const val NBT_COMPOUND = 10
const val NBT_NUMERIC = 99

private val perps = mapOf(
    Direction.UP to listOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST),
    Direction.DOWN to listOf(Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST),
    Direction.NORTH to listOf(Direction.UP, Direction.WEST, Direction.DOWN, Direction.EAST),
    Direction.EAST to listOf(Direction.UP, Direction.NORTH, Direction.DOWN, Direction.SOUTH),
    Direction.SOUTH to listOf(Direction.UP, Direction.EAST, Direction.DOWN, Direction.WEST),
    Direction.WEST to listOf(Direction.UP, Direction.SOUTH, Direction.DOWN, Direction.NORTH)
)

fun ScreenHandler.addPlayerSlots(inventory: PlayerInventory, x: Int, y: Int, adder: (slot: Slot) -> Unit) {
    addSlots(inventory, x, y, 9, 3, adder, start = 9)
    addSlots(inventory, x, y + 58, 9, 1, adder, start = 0)
}

fun ScreenHandler.addSlots(inventory: Inventory, x: Int, y: Int, w: Int, h: Int, adder: (slot: Slot) -> Unit, start: Int = 0) {
    for (i in 0 until w) {
        for (j in 0 until h) {
            adder(Slot(inventory, start.plus(i).plus(j.times(w)), x + i.times(18), y + j.times(18)))
        }
    }
}