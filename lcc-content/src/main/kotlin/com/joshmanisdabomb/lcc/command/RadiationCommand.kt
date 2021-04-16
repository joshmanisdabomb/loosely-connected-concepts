package com.joshmanisdabomb.lcc.command

import com.joshmanisdabomb.lcc.directory.LCCComponents
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.TranslatableText

class RadiationCommand(val name: String) {

    val command by lazy {
        CommandManager.literal(name).requires { it.hasPermissionLevel(4) }
            .then(CommandManager.literal("add").then(CommandManager.argument("targets", EntityArgumentType.entities()).then(CommandManager.argument("amount", FloatArgumentType.floatArg()).executes {
                add(it.source, EntityArgumentType.getEntities(it, "targets"), FloatArgumentType.getFloat(it, "amount"))
            })))
            .then(CommandManager.literal("set").then(CommandManager.argument("targets", EntityArgumentType.entities()).then(CommandManager.argument("amount", FloatArgumentType.floatArg(0f)).executes {
                set(it.source, EntityArgumentType.getEntities(it, "targets"), FloatArgumentType.getFloat(it, "amount"))
            })))
            .then(CommandManager.literal("query").then(CommandManager.argument("target", EntityArgumentType.entity()).executes {
                query(it.source, EntityArgumentType.getEntity(it, "target"))
            }))
    }

    fun add(source: ServerCommandSource, targets: Collection<Entity>, amount: Float): Int {
        if (targets.none { it is LivingEntity }) {
            if (targets.size == 1) {
                throw failedLivingSingleException.create(targets.first().displayName)
            } else {
                throw failedLivingMultipleException.create()
            }
        }

        targets.filterIsInstance<LivingEntity>().forEach {
            LCCComponents.radiation.maybeGet(it).ifPresent { it.exposure = it.exposure.plus(amount).coerceAtLeast(0f) }
            if (it is ServerPlayerEntity) LCCComponents.radiation.sync(it)
        }

        if (targets.size == 1) {
            source.sendFeedback(TranslatableText("commands.lcc.$name.add.success.single", amount, targets.first().displayName), true)
        } else {
            source.sendFeedback(TranslatableText("commands.lcc.$name.add.success.multiple", amount, targets.size), true)
        }

        return targets.size
    }

    fun set(source: ServerCommandSource, targets: Collection<Entity>, amount: Float): Int {
        if (targets.none { it is LivingEntity }) {
            if (targets.size == 1) {
                throw failedLivingSingleException.create(targets.first().displayName)
            } else {
                throw failedLivingMultipleException.create()
            }
        }

        targets.filterIsInstance<LivingEntity>().forEach {
            LCCComponents.radiation.maybeGet(it).ifPresent { it.exposure = amount.coerceAtLeast(0f) }
            if (it is ServerPlayerEntity) LCCComponents.radiation.sync(it)
        }

        if (targets.size == 1) {
            source.sendFeedback(TranslatableText("commands.lcc.$name.set.success.single", targets.first().displayName, amount), true)
        } else {
            source.sendFeedback(TranslatableText("commands.lcc.$name.set.success.multiple", targets.size, amount), true)
        }

        return targets.size
    }

    fun query(source: ServerCommandSource, target: Entity): Int {
        if (target !is LivingEntity) throw failedLivingSingleException.create(target.displayName)
        val radiation = LCCComponents.radiation.maybeGet(target).map { it.exposure }.orElse(0f)
        source.sendFeedback(TranslatableText("commands.lcc.$name.query", target.displayName, radiation), false)
        return radiation.toInt()
    }

    private val failedLivingSingleException = DynamicCommandExceptionType { TranslatableText("commands.lcc.$name.failed.living.single", it) }
    private val failedLivingMultipleException = SimpleCommandExceptionType(TranslatableText("commands.lcc.$name.failed.living.multiple"))

}