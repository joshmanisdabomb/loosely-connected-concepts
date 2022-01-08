package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import net.minecraft.text.TranslatableText
import java.util.*

class LabelConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .then(LCCConsoleCommands.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).then(LCCConsoleCommands.required("label", StringArgumentType.string()).executes {
            label(it, DiskInfoArgumentType.get(it, "disk"), StringArgumentType.getString(it, "label"))
        }))

    fun label(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, label: String): Int {
        val disks = context.source.context.getAccessibleDisks()
        val (disk, partition) = DiskInfoArgumentType.getSingleDiskOrPartition(search.search(disks), search)
        when {
            partition != null && disk != null -> throw DiskInfoArgumentType.conflict.create(search)
            disk != null -> {
                context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.success.disk", disk.name, disk.getShortId(disks), label))
                //TODO empty string removes label, sanity check label for colons
                disk.label = label
            }
            partition != null -> {
                context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.success.partition", partition.label, partition.getShortId(disks), label))
                //TODO empty string not allowed, sanity check label for colons
                partition.label = label
            }
            else -> throw DiskInfoArgumentType.genericEmpty.create(search)
        }
        return 1
    }

    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.install.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.install.space_allocable", a, b, c, d) }

}