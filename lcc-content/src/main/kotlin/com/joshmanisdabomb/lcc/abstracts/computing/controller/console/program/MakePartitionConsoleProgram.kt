package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.PartitionArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.abstracts.computing.partition.SystemPartitionType
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.extensions.getText
import com.joshmanisdabomb.lcc.extensions.putText
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.*

class MakePartitionConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, preference = DiskInfoArgumentType.DiskInfoArgumentResult.DISK)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestDisks(context.source.context.getAccessibleDisks(), builder), builder) }
            .then(LCCConsolePrograms.required("partition", PartitionArgumentType { it !is SystemPartitionType })
                .then(LCCConsolePrograms.required("label", StringArgumentType.string()).executes {
                    prepare(it, DiskInfoArgumentType.get(it, "disk"), PartitionArgumentType.get(it, "partition"), StringArgumentType.getString(it, "label"))
                }.then(LCCConsolePrograms.required("size", IntegerArgumentType.integer(1)).suggests { context, builder -> CommandSource.suggestMatching(suggestSize(context, builder), builder) }.executes {
                    prepare(it, DiskInfoArgumentType.get(it, "disk"), PartitionArgumentType.get(it, "partition"), StringArgumentType.getString(it, "label"), IntegerArgumentType.getInteger(it, "size"))
                })
            )
        ))

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val type = LCCPartitionTypes.registry.get(Identifier(data.getString("Partition"))) ?: return null

        val diskId = data.getUuid("Disk")
        val diskShort = data.getString("DiskShort")
        val diskLabel = data.getText("DiskLabel")
        val disks = source.context.getAccessibleDisks()
        val disk = DiskInfo.getDisk(disks, diskId)
        if (disk == null) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.interrupt", diskLabel, diskShort), source.view)
            return null
        }

        val label = data.getString("Label")
        val size = data.getInt("Size")

        val partition = DiskPartition(UUID.randomUUID(), label, type, size)
        disk.addPartition(partition)
        val level = source.context.getWorldFromContext().levelProperties
        val storage = LCCComponents.computing_storage.maybeGet(level).orElseThrow()
        storage.addRootFolder(partition.id!!)
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.success", Text.translatable(partition.type.translationKey), partition.label, partition.getShortId(disks), partition.size, diskLabel, diskShort), source.view)
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, type: PartitionType, label: String, size: Int? = null): Int {
        if (label.isEmpty()) throw labelEmpty.create()
        if (label.matches(LabelConsoleProgram.labelRegex)) throw labelInvalid.create()

        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchDisks(disks)
        val disk = DiskInfoArgumentType.getSingleDisk(results, search) ?: throw DiskInfoArgumentType.noDisks.create(search)
        val shortId = disk.getShortId(disks)

        val total = disk.totalSpace
        val allocable = disk.allocableSpace
        val finalSize = if (size == null) {
            if (allocable <= 0) throw allocableSpaceNone.create(disk.name, shortId)
            allocable
        } else {
            if (allocable < size) throw allocableSpaceLow.create(disk.name, shortId, allocable, size)
            size
        }
        if (total < finalSize) throw totalSpaceLow.create(disk.name, shortId, total, finalSize)

        val nbt = NbtCompound()
        nbt.putUuid("Disk", disk.id)
        nbt.putString("DiskShort", shortId)
        nbt.putText("DiskLabel", disk.name)
        nbt.putString("Partition", type.id.toString())
        nbt.putInt("Size", finalSize)
        nbt.putString("Label", label)
        return startTask(context.source, nbt)
    }

    private fun suggestSize(context: CommandContext<ConsoleCommandSource>, builder: SuggestionsBuilder): List<String> {
        val search = DiskInfoArgumentType.get(context, "disk")
        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchDisks(disks)
        return results.map { it.allocableSpace.toString() }
    }

    private val labelEmpty = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.label_empty"))
    private val labelInvalid = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.$name.label_invalid"))

    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> Text.translatable("terminal.lcc.console.$name.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> Text.translatable("terminal.lcc.console.$name.space_allocable", a, b, c, d) }
    private val allocableSpaceNone = Dynamic2CommandExceptionType { a, b -> Text.translatable("terminal.lcc.console.$name.no_allocable", a, b) }

}
