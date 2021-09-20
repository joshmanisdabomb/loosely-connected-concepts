package com.joshmanisdabomb.lcc.networking

import com.joshmanisdabomb.lcc.trait.LCCEntityTrait
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.nbt.NbtTagSizeTracker
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object TraitSpawnPacket {

    val id = Identifier("lcc-hooks", "trait_spawn")

    object Handler : ClientPlayNetworking.PlayChannelHandler {

        override fun receive(client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf, responseSender: PacketSender) {
            val entity = Registry.ENTITY_TYPE[buf.readIdentifier()]
            val id = buf.readInt()
            val uuid = buf.readUuid()
            val x = buf.readDouble()
            val y = buf.readDouble()
            val z = buf.readDouble()
            val pitch = buf.readFloat()
            val yaw = buf.readFloat()
            val extra = buf.readNbt(NbtTagSizeTracker(65536L))
            client.execute {
                val world = client.world ?: return@execute
                val e = entity.create(world) ?: return@execute

                e.id = id
                e.uuid = uuid
                e.updatePosition(x, y, z)
                e.updateTrackedPosition(x, y, z)
                e.pitch = pitch
                e.yaw = yaw

                (e as? LCCEntityTrait)?.lcc_readSpawnPacket(extra)

                world.addEntity(e.id, e)
            }
        }
    }

}