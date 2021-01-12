package com.joshmanisdabomb.lcc.inventory

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.block.entity.RefiningBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeFinder
import net.minecraft.recipe.RecipeInputProvider
import net.minecraft.recipe.RecipeUnlocker

class RefiningInventory(private val block: RefiningBlock) : DefaultInventory(block.inputWidth.times(block.inputHeight) + block.outputSlotCount + block.fuelSlotCount), RecipeInputProvider, RecipeUnlocker {

    val width get() = block.inputWidth
    val height get() = block.inputHeight
    val outputs get() = block.outputSlotCount
    val fuels get() = block.fuelSlotCount

    override fun isValid(slot: Int, stack: ItemStack): Boolean {
        if (slot >= block.run { slotCount.minus(fuelSlotCount) }) return RefiningBlockEntity.isValidFuel(stack)
        if (slot >= block.run { slotCount.minus(fuelSlotCount).minus(outputSlotCount) }) return false
        return true
    }

    override fun setLastRecipe(recipe: Recipe<*>?) = Unit

    override fun getLastRecipe() = null

    override fun unlockLastRecipe(player: PlayerEntity) = Unit

    override fun provideRecipeInputs(finder: RecipeFinder) {
        for (i in 0..width*height) {
            finder.addItem(inventory[i])
        }
    }

}