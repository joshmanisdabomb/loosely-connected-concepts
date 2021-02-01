package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.block.entity.AtomicBombBlockEntity
import net.fabricmc.fabric.api.network.PacketConsumer
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.server.MinecraftServer
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

object LCCPacketsToServer : PacketDirectory() {

    override val _registry = ServerSidePacketRegistry.INSTANCE

    override fun id(path: String) = LCC.id(path)

    val gauntlet_switch by create { PacketConsumer { context, data ->
        val ability = data.readByte().coerceIn(0, GauntletAction.values().size.toByte())
        context.taskQueue.execute {
            if (context.player?.mainHandStack?.item == LCCItems.gauntlet) {
                context.player.mainHandStack.orCreateTag.putByte("lcc_gauntlet_ability", ability)
            }
        }
    } }

    val atomic_bomb_detonate by create { PacketConsumer { context, data ->
        val dim = data.readIdentifier()
        val pos = data.readBlockPos()
        context.taskQueue.execute {
            val server = context.taskQueue as? MinecraftServer ?: return@execute
            val world = server.getWorld(RegistryKey.of(Registry.DIMENSION, dim)) ?: return@execute
            val player = context.player ?: return@execute
            if (!world.isChunkLoaded(pos)) return@execute
            if (player.squaredDistanceTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) > 200f) return@execute
            (world.getBlockEntity(pos) as? AtomicBombBlockEntity)?.detonate(player)
        }
    } }

}