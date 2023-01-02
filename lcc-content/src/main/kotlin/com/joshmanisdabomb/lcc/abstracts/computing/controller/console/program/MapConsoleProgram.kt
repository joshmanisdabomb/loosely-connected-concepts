package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.storage.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.joshmanisdabomb.lcc.extensions.*
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

class MapConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            prepare(it, null)
        }.then(LCCConsolePrograms.required("disk", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.DISK, DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestAll(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            prepare(it, DiskInfoArgumentType.get(it, "disk"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val diskLabels = data.getCompound("DiskLabels")
        val diskCustomLabels = data.getCompound("DiskCustomLabels")
        val diskShorts = data.getCompound("DiskShorts")
        val diskUsed = data.getCompound("DiskUsed")
        val diskTotal = data.getCompound("DiskTotal")
        val partitionLabels = data.getCompound("PartitionLabels")
        val partitionShorts = data.getCompound("PartitionShorts")
        val partitionTypes = data.getCompound("PartitionTypes")
        val partitionSizes = data.getCompound("PartitionSizes")
        val partitionUsed = data.getCompound("PartitionUsed")
        data.getCompound("IdMap").forEachStringList { k, v ->
            source.controller.writeColumns(source.session, listOf(
                Text.translatable("terminal.lcc.console.$name.disk.left", diskLabels.getText(k)?.formatted(if (diskCustomLabels.getStringOrNull(k) == null) Formatting.DARK_GRAY else Formatting.RESET), diskShorts.getString(k)),
                Text.translatable("terminal.lcc.console.$name.disk.right", diskUsed.getInt(k), diskTotal.getInt(k)).formatted(if (v.isEmpty()) Formatting.DARK_GRAY else Formatting.RESET)
            ), source.view) {
                if (it == 1) this.putString("Alignment", "Right")
                else this.putBoolean("Fill", true)
            }
            v.forEach {
                val type = LCCPartitionTypes.registry[Identifier(partitionTypes.getString(it))]
                val formatting = type?.nameColor ?: Formatting.RESET
                val right = if (partitionUsed.contains(it)) {
                    Text.translatable("terminal.lcc.console.$name.partition.used.right", partitionUsed.getInt(it), partitionSizes.getInt(it))
                } else {
                    Text.translatable("terminal.lcc.console.$name.partition.right", partitionSizes.getInt(it))
                }
                source.controller.writeColumns(source.session, listOf(
                    Text.translatable("terminal.lcc.console.$name.partition.left", Text.literal(partitionLabels.getString(it)).formatted(formatting), partitionShorts.getString(it)),
                    right
                ), source.view) {
                    if (it == 1) this.putString("Alignment", "Right")
                    else this.putBoolean("Fill", true)
                }

            }
        }
        return null
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch?): Int {
        val nbt = NbtCompound()

        val disks = context.source.context.getAccessibleDisks()
        val search = search ?: DiskInfoSearch("").diskDefaultInclusion().partitionDefaultInclusion()
        val results = search.search(disks)
        val diskResults = results.first ?: emptySet()
        val partitionResults = results.second ?: emptySet()
        val filteredDisks = (diskResults + partitionResults.map { it.disk!! }).distinct()
        if (filteredDisks.isEmpty()) throw DiskInfoArgumentType.noGeneric.create(search)

        val map = filteredDisks.associateWith { if (diskResults.contains(it)) it.partitions else it.partitions.filter { partitionResults.any { p -> it.id == p.id } } }
        val partitions = map.values.flatten()

        val diskShortIds = map.keys.associateWith { it.getShortId(disks) }
        val diskLabels = map.keys.associateWith { it.name }
        val diskCustomLabels = map.keys.associateWith { it.label }
        val diskUsed = map.keys.associateWith { it.usedSpace }
        val diskTotal = map.keys.associateWith { it.totalSpace }
        val partitionShortIds = partitions.associateWith { it.getShortId(disks) }
        val partitionLabels = partitions.associateWith { it.label }
        val partitionTypes = partitions.associateWith { it.type }
        val partitionSizes = partitions.associateWith { it.size }
        val partitionUsed = partitions.associateWith { it.type.noFreeSpace.transform(null, it.usedSpace) }

        nbt.modifyCompound("IdMap") { map.forEach { (k, v) -> putStringUuidList(k.id.toString(), v.mapNotNull { it.id }) } }
        nbt.modifyCompound("DiskShorts") { diskShortIds.forEach { (k, v) -> putString(k.id.toString(), v) } }
        nbt.modifyCompound("DiskLabels") { diskLabels.forEach { (k, v) -> putText(k.id.toString(), v) } }
        nbt.modifyCompound("DiskCustomLabels") { diskCustomLabels.forEach { (k, v) -> putStringOrRemove(k.id.toString(), v) } }
        nbt.modifyCompound("DiskUsed") { diskUsed.forEach { (k, v) -> putInt(k.id.toString(), v) } }
        nbt.modifyCompound("DiskTotal") { diskTotal.forEach { (k, v) -> putInt(k.id.toString(), v) } }
        nbt.modifyCompound("PartitionShorts") { partitionShortIds.forEach { (k, v) -> putString(k.id.toString(), v) } }
        nbt.modifyCompound("PartitionLabels") { partitionLabels.forEach { (k, v) -> putString(k.id.toString(), v) } }
        nbt.modifyCompound("PartitionTypes") { partitionTypes.forEach { (k, v) -> putString(k.id.toString(), v.id.toString()) } }
        nbt.modifyCompound("PartitionSizes") { partitionSizes.forEach { (k, v) -> putInt(k.id.toString(), v) } }
        nbt.modifyCompound("PartitionUsed") { partitionUsed.forEach { (k, v) -> putIntOrRemove(k.id.toString(), v) } }

        return startTask(context.source, nbt)
    }

}