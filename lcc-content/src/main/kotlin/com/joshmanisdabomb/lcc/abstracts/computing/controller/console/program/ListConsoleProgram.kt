package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.StoragePathArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.*
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import java.util.concurrent.CompletableFuture

class ListConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            prepare(it, StoragePath("."))
        }.then(LCCConsolePrograms.required("dir", StoragePathArgumentType()).suggests(this::getPathSuggestions).executes {
            prepare(it, StoragePathArgumentType.get(it, "dir"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val disks = source.context.getAccessibleDisks()

        val dirId = data.getUuid("Directory")
        val dirName = data.getString("DirectoryName")
        val partitionId = data.getUuid("Partition")
        val partitionShort = data.getString("PartitionShort")
        val partitionLabel = data.getString("PartitionLabel")
        val file = data.getUuidOrNull("File")
        val partition = StorageDisk.findPartition(disks, partitionId)
        if (partition == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.interrupt", dirName, partitionLabel, partitionShort), source.view)
            return null
        }

        val level = source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val folder = storage.getFolder(dirId)
        if (folder == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.exists", dirName, partitionLabel, partitionShort), source.view)
            return null
        }

        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.header", folder.name), source.view)

        var folders = emptyList<StorageFolder>()
        val files = if (file != null) {
            listOfNotNull(storage.getFile(file))
        } else {
            folders = storage.getFolders(*folder.folders.toTypedArray()).sortedWith(compareBy { it.name })
            storage.getFiles(*folder.files.toTypedArray()).sortedWith(compareBy { it.name })
        }
        source.controller.writeColumns(source.session, listOf(
            Text.translatable("terminal.lcc.console.$name.item.left", Text.literal(".")),
            Text.translatable("terminal.lcc.console.$name.item.right", folder.usedCache)
        ), source.view) {
            if (it == 1) this.putString("Alignment", "Right")
            else this.putBoolean("Fill", true)
        }
        if (folder.path.isNotEmpty()) {
            val parent = storage.getFolder(folder.path.last())
            if (parent != null) {
                source.controller.writeColumns(source.session, listOf(
                    Text.translatable("terminal.lcc.console.$name.item.left", Text.literal("..")),
                    Text.translatable("terminal.lcc.console.$name.item.right", parent.usedCache)
                ), source.view) {
                    if (it == 1) this.putString("Alignment", "Right")
                    else this.putBoolean("Fill", true)
                }
            }
        }
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
        val working = context.source.session.getViewData(context.source.view).getUuidOrNull("WorkingDir") ?: context.source.session.getViewData(context.source.view).getUuidOrNull("RootDir")

        val disks = context.source.context.getAccessibleDisks()
        val level = context.source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val resolver = StoragePathResolver(path)
            .interest(StorageDivision.StorageDivisionType.PARTITION, StorageDivision.StorageDivisionType.FILE, StorageDivision.StorageDivisionType.FOLDER)
            .withCurrentPartition(using)
            .withCurrentDirectory(working)

        val resolved = resolver.single(disks, storage)
        if (resolved.division == null) {
            throw when (resolved.path.type) {
                StorageDivision.StorageDivisionType.DISK -> unresolvedDisk.create(resolved.path.result.input)
                StorageDivision.StorageDivisionType.PARTITION -> unresolvedPartition.create(resolved.path.result.input)
                null -> unresolved.create(resolved.path.input)
                else -> unresolvedFolder.create(resolved.path.result.input)
            }
        }
        val partition = resolved.partition ?: throw missingPartition.create()
        nbt.putUuid("Partition", partition.id)
        nbt.putString("PartitionShort", partition.getShortId(disks))
        nbt.putString("PartitionLabel", partition.label)
        if (resolved.file != null) nbt.putUuid("File", resolved.file!!.id)
        val folder = resolved.folder ?: throw missingFolder.create()
        nbt.putUuid("Directory", folder.id)
        nbt.putString("DirectoryName", folder.name)

        return startTask(context.source, nbt)
    }

    private fun getPathSuggestions(context: CommandContext<ConsoleCommandSource>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val using = context.source.session.getViewData(context.source.view).getUuidOrNull("Use")
        val working = context.source.session.getViewData(context.source.view).getUuidOrNull("WorkingDir") ?: context.source.session.getViewData(context.source.view).getUuidOrNull("RootDir")
        val level = context.source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val suggestions = StoragePathSuggestor(StorageDivision.StorageDivisionType.FOLDER, StorageDivision.StorageDivisionType.PARTITION, StorageDivision.StorageDivisionType.DISK)
            .withCurrentPartition(using)
            .withCurrentDirectory(working)
            .suggest(context.source.context.getAccessibleDisks(), storage, builder)
        return CommandSource.suggestMatching(suggestions, builder)
    }

    private val unresolved = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved", it) }
    private val unresolvedDisk = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved.disk", it) }
    private val unresolvedPartition = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved.partition", it) }
    private val unresolvedFolder = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved.folder", it) }
    private val missingPartition = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.partition"))
    private val missingFolder = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.folder"))

}