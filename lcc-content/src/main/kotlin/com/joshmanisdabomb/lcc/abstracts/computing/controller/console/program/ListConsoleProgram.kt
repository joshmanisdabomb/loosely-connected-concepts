package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.context.CommandContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Formatting

class ListConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            prepare(it)
        }


    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val disks = source.context.getAccessibleDisks()

        val using = source.session.getViewData(source.view).getUuidOrNull("Use")
        if (using != null) {
            val partition = DiskInfo.findPartition(disks, using)
            if (partition != null) {
                val level = source.context.getWorldFromContext().levelProperties
                val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
                val root = storage.getRootFolder(using) ?: return null
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.header", root.name, root.usedCache), source.view)
                val folders = storage.getFolders(*root.folders.toTypedArray()).values.sortedWith(compareBy { it.name })
                val files = storage.getFiles(*root.files.toTypedArray()).values.sortedWith(compareBy { it.name })
                for (folder in folders) {
                    source.controller.writeColumns(source.session, listOf(
                        Text.translatable("terminal.lcc.console.$name.item.left", Text.literal("/").formatted(Formatting.YELLOW), folder.name),
                        Text.translatable("terminal.lcc.console.$name.item.right", folder.usedCache)
                    ), source.view) {
                        if (it == 1) this.putString("Alignment", "Right")
                        else this.putBoolean("Fill", true)
                    }
                }
                for (file in files) {
                    source.controller.writeColumns(source.session, listOf(
                        Text.translatable("terminal.lcc.console.$name.item.left", Text.literal("⊙").formatted(Formatting.AQUA), file.name),
                        Text.translatable("terminal.lcc.console.$name.item.right", file.size)
                    ), source.view) {
                        if (it == 1) this.putString("Alignment", "Right")
                        else this.putBoolean("Fill", true)
                    }
                }
                return null
            }
        }

        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.useless"), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>): Int {
        val nbt = NbtCompound()

        return startTask(context.source, nbt)
    }

}