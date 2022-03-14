package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class Nugget9RecipeFactory(val ingot: ItemConvertible, val to: Boolean = true, val from: Boolean = true, val group: String? = null, toCriterion: (ShapedRecipeJsonBuilder.(entry: Item) -> Unit)? = null, fromCriterion: (ShapelessRecipeJsonBuilder.(entry: Item) -> Unit)? = null) : RecipeFactory {

    val toCriterion = toCriterion ?: { hasCriterionShaped(this, it) }
    val fromCriterion = fromCriterion ?: { hasCriterionShapeless(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        if (to) {
            ShapedRecipeJsonBuilder.create(ingot)
                .pattern("iii")
                .pattern("iii")
                .pattern("iii")
                .input('i', entry)
                .group(group)
                .apply { this@Nugget9RecipeFactory.toCriterion(this, entry) }.apply { offerShaped(this, data, loc(registry(ingot.asItem())) { it.plus("_from_nuggets") }) }
        }
        if (from) {
            ShapelessRecipeJsonBuilder.create(entry, 9)
                .input(ingot)
                .group(group)
                .apply { this@Nugget9RecipeFactory.fromCriterion(this, ingot.asItem()) }.apply { offerShapeless(this, data, loc(registry(entry))) }
        }
    }

}