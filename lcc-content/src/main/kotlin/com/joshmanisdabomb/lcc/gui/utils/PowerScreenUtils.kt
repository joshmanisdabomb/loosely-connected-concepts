package com.joshmanisdabomb.lcc.gui.utils

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.OrderedText
import net.minecraft.text.TranslatableText
import kotlin.math.ceil

interface PowerScreenUtils {

    val offsetX: Int
    val offsetY: Int
    val ticks: Int

    val textRenderer: TextRenderer

    val power_v get() = 0
    val burn_v get() = 0
    val progress_v get() = 14
    val action_v get() = 31

    val translationKey: String

    fun renderBarX(matrices: MatrixStack, value: Float, max: Float, x: Int, y: Int, u: Int, v: Int, w: Int, h: Int) {
        val w2 = ceil(w.times(value.div(max))).toInt()
        draw(matrices, x, y, u, v, w2, h)
    }

    fun renderBarY(matrices: MatrixStack, value: Float, max: Float, x: Int, y: Int, u: Int, v: Int, w: Int, h: Int) {
        val h2 = ceil(h.times(value.div(max))).toInt()
        draw(matrices, x, y + h.minus(h2), u, v + h.minus(h2), w, h2)
    }

    fun renderPower(matrices: MatrixStack, power: Float, maxPower: Float, x: Int, y: Int) {
        renderBarY(matrices, power, maxPower, x, y, offsetX, power_v, 11, 13)
    }

    fun renderBurn(matrices: MatrixStack, burn: Int, maxBurn: Int, x: Int, y: Int) {
        renderBarY(matrices, burn.toFloat(), maxBurn.toFloat(), x, y, offsetX, burn_v, 14, 14)
    }

    fun renderArrow(matrices: MatrixStack, progress: Int, maxProgress: Int, x: Int, y: Int) {
        renderBarX(matrices, progress.toFloat(), maxProgress.toFloat(), x, y, offsetX + 1, progress_v, 23, 17)
    }

    fun renderAction(matrices: MatrixStack, icon: Int, efficiency: Float, maxEfficiency: Float, x: Int, y: Int) {
        this.draw(matrices, x, y, offsetX, action_v + icon.times(14), 14, 14)
        val eff = efficiency.div(maxEfficiency)
        if (eff >= 1f && ticks % 14 >= 7) {
            this.draw(matrices, x, y, offsetX.plus(28), action_v + icon.times(14), 14, 14)
        } else {
            renderBarY(matrices, efficiency, maxEfficiency, x, y, offsetX.plus(14), action_v + icon.times(14), 14, 13)
        }
    }

    fun renderPowerBar(matrices: MatrixStack, power: Float, maxPower: Float, x: Int, y: Int, w: Int) {
        renderBarX(matrices, power, maxPower, x, y, 0, offsetY, w, 10)
    }

    fun renderPowerTooltip(matrices: MatrixStack, power: Float, consumed: Float?, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        val prefix = if (consumed != null) ".recipe" else ""
        if (mouseX in x && mouseY in y) {
            tooltip(matrices, textRenderer.wrapLines(TranslatableText("$translationKey.power".plus(prefix), LooseEnergy.displayWithUnits(LooseEnergy.fromStandard(power)), LooseEnergy.displayWithUnits(consumed ?: 0f)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun renderProgressTooltip(matrices: MatrixStack, progress: Int, maxProgress: Int, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        if (mouseX in x && mouseY in y) {
            tooltip(matrices, textRenderer.wrapLines(TranslatableText("$translationKey.time", ceil(progress.div(20f)).toInt(), ceil(maxProgress.div(20f)).toInt()), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun renderActionTooltip(matrices: MatrixStack, efficiency: Float, maxEfficiency: Float?, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        val prefix = if (maxEfficiency != null) ".recipe" else ""
        if (mouseX in x && mouseY in y) {
            tooltip(matrices, textRenderer.wrapLines(TranslatableText("$translationKey.efficiency".plus(prefix), efficiency.plus(100).decimalFormat(force = true), (maxEfficiency ?: 0f).plus(100).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun renderBurnTooltip(matrices: MatrixStack, progress: Int, maxProgress: Int, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        if (mouseX in x && mouseY in y) {
            tooltip(matrices, textRenderer.wrapLines(TranslatableText("$translationKey.burn", ceil(progress.div(20f)).toInt(), ceil(maxProgress.div(20f)).toInt()), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun renderSteamTooltip(matrices: MatrixStack, output: Float, outputCeil: Float, water: Int, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        if (mouseX in x && mouseY in y) {
            tooltip(matrices, textRenderer.wrapLines(TranslatableText("$translationKey.output", LooseEnergy.displayWithUnits(output), LooseEnergy.displayWithUnits(outputCeil), water.div(3f).times(100f).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    @JvmDefault
    private fun draw(matrices: MatrixStack, x: Int, y: Int, u: Int, v: Int, width: Int, height: Int) = (this as DrawableHelper).drawTexture(matrices, x, y, u, v, width, height)

    private fun tooltip(matrices: MatrixStack, wrapLines: List<OrderedText>, mouseX: Int, mouseY: Int) = (this as Screen).renderOrderedTooltip(matrices, wrapLines, mouseX, mouseY)

}