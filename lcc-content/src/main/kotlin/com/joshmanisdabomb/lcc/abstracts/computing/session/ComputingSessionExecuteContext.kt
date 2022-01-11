package com.joshmanisdabomb.lcc.abstracts.computing.session

import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.World
import java.util.*

interface ComputingSessionExecuteContext {

    fun getSession(): ComputingSession?

    fun getSessionToken(): UUID?

    fun getWorldFromContext(): World

    fun setErrorCode(code: Int)

    fun reboot()

    fun shutdown()

    fun getAccessibleDisks(): Set<DiskInfo>

    fun getAccessiblePartitions() = getAccessibleDisks().flatMap { it.partitions }.toSet()

    fun getDisk(id: UUID) = getAccessibleDisks().firstOrNull { it.id == id }

    fun getDiskWithPartition(id: UUID) = getAccessibleDisks().firstOrNull { it.partitions.any { it.id == id } }

    fun getPartition(id: UUID) = getAccessiblePartitions().firstOrNull { it.id == id }

    fun markDirty()

    fun findPartition(partition: UUID, disks: Set<DiskInfo> = getAccessibleDisks()): DiskPartition? {
        return disks.firstNotNullOfOrNull { it.partitions.firstOrNull { it.id == partition } }
    }

    fun isWatching(player: ServerPlayerEntity, view: UUID? = null): Boolean {
        val sessionId = getSessionToken() ?: return false
        val handler = player.currentScreenHandler as? ComputingSessionViewContextProvider ?: return false
        val currentView = handler.getView(player, player.getWorld())
        return currentView.getSessionToken() == sessionId && (view == null || view == currentView.getViewToken())
    }

    fun getWatching(server: MinecraftServer, view: UUID? = null) = server.playerManager.playerList.filter { isWatching(it, view) }

}