package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.controller.LinedComputingController.Companion.console_offset
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command.LCCConsoleCommands
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.*
import com.mojang.brigadier.ParseResults
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.LiteralText
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.lwjgl.glfw.GLFW
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.max

class ConsoleComputingController : LinedComputingController() {

    private val resultsCache = mutableMapOf<ConsoleCommandSource, MutableMap<String, ParseResults<ConsoleCommandSource>>>()

    override fun serverTick(session: ComputingSession, context: ComputingSessionExecuteContext) {
        val sworld = context.getWorldFromContext() as? ServerWorld ?: return
        for ((k, v) in session.getViewData()) {
            //Cache parse results per command source per buffer.
            val buffer = v.getString("Buffer")
            val watching = context.getWatching(sworld.server, k)
            //Calculate command parsing of buffers and generate suggestions.
            watching.forEach {
                val source = ConsoleCommandSource(session, context, k, it)
                val results = useParseCache(buffer, source)
                val future = LCCConsoleCommands.dispatcher.getCompletionSuggestions(results)
                future.thenRun {
                    if (!future.isDone) return@thenRun
                    v.putCompoundList("Suggestions", future.join().list.map {
                        val nbt = NbtCompound()
                        nbt.putString("Text", it.text)
                        nbt.putString("Buffer", it.apply(buffer))
                        nbt
                    })
                }
            }
            //Process queue of commands from each terminal.
            v.getCompoundList("Queue").forEach {
                val player = sworld.server.playerManager.getPlayer(it.getUuid("Player")) ?: return@forEach
                val source = ConsoleCommandSource(session, context, k, player)
                val next = it.getString("Buffer")
                try {
                    LCCConsoleCommands.dispatcher.execute(useParseCache(next, source))
                } catch (e: CommandSyntaxException) {
                    val text = e.rawMessage as? TranslatableText
                    if (text != null) {
                        write(session, when (text.key) {
                            "command.unknown.command" -> TranslatableText("terminal.lcc.console.error.command", e.cursor)
                            "command.unknown.argument" -> TranslatableText("terminal.lcc.console.error.argument", e.cursor)
                            else -> { text }
                        }, k)
                    } else {
                        write(session, TranslatableText("terminal.lcc.console.exception.syntax"), k)
                        e.printStackTrace()
                    }
                } catch (e: IllegalArgumentException) {
                    write(session, TranslatableText("terminal.lcc.console.exception.argument"), k)
                    e.printStackTrace()
                }
            }
            v.remove("Queue")
            session.sync()
        }
        val disks = context.getAccessibleDisks()
        for (disk in disks) {
            disk.initialise()
        }
    }

    override fun serverTickView(session: ComputingSession, context: ComputingSessionViewContext) {
        val viewId = context.getViewToken()!!
        session.getViewData(viewId).modifyStringList("Loaded") {
            val uuid = viewId.toString()
            if (!this.contains(uuid)) {
                add(uuid)
                write(session, TranslatableText("terminal.lcc.console.version", "1.0"), viewId)
                session.sync()
            }
            null
        }
    }

    override fun write(session: ComputingSession, text: MutableText, view: UUID?) {
        var map = session.getViewData()
        if (view != null) {
            map = mapOf(view to session.getViewData(view))
        }
        map.forEach { (k, v) ->
            v.modifyStringList("Output") {
                this.add(Text.Serializer.toJson(text.fillStyle(style)))
                this.takeLast(50)
            }
        }
        session.sync()
    }

    override fun clear(session: ComputingSession, view: UUID?) {
        val viewId = view ?: return
        session.getViewData(viewId).put("Output", NbtList())
        session.sync()
    }

    override fun clientTick(session: ComputingSession, context: ComputingSessionExecuteContext) = Unit

    override fun onOpen(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext) = Unit

    override fun onClose(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext) = Unit

