package com.joshmanisdabomb.lcc.trait

import com.joshmanisdabomb.lcc.networking.TraitSpawnPacket
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

interface LCCEntityTrait {

    @JvmDefault
    fun lcc_createSpawnPacket(append: NbtCompound.() -> Unit = {}): Packet<*> {
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

        buf.writeNbt(NbtCompound().apply(append))

        return ServerSidePacketRegistry.INSTANCE.toPacket(TraitSpawnPacket.id, buf)
    }

    @JvmDefault
    fun lcc_readSpawnPacket(data: NbtCompound?) {

    }

    @JvmDefault
    fun lcc_raycastIgnore(caster: Entity) = false

}
