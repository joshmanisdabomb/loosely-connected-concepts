package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry
import net.minecraft.client.MinecraftClient

object LCCPacketsClient : PacketDirectory() {

    override val _registry = ClientSidePacketRegistry.INSTANCE

}