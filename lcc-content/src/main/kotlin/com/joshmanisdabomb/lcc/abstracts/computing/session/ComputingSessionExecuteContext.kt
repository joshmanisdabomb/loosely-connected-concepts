package com.joshmanisdabomb.lcc.abstracts.computing.session

import com.joshmanisdabomb.lcc.abstracts.computing.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.DiskPartition
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement
import net.minecraft.world.World
import java.util.*

interface ComputingSessionExecuteContext {

    fun getSession(): ComputingSession?

    fun getWorldFromContext(): World

    fun setErrorCode(code: Int)

    fun getAccessibleDisks(): Set<DiskInfo>

    fun findPartition(partition: UUID, disks: Set<DiskInfo> = getAccessibleDisks()): DiskPartition? {
        return disks.firstNotNullOfOrNull { it.partitions.firstOrNull { it.id == partition } }
    }

}