package com.joshmanisdabomb.lcc.item

import io.netty.util.internal.PlatformDependent
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

interface PlasticCraftingResult {

    @JvmDefault
    fun modifyPlasticOutputStack(stack: ItemStack, resultColor: Int) {
        stack.setSubNbt("display", NbtCompound().apply { putInt("color", resultColor) })
    }

}