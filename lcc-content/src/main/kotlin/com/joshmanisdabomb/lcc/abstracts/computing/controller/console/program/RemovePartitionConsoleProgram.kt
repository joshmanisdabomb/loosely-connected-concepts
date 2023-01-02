package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.storage.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getBooleanOrNull
import com.joshmanisdabomb.lcc.extensions.getText
import com.joshmanisdabomb.lcc.extensions.putText
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

class RemovePartitionConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("partition", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION, preference = DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestPartitions(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            prepare(it, DiskInfoArgumentType.get(it, "partition"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val partitionId = data.getUuid("Partition")
        val partitionShort = data.getString("PartitionShort")
        val partitionLabel = data.getString("PartitionLabel")
        val diskShort = data.getString("DiskShort")
        val diskLabel = data.getText("DiskLabel")
        val disks = source.context.getAccessibleDisks()
        val disk = StorageDisk.getDiskWithPartition(disks, partitionId)
        if (disk == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.interrupt", partitionLabel, partitionShort), source.view)
            return null
        }

        if (data.getInt("Ticks") <= 0) source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.prompt", partitionLabel, partitionShort), source.view)
        return when (data.getBooleanOrNull("Prompt")) {
            true -> {
                disk.removePartition(partitionId)
                val level = source.context.getWorldFromContext().levelProperties
                val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
                storage.unlinkRootFolder(partitionId)
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.success", partitionLabel, partitionShort, diskLabel, diskShort), source.view)
                null
            }
            false -> {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.aborted"), source.view)
                null
            }
            else -> true
        }
    }

    override fun keyPressed(session: ComputingSession, data: NbtCompound, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int): Boolean {
        if (!data.contains("Prompt")) {
            return when (keyCode) {
                GLFW.GLFW_KEY_Y -> {
                    data.putBoolean("Prompt", true)
                    true
                }
                GLFW.GLFW_KEY_N -> {
                    data.putBoolean("Prompt", false)
                    true
                }
                else -> false
            }
        }
        return false
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch): Int {
        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchPartitions(StorageDisk.getPartitions(disks))
        val partition = DiskInfoArgumentType.getSinglePartition(results, search) ?: throw DiskInfoArgumentType.noPartitions.create(search)
        val partitionId = partition.getShortId(disks)
        val disk = partition.disk

        val nbt = NbtCompound()
        nbt.putUuid("Partition", partition.id)
        nbt.putString("PartitionShort", partitionId)
        nbt.putString("PartitionLabel", partition.label)
        if (disk != null) {
            val diskId = disk.getShortId(disks)
            nbt.putString("DiskShort", diskId)
            nbt.putText("DiskLabel", disk.name)
        }
        return startTask(context.source, nbt)
    }

}
