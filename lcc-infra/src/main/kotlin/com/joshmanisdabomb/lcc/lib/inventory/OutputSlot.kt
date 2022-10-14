package com.joshmanisdabomb.lcc.lib.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

open class OutputSlot(private val player: PlayerEntity, inventory: Inventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {

    private var amount = 0

    override fun canInsert(stack: ItemStack) = false

    override fun takeStack(amount: Int): ItemStack {
        if (hasStack()) {
            this.amount += Math.min(amount, this.stack.count)
        }
        return super.takeStack(amount)
    }

    override fun onTakeItem(player: PlayerEntity, stack: ItemStack) {
        this.onCrafted(stack)
        super.onTakeItem(player, stack)
    }

    override fun onCrafted(stack: ItemStack, amount: Int) {
        this.amount += amount
        this.onCrafted(stack)
    }

    override fun onCrafted(stack: ItemStack) {
        stack.onCraft(player.world, player, amount)
        amount = 0
    }

}