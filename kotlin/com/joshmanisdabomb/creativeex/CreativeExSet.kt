package com.joshmanisdabomb.creativeex

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class CreativeExSet(val name: String, sortValue: Int) : CreativeExStackDisplay(sortValue) {

    val stacks = mutableListOf<CreativeExItemStackDisplay>()
    val open = false

    override fun appendStacks(stacks: DefaultedList<ItemStack>) {
        val list = this.stacks.sortedBy { it.sortValue }
        stacks.add(list.first().stack)
        if (open) stacks.addAll(list.map { it.stack })
    }

    fun add(stack: ItemStack, sortValue: Int) {
        stacks.add(CreativeExItemStackDisplay(stack, sortValue))
    }

}