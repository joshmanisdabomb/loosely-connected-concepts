package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeMatcher
import net.minecraft.screen.AbstractRecipeScreenHandler
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

abstract class RefiningScreenHandler(type: ScreenHandlerType<out ScreenHandler>, syncId: Int, protected val playerInventory: PlayerInventory, val inventory: RefiningInventory, val properties: PropertyDelegate) : AbstractRecipeScreenHandler<RefiningInventory>(type, syncId) {

    abstract val block: RefiningBlock

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

    override fun onContentChanged(inventory: Inventory) {
        super.onContentChanged(inventory)
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
            } else if (!insertItem(originalStack, inventory.width + inventory.height + inventory.outputs, inventory.size(), false) && !insertItem(originalStack, 0, inventory.width + inventory.height + inventory.outputs, false)) {
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
    fun powerAmount() = DecimalTransport.from(properties.get(0), properties.get(1))

    @Environment(EnvType.CLIENT)
    fun progressAmount() = properties.get(2)

    @Environment(EnvType.CLIENT)
    fun efficiencyAmount() = DecimalTransport.from(properties.get(3), properties.get(4))

    @Environment(EnvType.CLIENT)
    fun maxProgressAmount() = properties.get(5)

    @Environment(EnvType.CLIENT)
    fun maxEfficiencyAmount() = DecimalTransport.from(properties.get(6), properties.get(7))

    @Environment(EnvType.CLIENT)
    fun iconIndex() = properties.get(8).run { if (this < 0) null else this }

    val currentRecipe get() = playerInventory.player.world.recipeManager.getFirstMatch(LCCRecipeTypes.refining, inventory, playerInventory.player.world)

    override fun populateRecipeFinder(finder: RecipeMatcher) {
        for (i in 0 until inventory.width * inventory.height) {
            finder.addUnenchantedInput(inventory.getStack(i))
        }
    }

    override fun clearCraftingSlots() {
        for (i in 0 until inventory.width * inventory.height) {
            inventory.setStack(i, ItemStack.EMPTY)
        }
    }

    override fun matches(recipe: Recipe<in RefiningInventory>) = recipe.matches(inventory, playerInventory.player.world)

    override fun getCraftingResultSlotIndex() = -1

    override fun getCraftingWidth() = inventory.width

    override fun getCraftingHeight() = inventory.height

    override fun getCraftingSlotCount() = inventory.size()

    override fun getCategory() = null

    override fun canInsertIntoSlot(i: Int) = i < inventory.width * inventory.height

}
