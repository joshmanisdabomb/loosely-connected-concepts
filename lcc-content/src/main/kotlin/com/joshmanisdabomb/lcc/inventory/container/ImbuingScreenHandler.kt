package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.recipe.imbuing.ImbuingRecipe
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ForgingScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.slot.Slot
import net.minecraft.text.TranslatableText

class ImbuingScreenHandler(syncId: Int, playerInventory: PlayerInventory, context: ScreenHandlerContext) : ForgingScreenHandler(LCCScreenHandlers.imbuing, syncId, playerInventory, context) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, ScreenHandlerContext.EMPTY)

    private val world = playerInventory.player.world
    private var currentRecipe: ImbuingRecipe? = null

    override fun canUse(state: BlockState) = state.isOf(LCCBlocks.imbuing_press)

    override fun canTakeOutput(player: PlayerEntity, present: Boolean) = currentRecipe?.matches(input, world) == true

    override fun onTakeOutput(player: PlayerEntity, stack: ItemStack) {
        output.unlockLastRecipe(player)
        input.getStack(0).decrement(1)
        input.getStack(1).damage(1, player) {}
    }

    override fun updateResult() {
        val list = world.recipeManager.getAllMatches(LCCRecipeTypes.imbuing, input, world)
        if (list.isEmpty()) {
            output.setStack(0, ItemStack.EMPTY)
        } else {
            val first = list.first()
            currentRecipe = first
            val stack = first.craft(input)
            output.lastRecipe = currentRecipe
            output.setStack(0, stack)
        }
    }

    override fun isUsableAsAddition(stack: ItemStack) = false

    override fun canInsertIntoSlot(slot: Slot) = slot.inventory != output && super.canInsertIntoSlot(slot)

    companion object {
        val title = TranslatableText("container.lcc.imbuing")
    }

}
