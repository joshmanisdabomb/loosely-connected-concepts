package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.util.Identifier

abstract class PacketForClientDirectory : AdvancedDirectory<ClientPlayNetworking.PlayChannelHandler, Identifier, Unit, Unit>() {

    fun initialiser(input: ClientPlayNetworking.PlayChannelHandler, context: DirectoryContext<Unit>, parameters: Unit) = context.id.also { ClientPlayNetworking.registerGlobalReceiver(it, input) }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}