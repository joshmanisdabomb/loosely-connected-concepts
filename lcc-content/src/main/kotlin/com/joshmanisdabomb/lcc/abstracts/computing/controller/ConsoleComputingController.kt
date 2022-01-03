package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.controller.LinedComputingController.Companion.total_columns
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LinedComputingController.Companion.total_rows
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.LCCConsoleCommands
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.*
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.command.argument.UuidArgumentType.getUuid
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import org.lwjgl.glfw.GLFW
import org.lwjgl.openal.ALUtil.getStringList
import java.util.*

class ConsoleComputingController : LinedComputingController() {

    override fun serverTick(session: ComputingSession, context: ComputingSessionExecuteContext) {
        val sworld = context.getWorldFromContext() as? ServerWorld ?: return
        for ((k, v) in session.getViewData()) {
            v.getCompoundList("Queue").forEach {
                val player = sworld.server.playerManager.getPlayer(it.getUuid("Player")) ?: return@forEach
                val view = it.getUuid("View")
                val source = ConsoleCommandSource(session, context, view, player)
                try {
                    LCCConsoleCommands.dispatcher.execute(it.getString("Buffer"), source)
                } catch (e: CommandSyntaxException) {
                    write(session, TranslatableText("terminal.lcc.console.unknown", e.cursor), view)
                }
            }
            v.remove("Queue")
            session.sync()
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
        val viewId = view ?: return
        session.getViewData(viewId).modifyStringList("Output") {
            this.add(Text.Serializer.toJson(text.fillStyle(style)))
            this.take(50)
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
                    session.sync()
                }
            }
            GLFW.GLFW_KEY_DOWN -> {
                if (historySeek > 1) {
                    vdata.putString("Buffer", history[historySeek-2])
                    vdata.putInt("HistorySeek", historySeek-1)
                    session.sync()
                }
            }
            GLFW.GLFW_KEY_BACKSPACE -> {
                vdata.modifyString("Buffer") { dropLast(1) }
                session.sync()
            }
            GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                val buffer = vdata.getString("Buffer")

                write(session, TranslatableText("terminal.lcc.console.buffer", buffer), viewId)
                vdata.putStringList("History", history.takeLast(49)).addString(buffer, 0)
                vdata.putString("Buffer", "")
                vdata.putInt("HistorySeek", 0)

                vdata.modifyCompoundList("Queue") {
                    val entry = NbtCompound()
                    entry.putUuid("View", viewId)
                    entry.putUuid("Player", player.uuid)
                    entry.putString("Buffer", buffer)
                    add(entry)
                    null
                }
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
        val buffer = vdata.getString("Buffer")

        vdata.putString("Buffer", buffer + char)
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
            renderLine(matrices, x, y, ty, TranslatableText("terminal.lcc.console.buffer", buffer.takeLast(total_columns - 3), (System.currentTimeMillis().rem(2000) > 1000).transform("_", "")).fillStyle(style))
        }
    }

}
