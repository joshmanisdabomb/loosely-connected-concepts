package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.util.Identifier

abstract class PacketForServerDirectory : AdvancedDirectory<ServerPlayNetworking.PlayChannelHandler, Identifier, Unit, Unit>() {

    fun initialiser(input: ServerPlayNetworking.PlayChannelHandler, context: DirectoryContext<Unit>, parameters: Unit) = context.id.also { ServerPlayNetworking.registerGlobalReceiver(it, input) }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}