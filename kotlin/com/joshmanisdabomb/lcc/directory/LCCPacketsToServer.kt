package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.concepts.gauntlet.GauntletAction
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry

object LCCPacketsToServer : PacketDirectory() {

    override val _registry = ServerSidePacketRegistry.INSTANCE

    val gauntlet_switch by create { PacketConsumer { context, data ->
        val ability = data.readByte().coerceIn(0, GauntletAction.values().size.toByte())
        context.taskQueue.execute {
            if (context.player?.mainHandStack?.item == LCCItems.gauntlet) {
                context.player.mainHandStack.orCreateTag.putByte("lcc_gauntlet_ability", ability)
            }
        }
    } }

}