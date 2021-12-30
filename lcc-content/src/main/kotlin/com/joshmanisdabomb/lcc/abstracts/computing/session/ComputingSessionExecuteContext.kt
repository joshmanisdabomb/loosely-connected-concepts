package com.joshmanisdabomb.lcc.abstracts.computing.session

import com.joshmanisdabomb.lcc.abstracts.computing.DiskInfo
import net.minecraft.item.ItemStack
import net.minecraft.world.World

interface ComputingSessionExecuteContext {

    fun getWorldFromContext(): World

    fun setErrorCode(code: Int)

    fun getAccessibleDisks(): Set<DiskInfo>

}