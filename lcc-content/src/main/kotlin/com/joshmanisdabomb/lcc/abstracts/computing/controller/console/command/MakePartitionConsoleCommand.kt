package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.PartitionArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.abstracts.computing.partition.SystemPartitionType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.text.TranslatableText
import java.util.*

class MakePartitionConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .then(LCCConsoleCommands.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, preference = DiskInfoArgumentType.DiskInfoArgumentResult.DISK)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestAll(context.source.context.getAccessibleDisks(), builder), builder) }
            .then(LCCConsoleCommands.required("partition", PartitionArgumentType { it !is SystemPartitionType })
                .then(LCCConsoleCommands.required("label", StringArgumentType.string()).executes {
                    mkpart(it, DiskInfoArgumentType.get(it, "disk"), PartitionArgumentType.get(it, "partition"), StringArgumentType.getString(it, "label"))
                }.then(LCCConsoleCommands.required("size", IntegerArgumentType.integer(1)).executes {
                    mkpart(it, DiskInfoArgumentType.get(it, "disk"), PartitionArgumentType.get(it, "partition"), StringArgumentType.getString(it, "label"), IntegerArgumentType.getInteger(it, "size"))
                })
            )
        ))

    fun mkpart(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, type: PartitionType, label: String, size: Int? = null): Int {
        if (label.isEmpty()) throw labelEmpty.create()
        if (label.matches(LabelConsoleCommand.labelRegex)) throw labelInvalid.create()

        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchDisks(disks)
        val disk = DiskInfoArgumentType.getSingleDisk(results, search) ?: throw DiskInfoArgumentType.noDisks.create(search)

        val total = disk.totalSpace
        val allocable = disk.allocableSpace
        val finalSize = if (size == null) {
            if (allocable <= 0) throw allocableSpaceNone.create(disk.name, disk.getShortId(disks))
            allocable
        } else {
            if (allocable < size) throw allocableSpaceLow.create(disk.name, disk.getShortId(disks), allocable, size)
            size
        }
        if (total < finalSize) throw totalSpaceLow.create(disk.name, disk.getShortId(disks), total, finalSize)

        val partition = DiskPartition(UUID.randomUUID(), label, type, finalSize)
        disk.addPartition(partition)
        context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.mkpart.success", TranslatableText(partition.type.translationKey), partition.label, partition.getShortId(disks), partition.size, disk.name, disk.getShortId(disks)))
        return 1
    }

    private val labelEmpty = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.mkpart.label_empty"))
    private val labelInvalid = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.mkpart.label_invalid"))

    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.mkpart.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.mkpart.space_allocable", a, b, c, d) }
    private val allocableSpaceNone = Dynamic2CommandExceptionType { a, b -> TranslatableText("terminal.lcc.console.mkpart.no_allocable", a, b) }

}
