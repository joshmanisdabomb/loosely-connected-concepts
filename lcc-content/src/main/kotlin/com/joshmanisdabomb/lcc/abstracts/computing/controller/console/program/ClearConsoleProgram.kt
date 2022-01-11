package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import net.minecraft.nbt.NbtCompound

class ClearConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes { startTask(it.source) }

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        source.controller.clear(source.session, source.view)
        return null
    }

}