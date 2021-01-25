package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.block.entity.ClassicCryingObsidianBlockEntity
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketConsumer
import net.minecraft.block.Blocks
import net.minecraft.client.MinecraftClient
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

object LCCPacketsToClient : PacketDirectory() {

    override val _registry = ClientSidePacketRegistry.INSTANCE

    override fun id(path: String) = LCC.id(path)

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

    val classic_crying_obsidian_update by create { PacketConsumer { context, data ->
        val pos = data.readBlockPos()
        val activate = data.readBoolean()
        context.taskQueue.execute {
            val world = MinecraftClient.getInstance()?.world ?: return@execute
            val player = MinecraftClient.getInstance()?.player ?: return@execute
            if (!world.isRegionLoaded(pos, pos)) return@execute
            val be = world.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity ?: return@execute
            if (activate) be.register(player, Vec3d.ZERO) else be.deregister(player)
            world.scheduleBlockRerenderIfNeeded(pos, Blocks.AIR.defaultState, LCCBlocks.classic_crying_obsidian.defaultState)
        }
    } }

}