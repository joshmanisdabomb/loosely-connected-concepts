package com.joshmanisdabomb.lcc.abstracts.computing

import net.minecraft.item.ItemStack

interface ComputingSessionContext {

    fun setErrorCode(code: Int)

    fun getAccessibleDisks(): Set<ItemStack>

}