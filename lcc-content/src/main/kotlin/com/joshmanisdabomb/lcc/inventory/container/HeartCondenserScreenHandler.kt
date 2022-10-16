package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.inventory.OutputSlot
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeInputProvider
import net.minecraft.recipe.RecipeMatcher
import net.minecraft.screen.AbstractRecipeScreenHandler
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.slot.Slot
import net.minecraft.util.math.BlockPos

class HeartCondenserScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory, val inventory: LCCInventory, val properties: PropertyDelegate) : AbstractRecipeScreenHandler<Inventory>(LCCScreenHandlers.heart_condenser, syncId) {

    private var _pos: BlockPos? = null
    val pos get() = _pos

    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf) : this(syncId, playerInventory, LCCInventory(3), ArrayPropertyDelegate(5)) {
        _pos = buf.readBlockPos()
    }

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, 3)
        checkDataCount(properties, 5)

        addSlot(PredicatedSlot(inventory, 0, 80, 17) { it.isIn(LCCItemTags.hearts) })
        addSlot(PredicatedSlot(inventory, 1, 58, 39) { it.isIn(LCCItemTags.heart_condenser_fuel) })
        addSlot(OutputSlot(playerInventory.player, inventory, 2, 80, 86))
        addPlayerSlots(playerInventory, 8, 116, ::addSlot)

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

    override fun populateRecipeFinder(finder: RecipeMatcher) {
        (inventory as? RecipeInputProvider)?.provideRecipeInputs(finder)
    }

    override fun clearCraftingSlots() {
        getSlot(0).stack = ItemStack.EMPTY
        getSlot(2).stack = ItemStack.EMPTY
    }

    override fun matches(recipe: Recipe<in Inventory>) = recipe.matches(inventory, playerInventory.player.world)

    override fun getCraftingResultSlotIndex() = 2
    override fun getCraftingWidth() = 1
    override fun getCraftingHeight() = 1
    override fun getCraftingSlotCount() = 3

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index == 2) {
                if (!insertItem(originalStack, 3, 39, true)) {
                    return ItemStack.EMPTY
                }
                slot.onQuickTransfer(originalStack, newStack)
            } else if (index != 1 && index != 0) {
                if (originalStack.isIn(LCCItemTags.hearts)) {
                    if (!insertItem(originalStack, 0, 1, false)) {
                        return ItemStack.EMPTY
                    }
                } else if (originalStack.isIn(LCCItemTags.heart_condenser_fuel)) {
                    if (!insertItem(originalStack, 1, 2, false)) {
                        return ItemStack.EMPTY
                    }
                } else if (index in 3..29) {
                    if (!insertItem(originalStack, 30, 39, false)) {
                        return ItemStack.EMPTY
                    }
                } else if (index in 30..38 && !insertItem(originalStack, 3, 30, false)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 3, 39, false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
            if (originalStack.count == newStack.count) {
                return ItemStack.EMPTY
            }
            slot.onTakeItem(player, originalStack)
        }
        return newStack
    }

    override fun canInsertIntoSlot(slot: Slot) = slot.index != 2 && super.canInsertIntoSlot(slot)
    override fun canInsertIntoSlot(index: Int) = index != 2

    override fun getCategory() = null

    @Environment(EnvType.CLIENT)
    fun burnAmount() = properties.get(0)

    @Environment(EnvType.CLIENT)
    fun burnMaxAmount() = properties.get(1)

    @Environment(EnvType.CLIENT)
    fun cookAmount() = properties.get(2)

    @Environment(EnvType.CLIENT)
    fun cookMaxAmount() = properties.get(3)

    @Environment(EnvType.CLIENT)
    fun healthAmount() = properties.get(4)

}
