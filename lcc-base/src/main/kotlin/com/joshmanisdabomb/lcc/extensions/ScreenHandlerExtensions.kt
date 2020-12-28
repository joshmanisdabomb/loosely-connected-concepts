package com.joshmanisdabomb.lcc.extensions

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

fun ScreenHandler.addPlayerSlots(inventory: PlayerInventory, x: Int, y: Int, adder: (slot: Slot) -> Unit) {
    addSlots(inventory, x, y, 9, 3, adder, start = 9)
    addSlots(inventory, x, y + 58, 9, 1, adder, start = 0)
}

fun ScreenHandler.addSlots(inventory: Inventory, x: Int, y: Int, w: Int, h: Int, adder: (slot: Slot) -> Unit, start: Int = 0) {
    for (i in 0 until w) {
        for (j in 0 until h) {
            adder(Slot(inventory, start.plus(i).plus(j.times(w)), x + i.times(18), y + j.times(18)))
        }
    }
}