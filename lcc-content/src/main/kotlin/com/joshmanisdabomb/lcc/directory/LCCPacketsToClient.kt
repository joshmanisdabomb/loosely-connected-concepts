package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.block.entity.ClassicCryingObsidianBlockEntity
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.block.Blocks
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

object LCCPacketsToClient : PacketForClientDirectory() {

    val soul_sand_jump_particle by entry(::initialiser) { ClientPlayNetworking.PlayChannelHandler { client, handler, data, sender ->
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
        client.execute {
            client.world?.addParticle(SoakingSoulSandJumpParticleEffect(direction, area, height, ring), always, x, y, z, dx, dy, dz)
        }
    } }

    val bounce_pad_extension by entry(::initialiser) { ClientPlayNetworking.PlayChannelHandler { client, handler, data, sender ->
        val pos = data.readBlockPos()
        client.execute {
            val world = client.world ?: return@execute
            if (!world.isRegionLoaded(pos, pos)) return@execute
            val be = world.getBlockEntity(pos) as? BouncePadBlockEntity ?: return@execute
            be.extend()
            be.effects()
        }
    } }

    val classic_crying_obsidian_update by entry(::initialiser) { ClientPlayNetworking.PlayChannelHandler { client, handler, data, sender ->
        val pos = data.readBlockPos()
        val activate = data.readBoolean()
        client.execute {
            val world = client.world ?: return@execute
            val player = client.player ?: return@execute
            if (!world.isRegionLoaded(pos, pos)) return@execute
            val be = world.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity ?: return@execute
            if (activate) be.register(player, Vec3d.ZERO) else be.deregister(player)
            world.scheduleBlockRerenderIfNeeded(pos, Blocks.AIR.defaultState, LCCBlocks.classic_crying_obsidian.defaultState)
        }
    } }

    override fun id(name: String) = LCC.id(name)

}