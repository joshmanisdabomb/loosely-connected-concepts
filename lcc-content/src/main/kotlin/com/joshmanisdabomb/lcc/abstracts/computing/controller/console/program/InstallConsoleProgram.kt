package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.joshmanisdabomb.lcc.extensions.getText
import com.joshmanisdabomb.lcc.extensions.putText
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.TranslatableText
import java.util.*

class InstallConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, preference = DiskInfoArgumentType.DiskInfoArgumentResult.DISK)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestDisks(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            prepare(it, DiskInfoArgumentType.get(it, "disk"))
        }.then(LCCConsolePrograms.required("label", StringArgumentType.string()).executes {
            prepare(it, DiskInfoArgumentType.get(it, "disk"), StringArgumentType.getString(it, "label"))
        }))

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val diskId = data.getUuid("Disk")
        val diskShort = data.getString("DiskShort")
        val diskLabel = data.getText("DiskLabel")
        val disks = source.context.getAccessibleDisks()
        val disk = DiskInfo.getDisk(disks, diskId)
        if (disk == null) {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.interrupt", diskLabel, diskShort), source.view)
            return null
        }

        val label = data.getString("Label")
        val required = LCCPartitionTypes.console.size

        val partition = DiskPartition(UUID.randomUUID(), label, LCCPartitionTypes.console, required)
        disk.addPartition(partition)
        source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.success", partition.label, partition.getShortId(disks), partition.size, diskLabel, diskShort), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, label: String = "Console OS"): Int {
        if (label.isEmpty()) throw labelEmpty.create()
        if (label.matches(LabelConsoleProgram.labelRegex)) throw labelInvalid.create()

        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchDisks(disks)
        val disk = DiskInfoArgumentType.getSingleDisk(results, search) ?: throw DiskInfoArgumentType.noDisks.create(search)
        val shortId = disk.getShortId(disks)

        val required = LCCPartitionTypes.console.size
        val total = disk.totalSpace
        if (total < required) throw totalSpaceLow.create(disk.name, shortId, total, required)
        val allocable = disk.allocableSpace
        if (allocable < required) throw allocableSpaceLow.create(disk.name, shortId, allocable, required)

        val nbt = NbtCompound()
        nbt.putUuid("Disk", disk.id)
        nbt.putString("DiskShort", shortId)
        nbt.putText("DiskLabel", disk.name)
        nbt.putString("Label", label)
        return startTask(context.source, nbt)
    }

    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.$name.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.$name.space_allocable", a, b, c, d) }

    private val labelEmpty = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.$name.label_empty"))
    private val labelInvalid = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.$name.label_invalid"))

}