package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.storage.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.partition.SystemPartitionType
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text

class UseConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            val nbt = NbtCompound()
            nbt.putString("Operation", "get")

            startTask(it.source, nbt)
        }.then(LCCConsolePrograms.required("partition", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION, preference = DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestPartitions(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            prepare(it, DiskInfoArgumentType.get(it, "partition"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val disks = source.context.getAccessibleDisks()

        if (data.getString("Operation") == "get") {
            val using = source.session.getViewData(source.view).getUuidOrNull("Use")
            if (using != null) {
                val partition = StorageDisk.findPartition(disks, using)
                if (partition != null) {
                    val disk = partition.disk!!
                    source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.get", partition.label, partition.getShortId(disks), disk.name, disk.getShortId(disks)), source.view)
                    return null
                }
            }
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.get.empty"), source.view)
            return null
        }

        val partitionId = data.getUuid("Partition")
        val partitionShort = data.getString("PartitionShort")
        val partitionLabel = data.getString("PartitionLabel")
        val diskShort = data.getString("DiskShort")
        val diskLabel = data.getString("DiskLabel")
        val partition = StorageDisk.findPartition(disks, partitionId)
        if (partition == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.interrupt", partitionLabel, partitionShort), source.view)
            return null
        }

        source.session.getViewData(source.view).putUuid("Use", partitionId)
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.success", partitionLabel, partitionShort, diskLabel, diskShort), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch): Int {
        val nbt = NbtCompound()
        nbt.putString("Operation", "set")

        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchPartitions(StorageDisk.getPartitions(disks))
        val partition = DiskInfoArgumentType.getSinglePartition(results, search) ?: throw DiskInfoArgumentType.noPartitions.create(search)
        val shortId = partition.getShortId(disks)

        if (partition.type is SystemPartitionType) throw systemPartition.create(partition.label, shortId)

        val disk = partition.disk ?: throw DiskInfoArgumentType.noPartitions.create(search)
        val diskShortId = disk.getShortId(disks)

        nbt.putUuid("Partition", partition.id)
        nbt.putString("PartitionShort", shortId)
        nbt.putString("PartitionLabel", partition.label)
        nbt.putUuid("Disk", disk.id)
        nbt.putString("DiskShort", diskShortId)
        nbt.putString("DiskLabel", disk.label)

        return startTask(context.source, nbt)
    }

    private val systemPartition = Dynamic2CommandExceptionType { a, b -> Text.translatable("terminal.lcc.console.$name.system", a, b) }

}