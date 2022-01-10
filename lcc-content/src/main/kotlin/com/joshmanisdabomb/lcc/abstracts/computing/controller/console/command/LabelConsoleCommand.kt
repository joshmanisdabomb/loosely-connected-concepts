package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.text.TranslatableText

class LabelConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .then(LCCConsoleCommands.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestAll(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            get(it, DiskInfoArgumentType.get(it, "disk"))
        }.then(LCCConsoleCommands.required("label", StringArgumentType.string()).executes {
            set(it, DiskInfoArgumentType.get(it, "disk"), StringArgumentType.getString(it, "label"))
        }))

    fun get(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch): Int {
        val disks = context.source.context.getAccessibleDisks()
        val (disk, partition) = DiskInfoArgumentType.getSingleDiskOrPartition(search.search(disks), search)
        when {
            partition != null && disk != null -> throw DiskInfoArgumentType.conflict.create(search)
            disk != null -> {
                if (disk.label != null) {
                    context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.found.disk", disk.stack.name, disk.getShortId(disks), disk.label))
                } else {
                    context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.none.disk", disk.stack.name, disk.getShortId(disks)))
                }
            }
            partition != null -> {
                context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.found.partition", partition.getShortId(disks), partition.label))
            }
            else -> throw DiskInfoArgumentType.genericEmpty.create(search)
        }
        return 1
    }

    fun set(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, label: String): Int {
        val disks = context.source.context.getAccessibleDisks()
        val (disk, partition) = DiskInfoArgumentType.getSingleDiskOrPartition(search.search(disks), search)
        when {
            partition != null && disk != null -> throw DiskInfoArgumentType.conflict.create(search)
            disk != null -> {
                if (label.matches(labelRegex)) throw diskLabelInvalid.create()
                if (label.isEmpty()) {
                    context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.removed", disk.name, disk.getShortId(disks)))
                    disk.label = null
                } else {
                    context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.success.disk", disk.name, disk.getShortId(disks), label))
                    disk.label = label
                }
            }
            partition != null -> {
                if (label.isEmpty()) throw labelEmpty.create()
                if (label.matches(labelRegex)) throw partitionLabelInvalid.create()
                context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.label.success.partition", partition.label, partition.getShortId(disks), label))
                partition.label = label
            }
            else -> throw DiskInfoArgumentType.genericEmpty.create(search)
        }
        return 1
    }

    private val labelEmpty = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.label.empty"))
    private val diskLabelInvalid = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.label.invalid.disk"))
    private val partitionLabelInvalid = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.label.invalid.partition"))

    companion object {
        val labelRegex = Regex("[:#/]+")
    }

}