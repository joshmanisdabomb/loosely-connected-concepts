package com.joshmanisdabomb.lcc.command

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.mojang.brigadier.arguments.FloatArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

class WinterCommand(val name: String) {

    val command by lazy {
        CommandManager.literal(name).requires { it.hasPermissionLevel(4) }
            .then(CommandManager.literal("add").then(CommandManager.argument("amount", FloatArgumentType.floatArg(-6f, 6f)).executes {
                add(it.source, FloatArgumentType.getFloat(it, "amount"))
            }))
            .then(CommandManager.literal("set").then(CommandManager.argument("amount", FloatArgumentType.floatArg(0f, 6f)).executes {
                set(it.source, FloatArgumentType.getFloat(it, "amount"))
            }))
            .then(CommandManager.literal("query").executes {
                query(it.source)
            })
    }

    fun add(source: ServerCommandSource, amount: Float): Int {
        LCCComponents.nuclear.maybeGet(source.world).ifPresent { it.winter = it.winter.plus(amount).coerceIn(0f, 6f) }
        LCCComponents.nuclear.sync(source.world)

        source.sendFeedback(TranslatableText("commands.lcc.$name.add.success", amount, source.world.registryKey.value), true)

        return 1
    }

    fun set(source: ServerCommandSource, amount: Float): Int {
        LCCComponents.nuclear.maybeGet(source.world).ifPresent { it.winter = amount.coerceIn(0f, 6f) }
        LCCComponents.nuclear.sync(source.world)

        source.sendFeedback(TranslatableText("commands.lcc.$name.set.success", source.world.registryKey.value, amount), true)

        return 1
    }

    fun query(source: ServerCommandSource): Int {
        val winter = LCCComponents.nuclear.maybeGet(source.world).map { it.winter }.orElse(0f)
        source.sendFeedback(TranslatableText("commands.lcc.$name.query", source.world.registryKey.value, winter), false)
        return winter.toInt()
    }

}