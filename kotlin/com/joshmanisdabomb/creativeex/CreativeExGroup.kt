package com.joshmanisdabomb.creativeex

import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions
import net.minecraft.block.Blocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList

abstract class CreativeExGroup(id: Identifier, index: Int = (BUILDING_BLOCKS as ItemGroupExtensions).fabric_expandArray().let { GROUPS.size - 1 }) : ItemGroup(index, "${id.namespace}.${id.path}") {

    private val display = mutableMapOf<CreativeExCategory, MutableList<CreativeExStackDisplay>>()
    private val sets = mutableMapOf<String, CreativeExSet>()

    fun addToCategory(stack: ItemStack, category: CreativeExCategory, sortValue: Int) {
        display.getOrPut(category) { mutableListOf() }.add(CreativeExItemStackDisplay(stack, sortValue))
    }

    fun addToSet(stack: ItemStack, set: String, key: CreativeExSetKey, setCategory: CreativeExCategory, setSortValue: Int) {
        sets.getOrPut(set) { CreativeExSet(set, setSortValue).apply { display.getOrPut(setCategory) { mutableListOf() }.add(this) } }.add(stack)
    }

    override fun appendStacks(stacks: DefaultedList<ItemStack>) {
        val map = display.toSortedMap(Comparator.comparingInt { it.sortValue })
        map.forEach { (k, v) -> v.sortedBy { it.sortValue }.forEach { it.appendStacks(stacks) }; newLine(stacks) }
    }

    private fun newLine(stacks: DefaultedList<ItemStack>) {
        val stacksInRow = stacks.size % 9
        if (stacksInRow == 0) return
        for (i in stacksInRow..8) stacks.add(ItemStack.EMPTY)
    }

}