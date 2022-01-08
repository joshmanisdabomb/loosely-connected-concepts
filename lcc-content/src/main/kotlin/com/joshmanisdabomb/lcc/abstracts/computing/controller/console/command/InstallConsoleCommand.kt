package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.mojang.brigadier.context.CommandContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import java.util.*

class InstallConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .then(LCCConsoleCommands.required("disk", DiskArgumentType()).executes {
            install(it, DiskArgumentType.get(it, "disk"), TranslatableText(LCCPartitionTypes.console.translationKey))
        })

    fun install(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, text: Text): Int {
        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchDisks(disks)
        val disk = when (results.size) {
            0 -> throw DiskArgumentType.noDisks.create(search)
            1 -> results.first()
            else -> throw DiskArgumentType.multipleDisks.create(search)
        }
        //TODO check space
        val partition = DiskPartition(UUID.randomUUID(), "Console OS", LCCPartitionTypes.console, LCCPartitionTypes.console.size)
        disk.addPartition(partition)
        context.source.context.markDirty()
        context.source.controller.write(context.source.session, TranslatableText("terminal.lcc.console.argument.disk.success", partition.label, partition.getShortId(disks), partition.size, disk.stack.name, disk.getShortId(disks)))
        return 1
    }

}