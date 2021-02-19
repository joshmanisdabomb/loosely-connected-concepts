package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.block.entity.AtomicBombBlockEntity
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

object LCCPacketsToServer : PacketForServerDirectory() {

    val gauntlet_switch by entry(::initialiser) { ServerPlayNetworking.PlayChannelHandler { server, player, handler, data, sender ->
        val ability = data.readByte().coerceIn(0, GauntletAction.values().size.toByte())
        server.execute {
            if (player?.mainHandStack?.item == LCCItems.gauntlet) {
                player.mainHandStack.orCreateTag.putByte("lcc_gauntlet_ability", ability)
            }
        }
    } }

    val atomic_bomb_detonate by entry(::initialiser) { ServerPlayNetworking.PlayChannelHandler { server, player, handler, data, sender ->
        val dim = data.readIdentifier()
        val pos = data.readBlockPos()
        server.execute {
            val world = server.getWorld(RegistryKey.of(Registry.DIMENSION, dim)) ?: return@execute
            if (!world.isChunkLoaded(pos)) return@execute
            if ((player ?: return@execute).squaredDistanceTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) > 200f) return@execute
            (world.getBlockEntity(pos) as? AtomicBombBlockEntity)?.detonate(player)
        }
    } }

    override fun id(name: String) = LCC.id(name)

}