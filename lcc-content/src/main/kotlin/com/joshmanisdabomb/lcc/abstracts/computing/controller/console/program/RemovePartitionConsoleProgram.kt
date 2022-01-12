package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.DiskInfoArgumentType
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.getBooleanOrNull
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.TranslatableText
import org.lwjgl.glfw.GLFW

class RemovePartitionConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("partition", DiskInfoArgumentType(DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION, preference = DiskInfoArgumentType.DiskInfoArgumentResult.PARTITION)).suggests { context, builder -> CommandSource.suggestMatching(DiskInfoArgumentType.suggestPartitions(context.source.context.getAccessibleDisks(), builder), builder) }.executes {
            prepare(it, DiskInfoArgumentType.get(it, "partition"))
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val partitionId = data.getUuid("Partition")
        val partitionShort = data.getString("PartitionShort")
        val partitionLabel = data.getString("PartitionLabel")
        val disks = source.context.getAccessibleDisks()
        val disk = DiskInfo.getDiskWithPartition(disks, partitionId)
        if (disk == null) {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.rmpart.interrupt", partitionLabel, partitionShort), source.view)
            return null
        }

        if (data.getInt("Ticks") <= 0) source.controller.write(source.session, TranslatableText("terminal.lcc.console.rmpart.prompt", partitionLabel, partitionShort), source.view)
        return when (data.getBooleanOrNull("Prompt")) {
            true -> {
                disk.removePartition(partitionId)
                source.controller.write(source.session, TranslatableText("terminal.lcc.console.rmpart.success", partitionLabel, partitionShort), source.view)
                null
            }
            false -> {
                source.controller.write(source.session, TranslatableText("terminal.lcc.console.rmpart.aborted"), source.view)
                null
            }
            else -> true
        }
    }

    override fun keyPressed(session: ComputingSession, data: NbtCompound, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int): Boolean {
        if (!data.contains("Prompt")) {
            return when (keyCode) {
                GLFW.GLFW_KEY_Y -> {
                    data.putBoolean("Prompt", true)
                    true
                }
                GLFW.GLFW_KEY_N -> {
                    data.putBoolean("Prompt", false)
                    true
                }
                else -> false
            }
        }
        return false
    }

    fun prepare(context: CommandContext<ConsoleCommandSource>, search: DiskInfoSearch): Int {
        val disks = context.source.context.getAccessibleDisks()
        val results = search.searchPartitions(disks.flatMap { it.partitions })
        val partition = DiskInfoArgumentType.getSinglePartition(results, search) ?: throw DiskInfoArgumentType.noPartitions.create(search)
        val partitionId = partition.getShortId(disks)

        val nbt = NbtCompound()
        nbt.putUuid("Partition", partition.id)
        nbt.putString("PartitionShort", partitionId)
        nbt.putString("PartitionLabel", partition.label)
        return startTask(context.source, nbt)
    }

    private val labelEmpty = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.mkpart.label_empty"))
    private val labelInvalid = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.mkpart.label_invalid"))

    private val totalSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.mkpart.space_total", a, b, c, d) }
    private val allocableSpaceLow = Dynamic4CommandExceptionType { a, b, c, d -> TranslatableText("terminal.lcc.console.mkpart.space_allocable", a, b, c, d) }
    private val allocableSpaceNone = Dynamic2CommandExceptionType { a, b -> TranslatableText("terminal.lcc.console.mkpart.no_allocable", a, b) }

}
