package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import kotlin.reflect.KProperty0

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