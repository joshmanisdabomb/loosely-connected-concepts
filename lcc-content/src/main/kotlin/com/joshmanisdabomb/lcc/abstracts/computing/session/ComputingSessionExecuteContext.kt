package com.joshmanisdabomb.lcc.abstracts.computing.session

import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
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

    fun getAccessibleDisks(): Set<StorageDisk>

    fun getAccessiblePartitions() = StorageDisk.getPartitions(getAccessibleDisks())

    fun getDisk(id: UUID) = StorageDisk.getDisk(getAccessibleDisks(), id)

    fun getDiskWithPartition(id: UUID) = StorageDisk.getDiskWithPartition(getAccessibleDisks(), id)

    fun getPartition(id: UUID) = StorageDisk.getPartition(getAccessiblePartitions(), id)

    fun markDirty()

    fun findPartition(partition: UUID, disks: Set<StorageDisk> = getAccessibleDisks()) = StorageDisk.findPartition(disks, partition)

    fun isWatching(player: ServerPlayerEntity, view: UUID? = null): Boolean {
        val sessionId = getSessionToken() ?: return false
        val handler = player.currentScreenHandler as? ComputingSessionViewContextProvider ?: return false
        val currentView = handler.getView(player, player.getWorld())
        return currentView.getSessionToken() == sessionId && (view == null || view == currentView.getViewToken())
    }

    fun getWatching(server: MinecraftServer, view: UUID? = null) = server.playerManager.playerList.filter { isWatching(it, view) }

}