package com.joshmanisdabomb.lcc.widens

import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.CookingRecipeSerializer
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.world.poi.PointOfInterestType

object CommonWidens {

    fun <T : AbstractCookingRecipe> cookingRecipeFactory(func: (id: Identifier, group: String, input: Ingredient, output: ItemStack, experience: Float, time: Int) -> T) = CookingRecipeSerializer.RecipeFactory { a, b, c, d, e, f -> func(a, b, c, d, e, f) }

    fun registerPointOfInterest(id: Identifier, states: Set<BlockState>, tickets: Int, distance: Int, completion: ((poi: PointOfInterestType) -> Boolean)? = null) = if (completion != null) PointOfInterestType.register(id.toString(), states, tickets, completion, distance) else PointOfInterestType.register(id.toString(), states, tickets, distance)

}