    override fun keyPressed(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) {
        val viewId = view.getViewToken()!!
        val vdata = session.getViewData(viewId)
        val history = vdata.getStringList("History").distinct()
        val historySeek = vdata.getInt("HistorySeek")
        when (keyCode) {
            GLFW.GLFW_KEY_UP -> {
                if (historySeek < history.size) {
                    vdata.putString("Buffer", history[historySeek])
                    vdata.putInt("HistorySeek", historySeek+1)
                    vdata.remove("Suggestions")
                    vdata.remove("SuggestionCycle")
                    vdata.remove("SuggestionCyclePosition")
                    session.sync()
                }
            }
            GLFW.GLFW_KEY_DOWN -> {
                if (historySeek > 1) {
                    vdata.putString("Buffer", history[historySeek-2])
                    vdata.putInt("HistorySeek", historySeek-1)
                    vdata.remove("Suggestions")
                    vdata.remove("SuggestionCycle")
                    vdata.remove("SuggestionCyclePosition")
                    session.sync()
                }
            }
            GLFW.GLFW_KEY_BACKSPACE -> {
                vdata.modifyString("Buffer") { dropLast(1) }
                vdata.remove("Suggestions")
                vdata.remove("SuggestionCycle")
                vdata.remove("SuggestionCyclePosition")
                session.sync()
            }
            GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                val buffer = vdata.getString("Buffer")

                write(session, TranslatableText("terminal.lcc.console.buffer", buffer.takeLast(total_columns - 3)), viewId)
                vdata.putStringList("History", history.takeLast(49)).addString(buffer, 0)
                vdata.putString("Buffer", "")
                vdata.putInt("HistorySeek", 0)
                vdata.remove("Suggestions")
                vdata.remove("SuggestionCycle")
                vdata.remove("SuggestionCyclePosition")

                vdata.modifyCompoundList("Queue") {
                    val entry = NbtCompound()
                    entry.putUuid("Player", player.uuid)
                    entry.putString("Buffer", buffer)
                    add(entry)
                    null
                }
            }
            GLFW.GLFW_KEY_TAB -> {
                if (vdata.contains("SuggestionCycle", NBT_LIST)) {
                    val suggestions = vdata.getCompoundList("SuggestionCycle")
                    val newPos = vdata.modifyInt("SuggestionCyclePosition") { this.plus(1).rem(suggestions.size) }
                    vdata.putString("Buffer", suggestions[newPos].getString("Buffer"))
                } else {
                    val suggestions = vdata.getCompoundList("Suggestions")
                    if (suggestions.isNotEmpty()) {
                        vdata.putCompoundList("SuggestionCycle", suggestions)
                        vdata.putInt("SuggestionCyclePosition", 0)
                        vdata.putString("Buffer", suggestions.first().getString("Buffer"))
                    }
                }
                session.sync()
            }
            else -> return
        }
    }

    override fun keyReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) = Unit

    override fun mouseClicked(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) = Unit

    override fun mouseReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) = Unit

    override fun charTyped(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, char: Char) {
        val viewId = view.getViewToken()!!
        val vdata = session.getViewData(viewId)
        vdata.modifyString("Buffer") { this + char }

        vdata.remove("Suggestions")
        vdata.remove("SuggestionCycle")
        vdata.remove("SuggestionCyclePosition")
        session.sync()
    }

    @Environment(EnvType.CLIENT)
    override fun render(session: ComputingSession, view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int) {
        val viewId = view.getViewToken()!!
        val vdata = session.getViewData(viewId)
        val output = vdata.getStringList("Output")
        val buffer = vdata.getString("Buffer")
        val queue = vdata.getList("Queue", NBT_COMPOUND)

        val ty = renderOutput(readOutput(output), matrices, x, y) { it.takeLast(total_rows - 1) }
        if (queue.isEmpty()) {
            val slice = buffer.takeLast(total_columns - 3)
            renderLine(matrices, x, y, ty, TranslatableText("terminal.lcc.console.buffer", slice, (System.currentTimeMillis().rem(2000) > 1000).transform("_", "")).fillStyle(style))

            val suggestions = vdata.getCompoundList("SuggestionCycle")
            if (suggestions.isNotEmpty()) {
                val pos = vdata.getInt("SuggestionCyclePosition")
                val pos2 = pos.minus(1).plus(suggestions.size).rem(suggestions.size)
                val pos3 = pos.plus(1).plus(suggestions.size).rem(suggestions.size)
                val text = suggestions[pos].getString("Text")

                val suggestionX = slice.length.plus(2).minus(text.length)
                var text2 = if (pos2 != pos) suggestions[pos2].getString("Text") else null
                var text3 = if (pos3 != pos && pos3 != pos2) suggestions[pos3].getString("Text") else null
                val count = 1.plus((text2 != null).transformInt()).plus((text3 != null).transformInt())
                val suggestionY = when (count) {
                    3 -> if (ty.plus(3) < total_rows) ty.plus(2) else ty.minus(2)
                    2 -> {
                        if (ty.plus(2) < total_rows) {
                            text3 = text2
                            text2 = null
                            ty.plus(1)
                        } else {
                            ty.minus(1)
                        }
                    }
                    else -> if (ty.plus(1) < total_rows) ty.plus(1) else ty.minus(1)
                }
                val suggestionW = max(max(text.length, text2?.length ?: 0), text3?.length ?: 0)

                renderHighlight(matrices, x, y, suggestionY, 0xFF333333, suggestionX.minus(1), suggestionX.plus(suggestionW).plus(1))
                renderLine(matrices, x + char_width.times(suggestionX), y, suggestionY, LiteralText(text).fillStyle(style), 0xFFFFFF)
                if (text2 != null) {
                    renderHighlight(matrices, x, y, suggestionY-1, 0xFF333333, suggestionX.minus(1), suggestionX.plus(suggestionW).plus(1))
                    renderLine(matrices, x + char_width.times(suggestionX), y, suggestionY-1, LiteralText(text2).fillStyle(style), 0x000000)
                }
                if (text3 != null) {
                    renderHighlight(matrices, x, y, suggestionY+1, 0xFF333333, suggestionX.minus(1), suggestionX.plus(suggestionW).plus(1))
                    renderLine(matrices, x + char_width.times(suggestionX), y, suggestionY+1, LiteralText(text3).fillStyle(style), 0x777777)
                }
            }
        }
    }

    private fun useParseCache(buffer: String, source: ConsoleCommandSource): ParseResults<ConsoleCommandSource> {
        return resultsCache.getOrPut(source) { mutableMapOf() }.getOrPut(buffer) { LCCConsoleCommands.dispatcher.parse(buffer, source) }
    }

}
