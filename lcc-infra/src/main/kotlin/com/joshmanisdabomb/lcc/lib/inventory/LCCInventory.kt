package com.joshmanisdabomb.lcc.lib.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

open class LCCInventory(size: Int) : Inventory, Iterable<ItemStack> {

    val list = DefaultedList.ofSize(size, ItemStack.EMPTY)
    private val listeners = mutableListOf<InventoryChangedListener>()
    private val segments = mutableMapOf<String, IntArray>()

    val slotInts by lazy { (0 until size()).toList().toIntArray() }

    override fun clear() = list.clear()

    override fun size() = list.size

    override fun isEmpty() = list.all(ItemStack::isEmpty)

    override fun getStack(slot: Int) = list[slot]

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val result = Inventories.splitStack(list, slot, amount)
        if (!result.isEmpty) markDirty()
        return result
    }

    override fun removeStack(slot: Int) = Inventories.removeStack(list, slot)

    override fun setStack(slot: Int, stack: ItemStack) {
        list[slot] = stack
        stack.count = stack.count.coerceAtMost(maxCountPerStack)
        markDirty()
    }

    override fun canPlayerUse(player: PlayerEntity) = true

    fun addListener(listener: InventoryChangedListener) = listeners.add(listener)

    fun removeListener(listener: InventoryChangedListener) = listeners.remove(listener)

    override fun markDirty() {
        listeners.forEach { it.onInventoryChanged(this) }
    }

    fun addSegment(key: String, slots: IntArray) = segments.put(key, slots)
    fun addSegment(key: String, slots: IntRange) = addSegment(key, slots.toList().toIntArray())
    fun addSegment(key: String, size: Int) = addSegment(key, (segments.flatMap { (_, v) -> v.toList() }.maxOrNull()?.plus(1) ?: 0).let { it until it.plus(size) })

    fun getSegmentSlots(key: String) = segments[key]
    fun getSegmentRange(key: String) = getSegmentSlots(key)?.run { sort(); firstOrNull()?.rangeTo(lastOrNull() ?: return null) }
    fun getSegmentStart(key: String) = getSegmentRange(key)?.start
    fun getSegmentEnd(key: String) = getSegmentRange(key)?.endInclusive

    fun slotsIn(segment: String): List<ItemStack>? {
        val s = segments[segment] ?: return null
        return list.filterIndexed { k, v -> s.contains(k) }
    }

    override fun iterator() = list.iterator()

    operator fun get(slot: Int) = getStack(slot)
    operator fun set(slot: Int, stack: ItemStack) = setStack(slot, stack)

}