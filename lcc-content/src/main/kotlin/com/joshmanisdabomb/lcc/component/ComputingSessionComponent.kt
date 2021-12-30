package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.block.entity.TerminalBlockEntity
import com.joshmanisdabomb.lcc.extensions.build
import com.joshmanisdabomb.lcc.inventory.container.TerminalScreenHandler
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.sync.ComponentPacketWriter
import dev.onyxstudios.cca.api.v3.component.sync.PlayerSyncPredicate
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.WorldProperties
import java.util.*

class ComputingSessionComponent(private val properties: WorldProperties) : ComponentV3, AutoSyncedComponent {

    private val sessions = mutableMapOf<UUID, ComputingSession>()

    fun startSession(id: UUID) : ComputingSession {
        val session = ComputingSession(id)
        sessions[id] = session
        return session
    }

    fun getSession(id: UUID) = sessions[id]

    fun closeSession(id: UUID) = sessions.remove(id)

    override fun readFromNbt(tag: NbtCompound) {
        tag.getCompound("Data").apply {
            keys.forEach { id ->
                try {
                    val uuid = UUID.fromString(id)
                    sessions[uuid] = ComputingSession(uuid).also { it.readNbt(getCompound(id)) }
                } catch (e: IllegalArgumentException) {
                    return@forEach
                }
            }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.build("Data", NbtCompound()) {
            sessions.forEach { (k, v) -> put(k.toString(), NbtCompound().also { v.writeNbt(it) }) }
        }
    }

    override fun shouldSyncWith(player: ServerPlayerEntity) = false

    override fun writeSyncPacket(buf: PacketByteBuf, recipient: ServerPlayerEntity) {
        buf.writeBoolean(false)
    }

    override fun applySyncPacket(buf: PacketByteBuf) {
        if (buf.readBoolean()) {
            super.applySyncPacket(buf)
        }
    }

    fun syncSingle(id: UUID?, terminal: (player: ServerPlayerEntity) -> UUID? = { getOpenTerminal(it)?.sessionAccess }) = ComponentPacketWriter { b, p ->
        val term = terminal(p)
        val tag = NbtCompound()
        tag.build("Data", NbtCompound()) {
            if (id != null) {
                val nbt = NbtCompound()
                sessions[id]?.writeNbt(nbt, term)
                nbt.remove("ServerData")
                if (term != null) {
                    nbt.remove("TerminalData")
                }
                put(id.toString(), nbt)
            }
        }
        b.writeBoolean(true)
        b.writeNbt(tag)
    }

    fun playersViewing(id: UUID) = PlayerSyncPredicate { getOpenTerminal(it)?.session == id }

    //TODO generify with interfaces
    private fun getOpenTerminal(player: ServerPlayerEntity): TerminalBlockEntity? {
        val handler = player.currentScreenHandler as? TerminalScreenHandler ?: return null
        return player.getWorld().getBlockEntity(handler.pos) as? TerminalBlockEntity
    }

}