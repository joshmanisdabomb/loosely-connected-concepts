package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.partition.SystemPartitionType
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.*
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import org.lwjgl.glfw.GLFW
import java.awt.SystemColor.menu
import java.util.*

class BIOSComputingController : LinedComputingController() {

    override fun serverTick(session: ComputingSession, context: ComputingSessionExecuteContext) {
        val data = session.getSharedData()
        val sdata = session.getServerData()
        val disks = context.getAccessibleDisks()

        val checked = sdata.getList("CheckedPartitions", NBT_STRING)
        val bootable = sdata.getList("Bootable", NBT_STRING)
        val menu = data.getList("Menu", NBT_STRING)
        for (disk in disks) {
            disk.initialise()
            for (partition in disk.partitions) {
                val partitionId = partition.id?.toString() ?: continue
                if (!checked.map(NbtElement::asString).contains(partitionId)) {
                    checked.addString(partitionId)
                    sdata.put("CheckedPartitions", checked)

                    val shortId = partition.getShortId(disks)
                    val system = partition.type as? SystemPartitionType
                    if (system == null) {
                        write(session, TranslatableText("terminal.lcc.bios.pass", partition.label, shortId))
                    } else {
                        bootable.addString(partitionId)
                        sdata.put("Bootable", bootable)
                        if (menu.isEmpty()) {
                            data.putInt("Autoload", session.ticks)
                        }
                        menu.addString(Text.Serializer.toJson(TranslatableText("terminal.lcc.bios.bootable", TranslatableText(partition.type.translationKey), partition.label, shortId).fillStyle(style)))
                        data.put("Menu", menu)
                        session.sync()
                    }
                }
            }
        }
    }

    override fun write(session: ComputingSession, text: MutableText, view: UUID?) {
        val data = session.getSharedData()
        val output = data.getStringList("Output")
        val list = NbtList()

        list.addStrings(output.take(49))
        list.addString(Text.Serializer.toJson(text.fillStyle(style)))

        data.put("Output", list)
        session.sync()
    }

    override fun clear(session: ComputingSession, view: UUID?) {
        session.getSharedData().put("Output", NbtList())
        session.sync()
    }

    override fun clientTick(session: ComputingSession, context: ComputingSessionExecuteContext) = Unit

    override fun onClose(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext) = Unit

    override fun keyPressed(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) {
        val data = session.getSharedData()
        val selector = data.getInt("Selector")
        when (keyCode) {
            GLFW.GLFW_KEY_UP -> {
                if (selector > 0) {
                    data.putInt("Selector", selector-1)
                    session.sync()
                }
            }
            GLFW.GLFW_KEY_DOWN -> {
                val menu = data.getList("Menu", NBT_STRING)
                if (selector < menu.size.coerceAtMost(11).minus(1)) {
                    data.putInt("Selector", selector+1)
                    data.remove("Autoload")
                    session.sync()
                }

            }
            GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                println("enter")
            }
            else -> return
        }
    }

    override fun keyReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) = Unit

    override fun mouseClicked(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) = Unit

    override fun mouseReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) = Unit

    override fun charTyped(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, char: Char) = Unit

    @Environment(EnvType.CLIENT)
    override fun render(session: ComputingSession, view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int) {
        renderLine(matrices, x, y, 0, title)

        val data = session.getSharedData()
        val selector = data.getInt("Selector")
        val scroll = data.getInt("Scroll")
        val autoload = if (data.contains("Autoload", NBT_INT)) data.getInt("Autoload") else null
        val menu = data.getStringList("Menu")
        if (menu.isEmpty()) {
            renderOutput(readOutput(data.getStringList("Output")), matrices, x, y, 1)
        } else {
            val output = readOutput(menu).drop(scroll).take(total_rows-1)
            renderOutput(output, matrices, x, y, 1) { it }
            renderHighlight(matrices, x, y, selector+1, 0xFFFFFFFF)
            if (autoload != null) {
                renderHighlight(matrices, x, y, selector + 1, 0xFF66BBFF, x2 = (session.ticks - autoload + MinecraftClient.getInstance().tickDelta).div(100f))
            }
            renderLine(matrices, x, y, selector+1, output[selector], color = 0x202020)
        }
    }

    companion object {
        val title = TranslatableText("terminal.lcc.bios.version", "1.0").formatted(Formatting.BLUE).fillStyle(style)
    }

}