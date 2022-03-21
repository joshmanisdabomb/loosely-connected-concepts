package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.partition.SystemPartitionType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.TranslatableText

class ResizePartitionConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("partition", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION, preference = DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestPartitions(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            prepare(it, DiskInfoArgumentType.get(it, "partition"))
        }.then(LCCConsolePrograms.required("size", IntegerArgumentType.integer(1)).executes {
            prepare(it, DiskInfoArgumentType.get(it, "partition"), IntegerArgumentType.getInteger(it, "size"))
        }))

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val partitionId = data.getUuid("Partition")
        val partitionShort = data.getString("PartitionShort")
        val partitionLabel = data.getString("PartitionLabel")
        val diskShort = data.getString("DiskShort")
        val diskLabel = data.getString("DiskLabel")
        val disks = source.context.getAccessibleDisks()
        val partition = DiskInfo.findPartition(disks, partitionId)
        if (partition == null) {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.interrupt", partitionLabel, partitionShort), source.view)
            return null
        }

        val size = data.getInt("Size")
        val currentSize = partition.size
        if (size > currentSize) {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.extend", partitionLabel, partitionShort, diskLabel, diskShort, currentSize, size), source.view)
        } else {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.shrink", partitionLabel, partitionShort, diskLabel, diskShort, currentSize, size), source.view)
        }
        partition.size = size
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch, size: Int? = null): Int {
        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchPartitions(DiskInfo.getPartitions(disks))
        val partition = DiskInfoArgumentType.getSinglePartition(results, search) ?: throw DiskInfoArgumentType.noPartitions.create(search)
        val shortId = partition.getShortId(disks)

        if (partition.type is SystemPartitionType) throw systemPartition.create(partition.label, shortId)

        val disk = partition.disk ?: throw DiskInfoArgumentType.noPartitions.create(search)
        val diskShortId = disk.getShortId(disks)

        val total = disk.totalSpace
        val currentSize = partition.size
        val allocable = disk.allocableSpace + currentSize
        val finalSize = if (size == null) {
            if (allocable <= 0) throw allocableSpaceNone.create(partition.label, shortId)
            allocable
        } else {
            if (allocable < size) throw allocableSpaceLow.create(partition.label, shortId, allocable, size)
            size
        }
        if (total < finalSize) throw totalSpaceLow.create(partition.label, shortId, total, finalSize)

        val used = partition.usedSpace
        if (finalSize < used) throw spaceUsed.create(partition.label, shortId, used, finalSize)
        if (currentSize == finalSize) throw sizeUnchanged.create(partition.label, shortId, finalSize)

        val nbt = NbtCompound()
        nbt.putUuid("Partition", partition.id)
        nbt.putString("PartitionShort", shortId)
        nbt.putString("PartitionLabel", partition.label)
        nbt.putUuid("Disk", disk.id)
        nbt.putString("DiskShort", diskShortId)
        nbt.putString("DiskLabel", disk.label)
        nbt.putInt("Size", finalSize)
        return startTask(context.source, nbt)
    }

    private val systemPartition = Dynamic2CommandExceptionType { a, b -> TranslatableText("terminal.lcc.console.$name.system", a, b) }
    private val sizeUnchanged = Dynamic3CommandExceptionType { a, b, c -> TranslatableText("terminal.lcc.console.$name.equal", a, b, c) }
    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.$name.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.$name.space_allocable", a, b, c, d) }
    private val allocableSpaceNone = Dynamic2CommandExceptionType { a, b -> TranslatableText("terminal.lcc.console.$name.no_allocable", a, b) }
    private val spaceUsed = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.$name.no_used", a, b, c, d) }

}
