package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.*

abstract class LinedComputingController : ComputingController() {

    abstract fun write(session: ComputingSession, text: Text, view: UUID? = null)

    abstract fun getOutput(session: ComputingSession, view: UUID? = null): List<Text>

    @Environment(EnvType.CLIENT)
    override fun getBackgroundColor(session: ComputingSession, view: ComputingSessionViewContext) = 0xFF161616

    @Environment(EnvType.CLIENT)
    override fun render(session: ComputingSession, view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int) {
        val textRenderer = MinecraftClient.getInstance().textRenderer
        getOutput(session, view.getViewToken()).flatMap { textRenderer.wrapLines(it, console_width) }.reversed().forEachIndexed { k, v ->
            textRenderer.draw(matrices, v, x + 10f, y + 100f - k.times(10f), 16777215)
        }
    }

    companion object {
        val font = LCC.id("fixed")
        val style = Style.EMPTY.withFont(font)

        val console_width = 228
        val console_offset = 6
        val char_width = 6
        val row_chars = Math.floor((console_width / char_width.toFloat()).toDouble()).toInt()
    }

}