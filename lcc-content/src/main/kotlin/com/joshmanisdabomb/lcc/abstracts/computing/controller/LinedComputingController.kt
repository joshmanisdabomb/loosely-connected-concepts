package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.*
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.MutableText
import net.minecraft.text.OrderedText
import net.minecraft.text.Style
import net.minecraft.text.Text
import java.util.*

abstract class LinedComputingController : ComputingController() {

    abstract fun write(session: ComputingSession, fragment: NbtCompound, view: UUID? = null)

    abstract fun writeModify(session: ComputingSession, view: UUID? = null, from: Int = 0, modify: NbtCompound.() -> NbtCompound)

    fun write(session: ComputingSession, text: MutableText, view: UUID? = null) {
        val column = NbtCompound()
        column.putTextList("Content", listOf(text.fillStyle(style)))
        val fragment = NbtCompound()
        fragment.putCompoundList("Columns", listOf(column))
        write(session, fragment, view)
    }

    fun writeRight(session: ComputingSession, text: MutableText, view: UUID? = null) {
        val column = NbtCompound()
        column.putTextList("Content", listOf(text.fillStyle(style)))
        column.putString("Alignment", "Right")
        val fragment = NbtCompound()
        fragment.putCompoundList("Columns", listOf(column))
        write(session, fragment, view)
    }

    fun writeColumns(session: ComputingSession, columns: List<MutableText>, view: UUID? = null, modify: NbtCompound.(Int) -> Unit) {
        val list = columns.mapIndexed { k, v ->
            val column = NbtCompound()
            column.putTextList("Content", listOf(v.fillStyle(style)))
            column.modify(k)
            column
        }
        val fragment = NbtCompound()
        fragment.putCompoundList("Columns", list)
        write(session, fragment, view)
    }

    abstract fun clear(session: ComputingSession, view: UUID? = null)

    @Environment(EnvType.CLIENT)
    override fun getBackgroundColor(session: ComputingSession, view: ComputingSessionViewContext) = 0xFF161616

    @Environment(EnvType.CLIENT)
    fun renderLine(matrices: MatrixStack, sx: Int, sy: Int, ty: Int, text: Text, color: Int = 0xBBBBBB, tx: Int = 0) {
        MinecraftClient.getInstance().textRenderer.draw(matrices, text, sx.toFloat() + console_offset + tx.times(char_width), sy.toFloat() + ty.times(row_height) + console_offset, color)
    }

    @Environment(EnvType.CLIENT)
    fun renderLine(matrices: MatrixStack, sx: Int, sy: Int, ty: Int, text: OrderedText, color: Int = 0xBBBBBB, tx: Int = 0) {
        MinecraftClient.getInstance().textRenderer.draw(matrices, text, sx.toFloat() + console_offset + tx.times(char_width), sy.toFloat() + ty.times(row_height) + console_offset, color)
    }

    @Environment(EnvType.CLIENT)
    fun formatText(text: List<MutableText>, width: Int? = total_columns): List<OrderedText> {
        return text.flatMap {
            MinecraftClient.getInstance().textRenderer.wrapLines(it, width?.times(char_width) ?: 9999999)
        }
    }

