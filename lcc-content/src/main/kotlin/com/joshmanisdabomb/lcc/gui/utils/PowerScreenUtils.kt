package com.joshmanisdabomb.lcc.gui.utils

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.OrderedText
import net.minecraft.text.TranslatableText
import kotlin.math.ceil

interface PowerScreenUtils {

    val offset: Int
    val ticks: Int

    val textRenderer: TextRenderer

    val powerU get() = 0
    val progressU get() = 14
    val actionU get() = 31

    fun renderPower(matrices: MatrixStack, power: Float, maxPower: Float, x: Int, y: Int) {
        val h = 13.times(power.div(maxPower)).toInt()
        drawTexture(matrices, x, y + 13.minus(h), offset, powerU + 13.minus(h), 11, h)
    }

    fun renderProgress(matrices: MatrixStack, ticks: Int, maxProgress: Int, x: Int, y: Int) {
        val w = ceil(23.times(ticks.toFloat().div(maxProgress))).toInt()
        this.drawTexture(matrices, x, y, offset.plus(1), progressU, w, 17)
    }

    fun renderAction(matrices: MatrixStack, icon: Int, efficiency: Float, maxEfficiency: Float, x: Int, y: Int) {
        this.drawTexture(matrices, x, y, offset, actionU + icon.times(14), 14, 14)
        val eff = efficiency.div(maxEfficiency)
        if (eff >= 1f && ticks % 14 >= 7) {
            this.drawTexture(matrices, x, y, offset.plus(28), actionU + icon.times(14), 14, 14)
        } else {
            val h2 = ceil(13.times(eff)).toInt()
            this.drawTexture(matrices, x, y + 13.minus(h2), offset.plus(14), actionU + icon.times(14) + 13.minus(h2), 14, h2)
        }
    }

    fun renderPowerTooltip(matrices: MatrixStack, power: Float, consumed: Float?, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        val prefix = if (consumed != null) ".recipe" else ""
        if (mouseX in x && mouseY in y) {
            renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.refining.power".plus(prefix), LooseEnergy.displayWithUnits(LooseEnergy.fromStandard(power)), LooseEnergy.displayWithUnits(consumed ?: 0f)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun renderProgressTooltip(matrices: MatrixStack, ticks: Int, maxTicks: Int, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        if (mouseX in x && mouseY in y) {
            renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.refining.time", ceil(ticks.div(20f)).toInt(), ceil(maxTicks.div(20f)).toInt()), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun renderActionTooltip(matrices: MatrixStack, efficiency: Float, maxEfficiency: Float?, mouseX: Int, mouseY: Int, x: IntRange, y: IntRange) {
        val prefix = if (maxEfficiency != null) ".recipe" else ""
        if (mouseX in x && mouseY in y) {
            renderOrderedTooltip(matrices, textRenderer.wrapLines(TranslatableText("container.lcc.refining.efficiency".plus(prefix), efficiency.plus(100).decimalFormat(force = true), (maxEfficiency ?: 0f).plus(100).decimalFormat(force = true)), Int.MAX_VALUE), mouseX, mouseY)
        }
    }

    fun drawTexture(matrices: MatrixStack, x: Int, y: Int, u: Int, v: Int, width: Int, height: Int)

    fun renderOrderedTooltip(matrices: MatrixStack, wrapLines: List<OrderedText>, mouseX: Int, mouseY: Int)

}