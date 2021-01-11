package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.RefinerBlock
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeFinder
import net.minecraft.recipe.book.RecipeBookCategory
import net.minecraft.screen.AbstractRecipeScreenHandler
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

abstract class RefiningScreenHandler(type: ScreenHandlerType<out ScreenHandler>, syncId: Int, protected val playerInventory: PlayerInventory, val inventory: DefaultInventory, val properties: PropertyDelegate) : AbstractRecipeScreenHandler<Inventory>(type, syncId) {

    abstract val block: RefinerBlock

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, block.slotCount)
        checkDataCount(properties, block.propertyCount)

        inventory.addListener(listener)
        inventory.onOpen(playerInventory.player)

        addProperties(properties)
    }

    override fun close(player: PlayerEntity) {
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

    @Environment(EnvType.CLIENT)
    fun powerAmount() = properties.get(0).div(1000f)

    @Environment(EnvType.CLIENT)
    fun powerFill() = powerAmount().div(block.maxEnergy)

    @Environment(EnvType.CLIENT)
    fun powerString() = LooseEnergy.displayWithUnits(LooseEnergy.fromStandard(powerAmount()))

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
