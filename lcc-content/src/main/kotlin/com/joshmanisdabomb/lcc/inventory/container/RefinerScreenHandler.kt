package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeFinder
import net.minecraft.recipe.book.RecipeBookCategory
import net.minecraft.screen.AbstractRecipeScreenHandler
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate

class RefinerScreenHandler(syncId: Int, private val playerInventory: PlayerInventory, val inventory: DefaultInventory, val properties: PropertyDelegate)  : AbstractRecipeScreenHandler<Inventory>(LCCScreenHandlers.refiner, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, DefaultInventory(LCCBlocks.refiner.slotCount), ArrayPropertyDelegate(2))

    val listener = InventoryChangedListener(this::onContentChanged)

    init {
        checkSize(inventory, LCCBlocks.refiner.slotCount)
        checkDataCount(properties, 2)
        inventory.addListener(listener)
        inventory.onOpen(playerInventory.player)

        addSlots(inventory, 17, 17, 3, 2, ::addSlot, start = 0)
        addSlots(inventory, 107, 17, 3, 2, ::addSlot, start = 6)
        addSlots(inventory, 35, 57, 3, 1, ::addSlot, start = 12)

        addPlayerSlots(playerInventory, 8, 90, ::addSlot)

        addProperties(properties)
    }

    override fun close(player: PlayerEntity?) {
        super.close(player)
        inventory.onClose(player)
        inventory.removeListener(listener)
    }

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }

            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }

    override fun populateRecipeFinder(finder: RecipeFinder?) {
        TODO("Not yet implemented")
    }

    override fun clearCraftingSlots() {
        TODO("Not yet implemented")
    }

    override fun matches(recipe: Recipe<in Inventory>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCraftingResultSlotIndex(): Int {
        TODO("Not yet implemented")
    }

    override fun getCraftingWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun getCraftingHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun getCraftingSlotCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getCategory(): RecipeBookCategory {
        TODO("Not yet implemented")
    }

    override fun method_32339(i: Int): Boolean {
        TODO("Not yet implemented")
    }

}
