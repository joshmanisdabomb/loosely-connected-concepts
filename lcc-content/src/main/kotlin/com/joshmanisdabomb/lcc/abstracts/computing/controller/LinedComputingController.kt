package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.MutableText
import net.minecraft.text.OrderedText
import net.minecraft.text.Style
import net.minecraft.text.Text
import java.util.*
import kotlin.math.floor

abstract class LinedComputingController : ComputingController() {

    abstract fun write(session: ComputingSession, text: MutableText, view: UUID? = null)

    abstract fun clear(session: ComputingSession, view: UUID? = null)

    fun readOutput(feed: List<String>) = feed.mapNotNull { Text.Serializer.fromJson(it) }

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
    fun renderOutput(output: List<Text>, matrices: MatrixStack, sx: Int, sy: Int, ty: Int = 0, color: Int = 0xBBBBBB, wrapper: (text: Text) -> List<OrderedText> = { MinecraftClient.getInstance().textRenderer.wrapLines(it, console_width) }, portion: (output: List<OrderedText>) -> List<OrderedText> = { it.takeLast(total_rows - ty) }) {
        output.flatMap(wrapper).let(portion).forEachIndexed { k, v ->
            renderLine(matrices, sx, sy, k+ty, v, color)
        }
    }

    @Environment(EnvType.CLIENT)
    fun renderHighlight(matrices: MatrixStack, sx: Int, sy: Int, ty: Int, color: Long, x1: Float = 0f, x2: Float = 1f) {
        DrawableHelper.fill(matrices, sx + console_width.times(x1).toInt(), sy + ty.times(row_height) + console_offset, sx + console_width.times(x2).toInt(), sy + ty.plus(1).times(row_height) + console_offset, color.toInt())
    }

    companion object {
        val font = LCC.id("fixed")
        val style = Style.EMPTY.withFont(font)

        val console_width = 240
        val console_height = 116
        val console_offset = 4
        val char_width = 6
        val row_height = 9
        val total_rows = console_height / row_height
        val total_columns = console_width / char_width
    }

}