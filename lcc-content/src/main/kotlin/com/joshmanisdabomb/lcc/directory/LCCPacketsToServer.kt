package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletAction
import com.joshmanisdabomb.lcc.abstracts.gauntlet.GauntletDirectory
import com.joshmanisdabomb.lcc.block.entity.AtomicBombBlockEntity
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import kotlin.text.Typography.half

object LCCPacketsToServer : PacketForServerDirectory() {

    val gauntlet_switch by entry(::initialiser) { ServerPlayNetworking.PlayChannelHandler { server, player, handler, data, sender ->
        val ability = GauntletDirectory.getOrNull(data.readString()) ?: return@PlayChannelHandler
        server.execute {
            if (player?.mainHandStack?.item == LCCItems.gauntlet) {
                GauntletAction.putInTag(ability, player.mainHandStack.orCreateNbt)
            }
        }
    } }

    val atomic_bomb_detonate by entry(::initialiser) { ServerPlayNetworking.PlayChannelHandler { server, player, handler, data, sender ->
        val dim = data.readIdentifier()
        val pos = data.readBlockPos()
        server.execute {
            val world = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, dim)) ?: return@execute
            if (!world.isChunkLoaded(pos)) return@execute
            if ((player ?: return@execute).squaredDistanceTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) > 200f) return@execute
            (world.getBlockEntity(pos) as? AtomicBombBlockEntity)?.detonate(player)
        }
    } }

    val nuclear_generator_activate by entry(::initialiser) { ServerPlayNetworking.PlayChannelHandler { server, player, handler, data, sender ->
        val dim = data.readIdentifier()
        val pos = data.readBlockPos()
        server.execute {
            val world = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, dim)) ?: return@execute
            if (!world.isChunkLoaded(pos)) return@execute
            if ((player ?: return@execute).squaredDistanceTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) > 200f) return@execute
            (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.activate(player)
        }
    } }

    val computer_power by entry(::initialiser) { ServerPlayNetworking.PlayChannelHandler { server, player, handler, data, sender ->
        val dim = data.readIdentifier()
        val pos = data.readBlockPos()
        val top = data.readBoolean()
        server.execute {
            val world = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, dim)) ?: return@execute
            if (!world.isChunkLoaded(pos)) return@execute
            if ((player ?: return@execute).squaredDistanceTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) > 200f) return@execute
            val half = (world.getBlockEntity(pos) as? ComputingBlockEntity)?.getHalf(top) ?: return@execute
            (half.module as? ComputerComputerModule)?.power(half, player)
        }
    } }

    override fun id(name: String) = LCC.id(name)

}