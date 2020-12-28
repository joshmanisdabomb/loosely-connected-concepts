package com.joshmanisdabomb.lcc.directory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.ServerCommandSource

object LCCCommands : ThingDirectory<LiteralArgumentBuilder<ServerCommandSource>, Unit>() {

    val lcc by lazy {
        /*literal("lcc").requires{it.hasPermissionLevel(4)}
            .then(literal("data")
                .then(literal("gen").executes { datagen(it) }
                    .then(CommandManager.argument("includeClient", BoolArgumentType.bool()).then(CommandManager.argument("includeServer", BoolArgumentType.bool()).executes{ datagen(it, BoolArgumentType.getBool(it, "includeClient"), BoolArgumentType.getBool(it, "includeServer")) }))
                ).then(literal("commit").executes {
                    val run = LCCData.commit()
                    if (run >= 0) {
                        it.source.sendFeedback(TranslatableText("commands.lcc.data.commit.success", run), true)
                    } else {
                        it.source.sendError(TranslatableText("commands.lcc.data.commit.failure"))
                    }
                    max(run.sign, 0)
                })
            )*/
    }

    override fun registerAll(things: Map<String, LiteralArgumentBuilder<ServerCommandSource>>, properties: Map<String, Unit>) {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, dedicated: Boolean ->
            things.forEach { k, v -> dispatcher.register(v) }
        })
    }

}

/*private fun datagen(context: CommandContext<ServerCommandSource>, client: Boolean = true, server: Boolean = true): Int {
    val run = LCCData.generate(client, server)
    if (run >= 0) {
        context.source.sendFeedback(TranslatableText("commands.lcc.data.gen.success", run), true)
    } else {
        context.source.sendError(TranslatableText("commands.lcc.data.gen.failure"))
    }
    return max(run.sign, 0)
}*/