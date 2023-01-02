package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.StoragePathArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDivision
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StoragePath
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StoragePathResolver
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.context.CommandContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Formatting

class ListConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            prepare(it, StoragePath("."))
        }.then(LCCConsolePrograms.required("dir", StoragePathArgumentType()).executes {
            prepare(it, StoragePathArgumentType.get(it, "dir"))
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
                Text.translatable("terminal.lcc.console.$name.item.left", Text.literal("⋆").formatted(Formatting.AQUA), file.name),
                Text.translatable("terminal.lcc.console.$name.item.right", file.size)
            ), source.view) {
                if (it == 1) this.putString("Alignment", "Right")
                else this.putBoolean("Fill", true)
            }
        }
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, path: StoragePath): Int {
        val nbt = NbtCompound()

        val using = context.source.session.getViewData(context.source.view).getUuidOrNull("Use")
        val working = context.source.session.getViewData(context.source.view).getUuidOrNull("Working")

        val disks = context.source.context.getAccessibleDisks()
        val level = context.source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val resolver = StoragePathResolver(path)
            .interest(StorageDivision.StorageDivisionType.FILE, StorageDivision.StorageDivisionType.FOLDER)
            .withCurrentPartition(using)
            .withCurrentDirectory(working)
        println(resolver.get(disks, storage))
        /*when (resolver.get(disks, storage)) {
            is StorageFile -> 0//TODO show listing of exact file
            is StorageFolder -> 1//TODO show listing of folder
            is StorageDisk -> 2//TODO show root folder of ONE partition ONLY
            is StoragePartition -> 3//TODO show root folder of the partition
            else -> 4//TODO this is a search, get the specified parent directory and show all files and folder that contain the path result
        }*/

        return startTask(context.source, nbt)
    }

}