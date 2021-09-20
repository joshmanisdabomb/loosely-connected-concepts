package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.CraftingResultInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeMatcher
import net.minecraft.recipe.RecipeUnlocker
import net.minecraft.screen.AbstractRecipeScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import kotlin.math.min

class DungeonTableScreenHandler(syncId: Int, private val playerInventory: PlayerInventory, val inventory: LCCInventory) : AbstractRecipeScreenHandler<Inventory>(LCCScreenHandlers.spawner_table, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, LCCInventory(48))

    val result = CraftingResultInventory()
    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, 48)
        inventory.addListener(listener)
        inventory.onOpen(playerInventory.player)
        onContentChanged(inventory)

        addSlot(object : Slot(result, 0, 227, 62) {
            private var amount = 0
            private val player get() = this@DungeonTableScreenHandler.playerInventory.player
            private val input get() = this@DungeonTableScreenHandler.inventory

            override fun canInsert(stack: ItemStack) = false

            override fun takeStack(amount: Int): ItemStack {
                if (hasStack()) this.amount += min(amount, stack.count)
                return super.takeStack(amount)
            }

            override fun onCrafted(stack: ItemStack, amount: Int) {
                this.amount += amount
                this.onCrafted(stack)
            }

            override fun onTake(amount: Int) {
                this.amount += amount
            }

            override fun onCrafted(stack: ItemStack) {
                if (this.amount > 0) stack.onCraft(player.world, player, this.amount)
                if (inventory is RecipeUnlocker) inventory.unlockLastRecipe(player)
                this.amount = 0
            }

            override fun onTakeItem(player: PlayerEntity, stack: ItemStack) {
                this.onCrafted(stack)
                val defaultedList = player.world.recipeManager.getRemainingStacks(LCCRecipeTypes.spawner_table, input, player.world)
                for (i in defaultedList.indices) {
                    var itemStack: ItemStack = input.getStack(i)
                    val itemStack2 = defaultedList[i]
                    if (!itemStack.isEmpty) {
                        input.getStack(i).decrement(1)
                        input.markDirty()
                        itemStack = input.getStack(i)
                    }
                    if (!itemStack2.isEmpty) {
                        if (itemStack.isEmpty) {
                            input.setStack(i, itemStack2)
                        } else if (ItemStack.areItemsEqualIgnoreDamage(itemStack, itemStack2) && ItemStack.areNbtEqual(itemStack, itemStack2)) {
                            itemStack2.increment(itemStack.count)
                            input.setStack(i, itemStack2)
                        } else if (!player.inventory.insertStack(itemStack2)) {
                            player.dropItem(itemStack2, false)
                        }
                    }
                }
                updateResult()
            }
        })

        addSlots(inventory, 44, 18, 6, 1, ::addSlot, start = 0)
        addSlots(inventory, 26, 36, 8, 1, ::addSlot, start = 6)
        addSlots(inventory, 8, 54, 10, 1, ::addSlot, start = 14)
        addSlots(inventory, 8, 72, 10, 1, ::addSlot, start = 24)
        addSlots(inventory, 26, 90, 8, 1, ::addSlot, start = 34)
        addSlots(inventory, 44, 108, 6, 1, ::addSlot, start = 42)

        addPlayerSlots(playerInventory, 48, 140, ::addSlot)
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
        inventory.removeListener(listener)
    }

    override fun onContentChanged(inventory: Inventory) {
        super.onContentChanged(inventory)
        updateResult()
    }

    private fun updateResult() {
        val world = playerInventory.player.world
        var stack = ItemStack.EMPTY
        if (!world.isClient) {
            val serverPlayer = playerInventory.player as? ServerPlayerEntity ?: return
            val recipe = world.server?.recipeManager?.getFirstMatch(LCCRecipeTypes.spawner_table, this.inventory, world) ?: return
            recipe.ifPresent {
                if (result.shouldCraftRecipe(world, serverPlayer, it)) {
                    stack = it.craft(this.inventory)
                }
            }
            result.setStack(0, stack)
            serverPlayer.networkHandler.sendPacket(ScreenHandlerSlotUpdateS2CPacket(syncId, nextRevision(), 0, stack))
        }
    }

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index == 0) {
                if (!player.world.isClient) originalStack.item.onCraft(originalStack, player.world, player)
                if (!insertItem(originalStack, inventory.size() + result.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
                slot.onQuickTransfer(originalStack, newStack)
            } else if (index < inventory.size() + result.size()) {
                if (!insertItem(originalStack, inventory.size() + result.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, result.size(), inventory.size() + result.size(), false)) {
                return ItemStack.EMPTY
            }

            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }

            if (originalStack.count == newStack.count) return ItemStack.EMPTY

            slot.onTakeItem(player, originalStack)
            if (index == 0) player.dropItem(originalStack, false)
        }

        return newStack
    }

    override fun canInsertIntoSlot(slot: Slot) = slot.inventory != result && super.canInsertIntoSlot(slot)

    override fun populateRecipeFinder(finder: RecipeMatcher) {
        for (i in 0..inventory.size()) {
            finder.addUnenchantedInput(inventory.getStack(i))
        }
    }

    override fun clearCraftingSlots() {
        inventory.clear()
        result.clear()
    }

    override fun matches(recipe: Recipe<in Inventory>) = recipe.matches(inventory, playerInventory.player.world)

    override fun getCraftingResultSlotIndex() = 0

    override fun getCraftingWidth() = 10

    override fun getCraftingHeight() = 6

    override fun getCraftingSlotCount() = 49

    override fun getCategory() = null

    override fun canInsertIntoSlot(i: Int) = i != craftingResultSlotIndex

}
