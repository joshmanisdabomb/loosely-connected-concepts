package com.joshmanisdabomb.lcc.inventory

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeInputProvider
import net.minecraft.recipe.RecipeMatcher
import net.minecraft.recipe.RecipeUnlocker

class RefiningInventory(private val block: RefiningBlock) : LCCInventory(block.slotCount), RecipeInputProvider, RecipeUnlocker {

    val width get() = block.inputWidth
    val height get() = block.inputHeight
    val inputs get() = block.inputSlotCount
    val outputs get() = block.outputSlotCount
    val fuels get() = block.fuelSlotCount

    init {
        addSegment("width", width)
        addSegment("height", height)
        addSegment("input", 0 until inputs)
        addSegment("output", outputs)
        addSegment("fuels", fuels)
    }

    override fun isValid(slot: Int, stack: ItemStack): Boolean {
        if (slot >= block.run { slotCount.minus(fuelSlotCount) }) return StackEnergyHandler.containsPower(stack)
        if (slot >= block.run { slotCount.minus(fuelSlotCount).minus(outputSlotCount) }) return false
        return true
    }

    override fun setLastRecipe(recipe: Recipe<*>?) = Unit

    override fun getLastRecipe() = null

    override fun unlockLastRecipe(player: PlayerEntity) = Unit

    override fun provideRecipeInputs(finder: RecipeMatcher) {
        slotsIn("input")?.forEach { finder.addInput(it) }
    }

}