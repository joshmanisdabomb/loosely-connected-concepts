package com.joshmanisdabomb.lcc.gui.utils

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import net.minecraft.client.font.TextRenderer
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

    fun renderPower(matrices: MatrixStack, power: Float, maxPower: Float, x: Int, y: Int) {
        val h = ceil(13.times(power.div(maxPower))).toInt()
        draw(matrices, x, y + 13.minus(h), offsetX, power_v + 13.minus(h), 11, h)
    }

    fun renderBurn(matrices: MatrixStack, power: Int, maxBurn: Int, x: Int, y: Int) {
        val h = ceil(13.times(power.toFloat().div(maxBurn))).toInt()
        draw(matrices, x, y + 13.minus(h), offsetX, burn_v + 13.minus(h), 14, h)
    }

    fun renderProgress(matrices: MatrixStack, progress: Int, maxProgress: Int, x: Int, y: Int) {
        val w = ceil(23.times(progress.toFloat().div(maxProgress))).toInt()
        this.draw(matrices, x, y, offsetX.plus(1), progress_v, w, 17)
    }

    fun renderAction(matrices: MatrixStack, icon: Int, efficiency: Float, maxEfficiency: Float, x: Int, y: Int) {
        this.draw(matrices, x, y, offsetX, action_v + icon.times(14), 14, 14)
        val eff = efficiency.div(maxEfficiency)
        if (eff >= 1f && ticks % 14 >= 7) {
            this.draw(matrices, x, y, offsetX.plus(28), action_v + icon.times(14), 14, 14)
        } else {
            val h2 = ceil(13.times(eff)).toInt()
            this.draw(matrices, x, y + 13.minus(h2), offsetX.plus(14), action_v + icon.times(14) + 13.minus(h2), 14, h2)
        }
    }

    fun renderPowerBar(matrices: MatrixStack, power: Float, maxPower: Float, x: Int, y: Int, w: Int) {
        val w2 = ceil(w.times(power.div(maxPower))).toInt()
        draw(matrices, x, y, 0, offsetY, w2, 10)
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
            tooltip(matrices, textRenderer.wrapLines(TranslatableText("$translationKey.output", LooseEnergy.displayWithUnits(output), LooseEnergy.displayWithUnits(outputCeil), water.div(3f).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    @JvmDefault
    private fun draw(matrices: MatrixStack, x: Int, y: Int, u: Int, v: Int, width: Int, height: Int) = (this as Screen).drawTexture(matrices, x, y, u, v, width, height)

    private fun tooltip(matrices: MatrixStack, wrapLines: List<OrderedText>, mouseX: Int, mouseY: Int) = (this as Screen).renderOrderedTooltip(matrices, wrapLines, mouseX, mouseY)

}