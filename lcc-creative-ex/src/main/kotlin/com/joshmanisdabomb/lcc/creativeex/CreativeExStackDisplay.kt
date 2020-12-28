package com.joshmanisdabomb.lcc.creativeex

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

abstract class CreativeExStackDisplay internal constructor(val sortValue: Int) {

    abstract fun appendStacks(stacks: DefaultedList<ItemStack>)

}

class CreativeExItemStackDisplay(val stack: ItemStack, sortValue: Int) : CreativeExStackDisplay(sortValue) {

    override fun appendStacks(stacks: DefaultedList<ItemStack>) {
        stacks.add(stack)
    }

}