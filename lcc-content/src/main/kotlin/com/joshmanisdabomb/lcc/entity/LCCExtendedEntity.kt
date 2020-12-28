package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCPacketsToClient
import com.joshmanisdabomb.lcc.directory.LCCPacketsToClient.id
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.Entity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

interface LCCExtendedEntity {

    @JvmDefault
    fun lcc_createSpawnPacket(extra: (buf: PacketByteBuf) -> Unit = {}): Packet<*> {
        val buf = PacketByteBuf(Unpooled.buffer())
        val me = this as Entity
        buf.writeIdentifier(Registry.ENTITY_TYPE.getId(me.type))
        buf.writeInt(me.entityId)
        buf.writeUuid(me.uuid)
        buf.writeDouble(me.x)
        buf.writeDouble(me.y)
        buf.writeDouble(me.z)
        buf.writeFloat(me.pitch)
        buf.writeFloat(me.yaw)
        return ServerSidePacketRegistry.INSTANCE.toPacket(LCCPacketsToClient.id { spawn_packet }, buf)
    }

    @JvmDefault
    fun lcc_handleSpawnPacket(data: PacketByteBuf) {

    }

}
