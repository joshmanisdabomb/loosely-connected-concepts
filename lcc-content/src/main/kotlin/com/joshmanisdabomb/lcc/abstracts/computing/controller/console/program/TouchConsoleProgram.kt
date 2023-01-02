package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageFile
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.partition.TextPartitionType
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import java.util.*

class TouchConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("name", StringArgumentType.string()).executes {
            prepare(it, StringArgumentType.getString(it, "name"), "")
        }.then(LCCConsolePrograms.required("contents", StringArgumentType.greedyString()).executes {
            prepare(it, StringArgumentType.getString(it, "name"), StringArgumentType.getString(it, "contents"))
        }))

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val disks = source.context.getAccessibleDisks()

        val using = source.session.getViewData(source.view).getUuidOrNull("Use")
        if (using == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.useless"), source.view)
            return null
        }
        val partition = StorageDisk.findPartition(disks, using) ?: return null
        val type = partition.type
        if (type !is TextPartitionType) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.incorrect_type"), source.view)
            return null
        }

        val level = source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val parent = storage.getRootFolder(using) ?: return null

        val label = data.getString("Name")
        val contents = data.getString("Contents")
        val file = StorageFile(UUID.randomUUID(), label)

        if (!type.writeToFile(storage, file, contents)) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.failed", label, parent.name), source.view)
            return null
        }

        if (!partition.recalculateSizes(storage, parent, offset = file.size)) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.free_space", label, parent.name), source.view)
            return null
        }

        storage.saveFile(file, parent)

        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.created", label, parent.name), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, name: String, contents: String): Int {
        val nbt = NbtCompound()
        nbt.putString("Name", name)
        nbt.putString("Contents", contents)

        return startTask(context.source, nbt)
    }

}