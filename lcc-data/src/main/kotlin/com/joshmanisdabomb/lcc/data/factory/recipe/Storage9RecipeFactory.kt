package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class Storage9RecipeFactory(val ingot: ItemConvertible, val to: Boolean = true, val from: Boolean = true, val group: String? = null, toCriterion: (ShapedRecipeJsonBuilder.(entry: Item) -> Unit)? = null, fromCriterion: (ShapelessRecipeJsonBuilder.(entry: Item) -> Unit)? = null) : RecipeFactory {

    val toCriterion = toCriterion ?: { hasCriterionShaped(this, it) }
    val fromCriterion = fromCriterion ?: { hasCriterionShapeless(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        if (to) {
            ShapedRecipeJsonBuilder.create(entry)
                .pattern("iii")
                .pattern("iii")
                .pattern("iii")
                .input('i', ingot)
                .group(group)
                .apply { this@Storage9RecipeFactory.toCriterion(this, ingot.asItem()) }.apply { offerShaped(this, data, loc(registry(entry))) }
        }
        if (from) {
            ShapelessRecipeJsonBuilder.create(ingot, 9)
                .input(entry)
                .group(group)
                .apply { this@Storage9RecipeFactory.fromCriterion(this, entry) }.apply { offerShapeless(this, data, loc(registry(ingot.asItem())) { it.plus("_from_storage") }) }
        }
    }

}