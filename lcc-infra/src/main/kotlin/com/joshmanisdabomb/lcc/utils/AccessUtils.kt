package com.joshmanisdabomb.lcc.utils

import net.minecraft.item.ItemStack
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.CookingRecipeSerializer
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

object AccessUtils {

    fun <T : AbstractCookingRecipe> cookingRecipeFactory(func: (id: Identifier, group: String, input: Ingredient, output: ItemStack, experience: Float, time: Int) -> T) = CookingRecipeSerializer.RecipeFactory { a, b, c, d, e, f -> func(a, b, c, d, e, f) }

}