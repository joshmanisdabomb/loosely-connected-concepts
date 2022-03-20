package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.CookingRecipeSerializer
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class SmeltFromItemRecipeFactory(val item: ItemConvertible, vararg val types: CookingRecipeSerializer<out AbstractCookingRecipe>, val experience: Float = 1f, val time: Int = getDefaultTime(types.first()), criterion: (CookingRecipeJsonBuilder.(entry: Item) -> Unit)? = null, val name: Identifier? = null) : RecipeFactory {

    val ingredient = Ingredient.ofItems(item)
    val criterion = criterion ?: { hasCriterionCooking(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val id = registry(entry)
        types.forEach { type -> CookingRecipeJsonBuilder.create(ingredient, entry, experience, time, type).apply { this@SmeltFromItemRecipeFactory.criterion(this, item.asItem()) }.apply { offerCooking(this, data, name ?: loc(id) { "${it}_from_${Registry.RECIPE_SERIALIZER.getId(type)!!.path}" }) } }
    }

    companion object {
        private fun getDefaultTime(type: CookingRecipeSerializer<out AbstractCookingRecipe>) = when (type) {
            RecipeSerializer.SMELTING -> 200
            RecipeSerializer.CAMPFIRE_COOKING -> 600
            else -> 100
        }
    }

}