    @Environment(EnvType.CLIENT)
    fun formatOutput(output: List<NbtCompound>, width: Int? = total_columns): List<OrderedText> {
        return output.flatMap {
            val columns = it.getCompoundList("Columns")

            var loop = 0
            var widths: List<Int?>
            var space = 1
            var unknowns = columns.count()
            do {
                loop += 1
                widths = columns.mapIndexed { k, v ->
                    val set = v.getIntOrNull("FixedWidth")
                    if (set != null) return@mapIndexed set

                    val content = v.getTextList("Content")
                    val lines = content.flatMap {
                        MinecraftClient.getInstance().textRenderer.wrapLines(it, 999999)
                    }

                    val maxWidth = lines.maxOf { MinecraftClient.getInstance().textRenderer.getWidth(it).div(char_width) }
                    if (width == null || maxWidth < space) {
                        maxWidth
                    } else {
                        null
                    }
                }

                val remaining = (width ?: 0) - widths.filterNotNull().sum().plus(columns.size.minus(1)).coerceAtLeast(0)
                val newUnknowns = widths.count { it == null }
                if (newUnknowns > 0) {
                    space = remaining.div(newUnknowns)
                    if (loop > 2 && newUnknowns == unknowns) {
                        loop = 0
                    }
                } else {
                    loop = 0
                }

                unknowns = newUnknowns
            } while (loop in 1..20)

            val widths2 = widths.map { it ?: space }
            val fill = widths2.filterIndexed { k, v -> columns[k].getBoolean("Fill") }.sum()
            val available = (width ?: 0) - widths2.filterIndexed { k, v -> !columns[k].getBoolean("Fill") }.sum().plus(columns.size.minus(1))
            val widths3 = widths2.mapIndexed { k, v ->
                if (width == null || !columns[k].getBoolean("Fill")) return@mapIndexed v
                v.div(fill.toFloat()).times(available).toInt()
            }

            val formattedColumns = columns.mapIndexed { k, v ->
                val content = v.getTextList("Content")
                val columnWidth = widths3[k]
                val lines = content.flatMap {
                    MinecraftClient.getInstance().textRenderer.wrapLines(it, columnWidth.times(char_width))
                }

                val alignedLines = when (v.getString("Alignment").lowercase()) {
                    "right" -> lines.map {
                        val textWidth = MinecraftClient.getInstance().textRenderer.getWidth(it).div(char_width)
                        OrderedText.concat(Text.literal(" ".repeat(columnWidth.minus(textWidth))).fillStyle(style).asOrderedText(), it)
                    }
                    else -> lines.map {
                        val textWidth = MinecraftClient.getInstance().textRenderer.getWidth(it).div(char_width)
                        OrderedText.concat(it, Text.literal(" ".repeat(columnWidth.minus(textWidth))).fillStyle(style).asOrderedText())
                    }
                }

                alignedLines.ifEmpty { listOf(OrderedText.EMPTY) }
            }

            val lineCount = formattedColumns.maxOf { it.size }
            val expandedColumns = formattedColumns.map {
                List(lineCount) { i -> it.getOrElse(i) { OrderedText.EMPTY } }
            }

            expandedColumns.reduce { a, b ->
                a.mapIndexed { k, v ->
                    OrderedText.concat(v, Text.literal(" ").fillStyle(style).asOrderedText(), b[k])
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    fun limitLines(lines: List<OrderedText>, height: Int = total_rows, scroll: Int = 0) = lines.dropLast(scroll.coerceIn(0, lines.count().minus(height).coerceAtLeast(0))).takeLast(height)

    @Environment(EnvType.CLIENT)
    fun renderLines(lines: List<OrderedText>, matrices: MatrixStack, sx: Int, sy: Int, ty: Int = 0, color: Int = 0xBBBBBB): Int {
        lines.forEachIndexed { k, v ->
            renderLine(matrices, sx, sy, k+ty, v, color)
        }
        return lines.size
    }

    @Environment(EnvType.CLIENT)
    fun renderHighlight(matrices: MatrixStack, sx: Int, sy: Int, ty: Int, color: Long, x1: Float = 0f, x2: Float = 1f) {
        DrawableHelper.fill(matrices, sx + console_width.times(x1.coerceIn(0f, 1f)).toInt(), sy + ty.times(row_height) + console_offset, sx + console_width.times(x2.coerceIn(0f, 1f)).toInt(), sy + ty.plus(1).times(row_height) + console_offset, color.toInt())
    }

    @Environment(EnvType.CLIENT)
    fun renderHighlight(matrices: MatrixStack, sx: Int, sy: Int, ty: Int, color: Long, x1: Int, x2: Int) {
        val x1f = if (x1 <= 0) 0f else if (x1 >= total_columns) 1f else char_width.times(x1).plus(console_offset).toFloat().div(console_width)
        val x2f = if (x2 <= 0) 0f else if (x2 >= total_columns) 1f else char_width.times(x2).plus(console_offset).toFloat().div(console_width)
        renderHighlight(matrices, sx, sy, ty, color, x1f, x2f)
    }

    companion object {
        val font = LCC.id("fixed")
        val style = Style.EMPTY.withFont(font)

        val console_width = 240
        val console_height = 116
        val console_offset = 4
        val char_width = 6
        val row_height = 9
        val total_rows = console_height.minus(console_offset).div(row_height)
        val total_columns = console_width.minus(console_offset).div(char_width)
    }

}