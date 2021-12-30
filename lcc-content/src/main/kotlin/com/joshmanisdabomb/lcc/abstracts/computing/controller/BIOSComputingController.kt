package com.joshmanisdabomb.lcc.abstracts.computing.controller

import com.joshmanisdabomb.lcc.abstracts.computing.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.extensions.addString
import com.joshmanisdabomb.lcc.extensions.addStrings
import com.joshmanisdabomb.lcc.extensions.getStringList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtList
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import java.util.*

class BIOSComputingController : LinedComputingController() {

    override fun serverTick(session: ComputingSession, context: ComputingSessionExecuteContext) {
        if (session.ticks == 0) write(session, LiteralText("Loose BIOS v1.0"))

        val sdata = session.getServerData()
        val disks = context.getAccessibleDisks()

        val checked = sdata.getStringList("CheckedPartitions").toMutableSet()
        for (disk in disks) {
            val info = DiskInfo(disk).initialise()
            for (partition in info.partitions) {
                val partitionId = partition.id?.toString() ?: continue
                if (!checked.contains(partitionId)) {
                    checked.add(partitionId)
                    write(session, LiteralText("No boot partition found on partition :${partition.label} (#${partitionId})"))
                }
            }
        }

        val newChecked = NbtList()
        newChecked.addStrings(checked)
        sdata.put("CheckedPartitions", newChecked)
    }

    override fun write(session: ComputingSession, text: Text, view: UUID?) {
        val data = session.getSharedData()
        val output = data.getStringList("Output")
        val list = NbtList()

        val lines = text.getWithStyle(style).map { Text.Serializer.toJson(it) }
        list.addStrings(output.take(50-lines.size))
        list.addStrings(lines)

        data.put("Output", list)
        session.sync()
    }

    override fun getOutput(session: ComputingSession, view: UUID?): List<Text> {
        return session.getSharedData().getStringList("Output").mapNotNull { Text.Serializer.fromJson(it) }
    }

    override fun onClose(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext) = Unit

    override fun keyPressed(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) = Unit

    override fun keyReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int) = Unit

    override fun mouseClicked(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) = Unit

    override fun mouseReleased(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, mouseX: Double, mouseY: Double, button: Int) = Unit

    override fun charTyped(session: ComputingSession, player: ServerPlayerEntity, view: ComputingSessionViewContext, char: Char) = Unit

}