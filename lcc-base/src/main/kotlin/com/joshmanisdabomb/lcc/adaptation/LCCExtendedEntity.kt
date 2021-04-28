package com.joshmanisdabomb.lcc.adaptation

import com.joshmanisdabomb.lcc.directory.LCCBasePacketsForClient
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.Entity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

interface LCCExtendedEntity {

    @JvmDefault
    fun lcc_createSpawnPacket(): Packet<*> {
        val buf = PacketByteBuf(Unpooled.buffer())
        val me = this as Entity
        buf.writeIdentifier(Registry.ENTITY_TYPE.getId(me.type))
        buf.writeInt(me.id)
        buf.writeUuid(me.uuid)
        buf.writeDouble(me.x)
        buf.writeDouble(me.y)
        buf.writeDouble(me.z)
        buf.writeFloat(me.method_36455())
        buf.writeFloat(me.method_36454())
        return ServerSidePacketRegistry.INSTANCE.toPacket(LCCBasePacketsForClient[LCCBasePacketsForClient::spawn_packet].first().id, buf)
    }

    @JvmDefault
    fun lcc_readSpawnPacket(data: PacketByteBuf) {

    }

}
