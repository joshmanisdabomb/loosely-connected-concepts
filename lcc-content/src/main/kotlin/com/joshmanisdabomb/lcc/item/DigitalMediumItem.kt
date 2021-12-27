package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.computing.medium.DigitalMedium
import com.joshmanisdabomb.lcc.extensions.NBT_INT
import com.joshmanisdabomb.lcc.lib.item.DefaultedColoredItem
import com.joshmanisdabomb.lcc.lib.item.DefaultedDyeableItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

class DigitalMediumItem(val medium: DigitalMedium, settings: Settings) : ComputingItem(medium.initialSpace, medium.maxSpace, settings, medium.upgrader), DefaultedDyeableItem, PlasticCraftingResult {

    override fun defaultColor(stack: ItemStack) = 0xFFFFFF

    override fun modifyPlasticOutputStack(stack: ItemStack, resultColor: Int) {
        stack.setSubNbt("display", NbtCompound().apply { putInt("color2", resultColor) })
    }

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int) = when (tint) {
            1 -> {
                val display = stack.getSubNbt("display")
                if (display?.contains("color2", NBT_INT) == true) display.getInt("color2") else PlasticItem.defaultColor
            }
            2 -> DefaultedColoredItem.getTintColor(stack, 0)
            else -> -1
        }
    }

}