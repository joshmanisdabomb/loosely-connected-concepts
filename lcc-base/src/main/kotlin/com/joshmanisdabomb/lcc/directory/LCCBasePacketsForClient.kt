package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.particle.DefaultParticleType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCBasePacketsForClient : PacketForClientDirectory() {

    val spawn_packet by entry(::initialiser) { ClientPlayNetworking.PlayChannelHandler { client, handler, data, sender ->
        val entity = Registry.ENTITY_TYPE[data.readIdentifier()]
        val id = data.readInt()
        val uuid = data.readUuid()
        val x = data.readDouble()
        val y = data.readDouble()
        val z = data.readDouble()
        val pitch = data.readFloat()
        val yaw = data.readFloat()
        client.execute {
            val world = client.world ?: return@execute
            val e = entity.create(world) ?: return@execute

            e.setEntityId(id)
            e.uuid = uuid
            e.updatePosition(x, y, z)
            e.updateTrackedPosition(x, y, z)
            e.pitch = pitch
            e.yaw = yaw

            world.addEntity(e.id, e)

            (e as? LCCExtendedEntity)?.lcc_readSpawnPacket(data)
        }
    } }

    val basic_particle by entry(::initialiser) { ClientPlayNetworking.PlayChannelHandler { client, handler, data, sender ->
        val particle = Registry.PARTICLE_TYPE[data.readIdentifier()] as? DefaultParticleType ?: return@PlayChannelHandler
        val always = data.readBoolean()
        val x = data.readDouble()
        val y = data.readDouble()
        val z = data.readDouble()
        val dx = data.readDouble()
        val dy = data.readDouble()
        val dz = data.readDouble()
        client.execute {
            client.world?.addParticle(particle, always, x, y, z, dx, dy, dz)
        }
    } }

    override fun id(name: String) = Identifier("lcc-base", name)

}