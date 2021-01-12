package com.joshmanisdabomb.lcc.inventory

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.block.entity.RefiningBlockEntity
import net.minecraft.item.ItemStack

class RefiningInventory(private val block: RefiningBlock) : DefaultInventory(block.inputWidth.times(block.inputHeight) + block.outputSlotCount + block.fuelSlotCount) {

    val width get() = block.inputWidth
    val height get() = block.inputHeight
    val outputs get() = block.outputSlotCount
    val fuels get() = block.fuelSlotCount

    override fun isValid(slot: Int, stack: ItemStack): Boolean {
        if (slot >= block.run { slotCount.minus(fuelSlotCount) }) return RefiningBlockEntity.isValidFuel(stack)
        if (slot >= block.run { slotCount.minus(fuelSlotCount).minus(outputSlotCount) }) return false
        return true
    }

}