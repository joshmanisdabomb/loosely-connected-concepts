package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier

class StonecutterItemRecipeFactory(val item: ItemConvertible, val count: Int = 1, criterion: (SingleItemRecipeJsonBuilder.(entry: Item) -> Unit)? = null, val name: Identifier? = null) : RecipeFactory {

    val ingredient = Ingredient.ofItems(item)
    val criterion = criterion ?: { hasCriterionSingle(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val id = registry(entry)
        SingleItemRecipeJsonBuilder(RecipeSerializer.STONECUTTING, ingredient, entry, count).apply { this@StonecutterItemRecipeFactory.criterion(this, item.asItem()) }.apply { offerSingle(this, data, name ?: loc(id) { "${it}_from_stonecutter" }) }
    }

}