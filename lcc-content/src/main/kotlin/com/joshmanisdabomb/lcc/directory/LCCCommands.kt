package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.command.RadiationCommand
import com.joshmanisdabomb.lcc.command.WinterCommand
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.ServerCommandSource

object LCCCommands : BasicDirectory<LiteralArgumentBuilder<ServerCommandSource>, Unit>() {

    val radiation by entry(::initialiser) { RadiationCommand(name).command }
    val nuclearwinter by entry(::initialiser) { WinterCommand(name).command }

    fun <C : LiteralArgumentBuilder<ServerCommandSource>> initialiser(input: C, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out LiteralArgumentBuilder<ServerCommandSource>, out LiteralArgumentBuilder<ServerCommandSource>>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        CommandRegistrationCallback.EVENT.register { d, server ->
            initialised.forEach {
                d.register(it.entry)
            }
        }
    }

}