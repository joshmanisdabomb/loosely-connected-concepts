package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.StoragePathArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.partition.SystemPartitionType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.*
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.joshmanisdabomb.lcc.extensions.putText
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text

class ChangeDirectoryConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            val nbt = NbtCompound()
            nbt.putString("Operation", "get")

            startTask(it.source, nbt)
        }.then(LCCConsolePrograms.required("dir", StoragePathArgumentType()).suggests { context, builder ->
            val using = context.source.session.getViewData(context.source.view).getUuidOrNull("Use")
            val working = context.source.session.getViewData(context.source.view).getUuidOrNull("WorkingDir") ?: context.source.session.getViewData(context.source.view).getUuidOrNull("RootDir")
            val level = context.source.context.getWorldFromContext().levelProperties
            val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
            val suggestions = StoragePathSuggestor(StorageDivision.StorageDivisionType.FOLDER, StorageDivision.StorageDivisionType.PARTITION, StorageDivision.StorageDivisionType.DISK)
                .withCurrentPartition(using)
                .withCurrentDirectory(working)
                .suggest(context.source.context.getAccessibleDisks(), storage, builder)
            CommandSource.suggestMatching(suggestions, builder)
        }.executes {
            prepare(it, StoragePathArgumentType.get(it, "dir"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val disks = source.context.getAccessibleDisks()

        if (data.getString("Operation") == "get") {
            val using = source.session.getViewData(source.view).getUuidOrNull("Use")
            val working = source.session.getViewData(source.view).getUuidOrNull("WorkingDir") ?: source.session.getViewData(source.view).getUuidOrNull("RootDir")
            if (using != null && working != null) {
                val partition = StorageDisk.findPartition(disks, using)
                if (partition != null) {
                    val disk = partition.disk!!

                    val level = source.context.getWorldFromContext().levelProperties
                    val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
                    val folder = storage.getFolder(working)

                    if (folder != null) {
                        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.get", storage.getAbsolutePath(folder).joinToString("/"), partition.label, partition.getShortId(disks), disk.name, disk.getShortId(disks)), source.view)
                        return null
                    }
                }
            }
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.get.empty"), source.view)
            return null
        }

        val dirId = data.getUuid("Directory")
        val dirName = data.getString("DirectoryName")
        val partitionId = data.getUuid("Partition")
        val partitionShort = data.getString("PartitionShort")
        val partitionLabel = data.getString("PartitionLabel")
        val diskShort = data.getString("DiskShort")
        val diskLabel = data.getType("DiskLabel")
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
        val root = storage.getRootFolder(partitionId)
        if (root == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.root", partitionLabel, partitionShort), source.view)
            return null
        }

        source.session.getViewData(source.view).putUuid("Use", partitionId)
        source.session.getViewData(source.view).putUuid("RootDir", root.id)
        source.session.getViewData(source.view).putUuid("WorkingDir", folder.id)
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.success", dirName, partitionLabel, partitionShort, diskLabel, diskShort), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, path: StoragePath): Int {
        val nbt = NbtCompound()
        nbt.putString("Operation", "set")

        val using = context.source.session.getViewData(context.source.view).getUuidOrNull("Use")
        val working = context.source.session.getViewData(context.source.view).getUuidOrNull("WorkingDir") ?: context.source.session.getViewData(context.source.view).getUuidOrNull("RootDir")

        val disks = context.source.context.getAccessibleDisks()
        val level = context.source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        val resolver = StoragePathResolver(path)
            .interest(StorageDivision.StorageDivisionType.PARTITION, StorageDivision.StorageDivisionType.FILE, StorageDivision.StorageDivisionType.FOLDER)
            .withCurrentPartition(using)
            .withCurrentDirectory(working)

        val result = resolver.single(disks, storage)
        if (result.division == null) {
            throw when (result.path.type) {
                StorageDivision.StorageDivisionType.DISK -> unresolvedDisk.create(result.path.result.input)
                StorageDivision.StorageDivisionType.PARTITION -> unresolvedPartition.create(result.path.result.input)
                null -> unresolved.create(result.path.input)
                else -> unresolvedFolder.create(result.path.result.input)
            }
        }
        val partition = result.partition ?: throw missingPartition.create()
        val partitionId = partition.id ?: throw uninitialisedPartition.create(partition.label)
        val shortId = partition.getShortId(disks)
        if (partition.type is SystemPartitionType) throw systemPartition.create(partition.label, shortId)

        val disk = result.disk ?: throw missingDisk.create(partition.label)
        val diskShortId = disk.getShortId(disks)
        
        if (result.file != null) throw missingFile.create()
        val folder = result.folder ?: throw missingFolder.create()
        nbt.putUuid("Directory", folder.id)
        nbt.putString("DirectoryName", folder.name)

        nbt.putUuid("Partition", partitionId)
        nbt.putString("PartitionShort", shortId)
        nbt.putString("PartitionLabel", partition.label)
        nbt.putUuid("Disk", disk.id)
        nbt.putString("DiskShort", diskShortId)
        nbt.putText("DiskLabel", disk.name)

        return startTask(context.source, nbt)
    }

    private val systemPartition = Dynamic2CommandExceptionType { a, b -> Text.translatable("terminal.lcc.console.$name.system", a, b) }
    private val unresolved = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved", it) }
    private val unresolvedDisk = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved.disk", it) }
    private val unresolvedPartition = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved.partition", it) }
    private val unresolvedFolder = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.unresolved.folder", it) }
    private val missingPartition = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.partition"))
    private val uninitialisedPartition = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.partition.id", it) }
    private val missingDisk = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.$name.disk", it) }
    private val missingFile = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.file"))
    private val missingFolder = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.folder"))

}