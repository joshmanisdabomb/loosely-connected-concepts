package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StoragePartition
import com.joshmanisdabomb.lcc.energy.LooseEnergy.name
import com.joshmanisdabomb.lcc.extensions.getText
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text

class LabelConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestAll(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            val nbt = NbtCompound()
            nbt.putString("Operation", "get")

            val search = DiskInfoArgumentType.get(it, "disk")
            val disks = it.source.context.getAccessibleDisks()
            val results = search.search(disks)
            val single = DiskInfoArgumentType.getSingleDiskOrPartition(results, search)
            DiskInfoArgumentType.attachResultsToNbt(disks, single, search, nbt)

            startTask(it.source, nbt)
        }.then(LCCConsolePrograms.required("label", StringArgumentType.string()).executes {
            val nbt = NbtCompound()
            nbt.putString("Operation", "set")

            val search = DiskInfoArgumentType.get(it, "disk")
            val disks = it.source.context.getAccessibleDisks()
            val results = search.search(disks)
            val single = DiskInfoArgumentType.getSingleDiskOrPartition(results, search)

            val label = StringArgumentType.getString(it, "label")
            checkLabel(label, single)
            nbt.putString("Label", label)

            DiskInfoArgumentType.attachResultsToNbt(disks, single, search, nbt)

            startTask(it.source, nbt)
        }))

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val operation = data.getString("Operation")
        val label = data.getString("Label")

        val diskId = data.getUuidOrNull("Disk")
        if (diskId != null) {
            val diskShort = data.getString("DiskShort")
            val diskLabel = data.getText("DiskLabel")
            val disk = source.context.getDisk(diskId)
            if (disk == null) {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.interrupt.disk.$operation", diskLabel, diskShort), source.view)
            } else if (operation == "set") {
                if (label.isEmpty()) {
                    source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.removed", disk.name, diskShort), source.view)
                    disk.label = null
                } else {
                    source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.success.disk", disk.name, diskShort, label), source.view)
                    disk.label = label
                }
            } else if (disk.label != null) {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.found.disk", disk.stack.name, diskShort, disk.label), source.view)
            } else {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.none.disk", disk.stack.name, diskShort), source.view)
            }
        }

        val partitionId = data.getUuidOrNull("Partition")
        if (partitionId != null) {
            val partitionShort = data.getString("PartitionShort")
            val partitionLabel = data.getString("PartitionLabel")
            val partition = source.context.getPartition(partitionId)
            if (partition == null) {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.interrupt.partition.$operation", partitionLabel, partitionShort), source.view)
            } else if (operation == "set") {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.success.partition", partition.label, partitionShort, label), source.view)
                partition.label = label
            } else {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.found.partition", partition.label, partitionShort, partition.label), source.view)
            }
        }
        return null
    }

    companion object {
        val labelRegex = Regex("[:#/]+")

        private val labelEmpty = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.empty"))
        private val diskLabelInvalid = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.invalid.disk"))
        private val partitionLabelInvalid = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.invalid.partition"))

        fun checkLabel(label: String, single: Pair<StorageDisk?, StoragePartition?>) {
            if (single.second != null) {
                if (label.isEmpty()) throw labelEmpty.create()
                if (label.matches(labelRegex)) throw partitionLabelInvalid.create()
            } else {
                if (label.matches(labelRegex)) throw diskLabelInvalid.create()
            }
        }
    }

}