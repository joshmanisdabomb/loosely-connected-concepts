package com.joshmanisdabomb.lcc.gui.utils

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.MathHelper.lerp
import kotlin.math.roundToInt

interface GauntletProgressRenderer {

    val loc: Int
    val size: Int

    fun renderProgress(matrix: MatrixStack, camera: PlayerEntity, percentage: Double, reverse: Boolean, u: Int, v: Int, angle: Float, delta: Float, offset: Int = -1) {
        if (reverse) {
            renderProgress(matrix, lerp(delta.toDouble(), 1 - percentage, 1 - percentage), u, v, angle)
        } else {
            renderProgress(matrix, lerp(delta.toDouble(), percentage, percentage), u, v, angle)
        }
    }

    fun renderProgress(matrix: MatrixStack, fill: Double, u: Int, v: Int, angle: Float) {
        val quarter = angle % 180 >= 90
        if (!fill.isNaN()) {
            val fill1 = loc + (1.minus(fill).times(size).roundToInt())
            val fill2a = u.times(size).plus(1.minus(fill).times(size).roundToInt())
            val fill2b = v.times(size).plus(1.minus(fill).times(size).roundToInt())
            val fill3 = size.times(fill).roundToInt()
            this.drawTexture(matrix, if (quarter) fill1 else loc, if (!quarter) fill1 else loc, if (quarter) fill2a else u.times(size), if (!quarter) fill2b else v.times(size), if (quarter) fill3 else size, if (!quarter) fill3 else size)
        }
    }

    fun renderLine(matrix: MatrixStack, camera: PlayerEntity, percentage: Double, reverse: Boolean, u: Int, v: Int, angle: Float, delta: Float) {
        if (reverse) {
            renderLine(matrix, lerp(delta.toDouble(), 1 - percentage, 1 - percentage), u, v, angle)
        } else {
            renderLine(matrix, lerp(delta.toDouble(), percentage, percentage), u, v, angle)
        }
    }

    fun renderLine(matrix: MatrixStack, fill: Double, u: Int, v: Int, angle: Float) {
        val quarter = angle % 180 >= 90
        if (!fill.isNaN()) {
            val fill1 = loc + (1.minus(fill).times(size).roundToInt())
            val fill2a = u.times(size).plus(1.minus(fill).times(size).roundToInt())
            val fill2b = v.times(size).plus(1.minus(fill).times(size).roundToInt())
            val fill3 = 1
            this.drawTexture(matrix, if (quarter) fill1 else loc, if (!quarter) fill1 else loc, if (quarter) fill2a else u.times(size), if (!quarter) fill2b else v.times(size), if (quarter) fill3 else size, if (!quarter) fill3 else size)
        }
    }

    fun drawTexture(matrix: MatrixStack, x: Int, y: Int, u: Int, v: Int, width: Int, height: Int)

}
