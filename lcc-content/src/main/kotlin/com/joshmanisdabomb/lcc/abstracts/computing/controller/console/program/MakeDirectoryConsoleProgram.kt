package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageFolder
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import java.util.*

class MakeDirectoryConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("name", StringArgumentType.greedyString()).executes {
            prepare(it, StringArgumentType.getString(it, "name"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val disks = source.context.getAccessibleDisks()

        val using = source.session.getViewData(source.view).getUuidOrNull("Use")
        if (using == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.useless"), source.view)
            return null
        }
        val partition = StorageDisk.findPartition(disks, using) ?: return null

        val level = source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val parent = storage.getRootFolder(using) ?: return null

        val label = data.getString("Name")
        val new = StorageFolder(UUID.randomUUID(), label)
        storage.saveFolder(new, parent)

        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.created", label, parent.name), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, name: String): Int {
        val nbt = NbtCompound()
        nbt.putString("Name", name)

        return startTask(context.source, nbt)
    }

}