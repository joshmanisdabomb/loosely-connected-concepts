package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.PacketRegistry
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.util.Identifier
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

object LCCPackets : ThingDirectory<PacketConsumer, PacketRegistry>() {

    val gauntletSwitch by create(ServerSidePacketRegistry.INSTANCE) { PacketConsumer { context, data ->
        val ability = data.readByte()
        context.taskQueue.execute {
            if (context.player?.mainHandStack?.item == LCCItems.gauntlet) {
                context.player.mainHandStack.orCreateTag.putByte("lcc_gauntlet_ability", ability)
            }
        }
    } }

    fun KProperty0<PacketConsumer>.id(): Identifier = LCC.id(name)

    override fun registerAll(things: Map<String, PacketConsumer>, properties: Map<String, PacketRegistry>) {
        for ((k, v) in things) {
            properties[k]?.register(LCC.id(k), v)
        }
    }

}