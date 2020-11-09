package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry

object LCCPacketsServer : PacketDirectory() {

    override val _registry = ServerSidePacketRegistry.INSTANCE

    val gauntletSwitch by create { PacketConsumer { context, data ->
        val ability = data.readByte()
        context.taskQueue.execute {
            if (context.player?.mainHandStack?.item == LCCItems.gauntlet) {
                context.player.mainHandStack.orCreateTag.putByte("lcc_gauntlet_ability", ability)
            }
        }
    } }

}