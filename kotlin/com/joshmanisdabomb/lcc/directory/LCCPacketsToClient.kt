package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.minecraft.client.MinecraftClient
import net.minecraft.particle.DefaultParticleType
import net.minecraft.util.math.Direction
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

    val soul_sand_jump_particle by create { PacketConsumer { context, data ->
        val always = data.readBoolean()
        val x = data.readDouble()
        val y = data.readDouble()
        val z = data.readDouble()
        val dx = data.readDouble()
        val dy = data.readDouble()
        val dz = data.readDouble()
        val direction = Direction.values()[data.readByte().toInt()]
        val area = data.readFloat()
        val height = data.readFloat()
        val ring = data.readFloat()
        context.taskQueue.execute {
            MinecraftClient.getInstance()?.world?.addParticle(SoakingSoulSandJumpParticleEffect(direction, area, height, ring), always, x, y, z, dx, dy, dz)
        }
    } }

    val bounce_pad_extension by create { PacketConsumer { context, data ->
        val pos = data.readBlockPos()
        context.taskQueue.execute {
            val world = MinecraftClient.getInstance()?.world ?: return@execute
            if (!world.isRegionLoaded(pos, pos)) return@execute
            val be = world.getBlockEntity(pos) as? BouncePadBlockEntity ?: return@execute
            be.extend()
            be.effects()
        }
    } }

}