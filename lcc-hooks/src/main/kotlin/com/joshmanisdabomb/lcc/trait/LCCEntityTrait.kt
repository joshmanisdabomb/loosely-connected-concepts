package com.joshmanisdabomb.lcc.trait

import com.joshmanisdabomb.lcc.networking.TraitSpawnPacket
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.Entity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

interface LCCEntityTrait {

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
        buf.writeFloat(me.pitch)
        buf.writeFloat(me.yaw)
        return ServerSidePacketRegistry.INSTANCE.toPacket(TraitSpawnPacket.id, buf)
    }

    @JvmDefault
    fun lcc_readSpawnPacket(data: PacketByteBuf) {

    }

}
