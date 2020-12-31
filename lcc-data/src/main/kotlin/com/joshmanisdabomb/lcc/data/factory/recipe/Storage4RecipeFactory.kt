package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class Storage4RecipeFactory(val ingot: ItemConvertible, val to: Boolean = true, val from: Boolean = true, val group: String? = null, toCriterion: (ShapedRecipeJsonFactory.(entry: Item) -> Unit)? = null, fromCriterion: (ShapelessRecipeJsonFactory.(entry: Item) -> Unit)? = null) : RecipeFactory {

    val toCriterion = toCriterion ?: { hasCriterion(this, it) }
    val fromCriterion = fromCriterion ?: { hasCriterion(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        if (to) {
            ShapedRecipeJsonFactory.create(entry)
                .pattern("ii")
                .pattern("ii")
                .input('i', ingot)
                .group(group)
                .apply { this@Storage4RecipeFactory.toCriterion(this, ingot.asItem()) }.apply { offer(this, data, loc(registry(entry))) }
        }
        if (from) {
            ShapelessRecipeJsonFactory.create(ingot, 4)
                .input(entry)
                .group(group)
                .apply { this@Storage4RecipeFactory.fromCriterion(this, entry) }.apply { offer(this, data, loc(registry(ingot.asItem())) { it.plus("_from_storage") }) }
        }
    }

}