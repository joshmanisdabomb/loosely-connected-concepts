package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command.LCCConsoleCommands.label
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.text.TranslatableText
import java.util.*

class InstallConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .then(LCCConsoleCommands.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, preference = DiskInfoArgumentType.DiskInfoArgumentResult.DISK)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestDisks(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            install(it, DiskInfoArgumentType.get(it, "disk"))
        }.then(LCCConsoleCommands.required("label", StringArgumentType.string()).executes {
            install(it, DiskInfoArgumentType.get(it, "disk"), StringArgumentType.getString(it, "label"))
        }))

    fun install(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, label: String = "Console OS"): Int {
        if (label.isEmpty()) throw labelEmpty.create()
        if (label.matches(LabelConsoleCommand.labelRegex)) throw labelInvalid.create()

        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchDisks(disks)
        val disk = DiskInfoArgumentType.getSingleDisk(results, search) ?: throw DiskInfoArgumentType.noDisks.create(search)

        val required = LCCPartitionTypes.console.size
        val total = disk.totalSpace
        if (total < required) throw totalSpaceLow.create(disk.name, disk.getShortId(disks), total, required)
        val allocable = disk.allocableSpace
        if (allocable < required) throw allocableSpaceLow.create(disk.name, disk.getShortId(disks), allocable, required)

        val partition = DiskPartition(UUID.randomUUID(), label, LCCPartitionTypes.console, required)
        disk.addPartition(partition)
        context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.install.success", partition.label, partition.getShortId(disks), partition.size, disk.name, disk.getShortId(disks)))
        return 1
    }

    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.install.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.install.space_allocable", a, b, c, d) }

    private val labelEmpty = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.install.label_empty"))
    private val labelInvalid = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.install.label_invalid"))

}