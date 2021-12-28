package com.joshmanisdabomb.lcc.component

import com.joshmanisdabomb.lcc.abstracts.computing.ComputingSession
import com.joshmanisdabomb.lcc.extensions.build
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.component.sync.ComponentPacketWriter
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

    fun syncSession(id: UUID?) = ComponentPacketWriter { b, p ->
        val tag = NbtCompound()
        tag.build("Data", NbtCompound()) {
            if (id != null) {
                put(id.toString(), NbtCompound().also { sessions[id]?.writeNbt(it) })
            }
        }
        b.writeBoolean(true)
        b.writeNbt(tag)
    }

}