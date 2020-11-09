package com.joshmanisdabomb.creativeex

import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class CreativeExSet(val name: String, sortValue: Int) : CreativeExStackDisplay(sortValue) {

    val stacks = mutableListOf<ItemStack>()

    override fun appendStacks(stacks: DefaultedList<ItemStack>) {
        stacks.addAll(stacks)
    }

    fun add(stack: ItemStack) {
        stacks.add(stack)
    }

}