package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import java.util.*

class EchoConsoleProgram(literal: String, val view: ConsoleCommandSource.() -> UUID?, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .then(LCCConsolePrograms.required("message", StringArgumentType.greedyString()).executes {
            val nbt = NbtCompound()
            nbt.putString("Message", StringArgumentType.getString(it, "message"))

            startTask(it.source, nbt)
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val message = data.getString("Message")
        source.controller.write(source.session, Text.literal(message), source.view())
        return null
    }

}