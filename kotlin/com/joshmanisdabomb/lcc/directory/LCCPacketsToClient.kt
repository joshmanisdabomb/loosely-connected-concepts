package com.joshmanisdabomb.lcc.directory

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.minecraft.client.MinecraftClient
import net.minecraft.particle.DefaultParticleType
import net.minecraft.util.registry.Registry

object LCCPacketsToClient : PacketDirectory() {

    override val _registry = ClientSidePacketRegistry.INSTANCE

    val basic_particle by create { PacketConsumer { context, data ->
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

}