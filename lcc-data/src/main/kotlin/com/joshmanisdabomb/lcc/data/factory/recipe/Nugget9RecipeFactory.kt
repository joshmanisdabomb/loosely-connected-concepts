package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class Nugget9RecipeFactory(val ingot: ItemConvertible, val to: Boolean = true, val from: Boolean = true, val group: String? = null, toCriterion: (ShapedRecipeJsonFactory.(entry: Item) -> Unit)? = null, fromCriterion: (ShapelessRecipeJsonFactory.(entry: Item) -> Unit)? = null) : RecipeFactory {

    val toCriterion = toCriterion ?: { hasCriterion(this, it) }
    val fromCriterion = fromCriterion ?: { hasCriterion(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        if (to) {
            ShapedRecipeJsonFactory.create(ingot)
                .pattern("iii")
                .pattern("iii")
                .pattern("iii")
                .input('i', entry)
                .group(group)
                .apply { this@Nugget9RecipeFactory.toCriterion(this, entry) }.apply { offer(this, data, loc(registry(ingot.asItem())) { it.plus("_from_nuggets") }) }
        }
        if (from) {
            ShapelessRecipeJsonFactory.create(entry, 9)
                .input(ingot)
                .group(group)
                .apply { this@Nugget9RecipeFactory.fromCriterion(this, ingot.asItem()) }.apply { offer(this, data, loc(registry(entry))) }
        }
    }

}