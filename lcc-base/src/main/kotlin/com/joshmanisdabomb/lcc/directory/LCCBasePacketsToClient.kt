package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntity
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.minecraft.client.MinecraftClient
import net.minecraft.particle.DefaultParticleType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCBasePacketsToClient : BasicDirectory<PacketConsumer, Unit>() {

    val spawn_packet by entry(::initialiser) { PacketConsumer { context, data ->
        val entity = Registry.ENTITY_TYPE[data.readIdentifier()]
        val id = data.readInt()
        val uuid = data.readUuid()
        val x = data.readDouble()
        val y = data.readDouble()
        val z = data.readDouble()
        val pitch = data.readFloat()
        val yaw = data.readFloat()
        context.taskQueue.execute {
            val world = MinecraftClient.getInstance().world ?: return@execute
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

    val basic_particle by entry(::initialiser) { PacketConsumer { context, data ->
        val particle = Registry.PARTICLE_TYPE[data.readIdentifier()] as? DefaultParticleType ?: return@PacketConsumer
        val always = data.readBoolean()
        val x = data.readDouble()
        val y = data.readDouble()
        val z = data.readDouble()
        val dx = data.readDouble()
        val dy = data.readDouble()
        val dz = data.readDouble()
        context.taskQueue.execute {
            MinecraftClient.getInstance()?.world?.addParticle(particle, always, x, y, z, dx, dy, dz)
        }
    } }

    fun initialiser(input: PacketConsumer, context: DirectoryContext<Unit>, parameters: Unit) = input.also { ClientSidePacketRegistry.INSTANCE.register(context.id, input) }

    override fun id(name: String) = Identifier("lcc-base", name)

    override fun defaultProperties(name: String) = Unit

}