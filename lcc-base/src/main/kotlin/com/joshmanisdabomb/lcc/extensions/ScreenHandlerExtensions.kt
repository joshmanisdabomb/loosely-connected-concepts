package com.joshmanisdabomb.lcc.extensions

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import kotlin.math.min

fun ScreenHandler.addPlayerSlots(inventory: PlayerInventory, x: Int, y: Int, adder: (slot: Slot) -> Unit) {
    addSlots(inventory, x, y, 9, 3, adder, start = 9)
    addSlots(inventory, x, y + 58, 9, 1, adder, start = 0)
}

fun ScreenHandler.addSlots(inventory: Inventory, x: Int, y: Int, w: Int, h: Int, adder: (slot: Slot) -> Unit, start: Int = 0, creator: (inventory: Inventory, index: Int, x: Int, y: Int) -> Slot = { a, b, c, d -> Slot(a, b, c, d) }) {
    for (j in 0 until h) {
        for (i in 0 until w) {
            adder(creator(inventory, start.plus(i).plus(j.times(w)), x + i.times(18), y + j.times(18)))
        }
    }
}

fun ScreenHandler.insertItemWithInventoryMaxStack(stack: ItemStack, startIndex: Int, endIndex: Int, fromLast: Boolean): Boolean {
    var bl = false
    var i: Int = startIndex
    if (fromLast) {
        i = endIndex - 1
    }

    var slot2: Slot
    var itemStack: ItemStack
    if (stack.isStackable) {
        while (!stack.isEmpty()) {
            if (fromLast) {
                if (i < startIndex) {
                    break
                }
            } else if (i >= endIndex) {
                break
            }
            slot2 = slots[i]
            itemStack = slot2.stack
            if (!itemStack.isEmpty && ItemStack.canCombine(stack, itemStack)) {
                val j: Int = itemStack.count + stack.count
                val maxCount = min(stack.maxCount, slot2.getMaxItemCount(stack))
                if (j <= maxCount) {
                    stack.count = 0
                    itemStack.count = j
                    slot2.markDirty()
                    bl = true
                } else if (itemStack.count < maxCount) {
                    stack.decrement(maxCount - itemStack.count)
                    itemStack.count = maxCount
                    slot2.markDirty()
                    bl = true
                }
            }
            if (fromLast) {
                --i
            } else {
                ++i
            }
        }
    }

    if (!stack.isEmpty) {
        if (fromLast) {
            i = endIndex - 1
        } else {
            i = startIndex
        }
        while (true) {
            if (fromLast) {
                if (i < startIndex) {
                    break
                }
            } else if (i >= endIndex) {
                break
            }
            slot2 = slots[i]
            itemStack = slot2.stack
            if (itemStack.isEmpty && slot2.canInsert(stack)) {
                if (stack.count > slot2.maxItemCount) {
                    slot2.stack = stack.split(slot2.maxItemCount)
                } else {
                    slot2.stack = stack.split(stack.count)
                }
                slot2.markDirty()
                bl = true
                break
            }
            if (fromLast) {
                --i
            } else {
                ++i
            }
        }
    }

    return bl
}