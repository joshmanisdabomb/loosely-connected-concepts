package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry

object LCCPackets : ThingDirectory<PacketConsumer, PacketRegistry>() {

    //val gauntletUpdate by create(ClientSidePacketRegistry.INSTANCE) { PacketConsumer { context, data -> context.taskQueue.execute { context.player.dataTracker. } } }

}