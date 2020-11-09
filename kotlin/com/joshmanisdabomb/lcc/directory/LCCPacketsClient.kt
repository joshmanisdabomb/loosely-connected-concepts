package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry

object LCCPacketsClient : PacketDirectory() {

    override val _registry = ClientSidePacketRegistry.INSTANCE

}