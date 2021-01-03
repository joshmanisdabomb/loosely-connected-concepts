package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.CookingRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.CookingRecipeSerializer
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class SmeltFromItemRecipeFactory(val item: ItemConvertible, vararg val types: CookingRecipeSerializer<out AbstractCookingRecipe>, val experience: Float = 1f, val time: Int = 100, criterion: (CookingRecipeJsonFactory.(entry: Item) -> Unit)? = null, val name: Identifier? = null) : RecipeFactory {

    val ingredient = Ingredient.ofItems(item)
    val criterion = criterion ?: { hasCriterion(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val id = registry(entry)
        types.forEach { type -> CookingRecipeJsonFactory.create(ingredient, entry, experience, time, type).apply { this@SmeltFromItemRecipeFactory.criterion(this, entry) }.apply { offer(this, data, name ?: loc(id) { "${it}_from_${Registry.RECIPE_SERIALIZER.getId(type)!!.path}" }) } }
    }

}