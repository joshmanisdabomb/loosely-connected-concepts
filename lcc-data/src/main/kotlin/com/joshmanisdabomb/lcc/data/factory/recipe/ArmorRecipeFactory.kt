package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class ArmorRecipeFactory(val ingot: ItemConvertible, criterion: (ShapedRecipeJsonBuilder.(entry: Item) -> Unit)? = null, val type: String? = null) : RecipeFactory {

    val criterion = criterion ?: { hasCriterionShaped(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val path = assetPath(entry)
        when (type ?: path.split('_').last()) {
            "helmet" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("iii")
                .pattern("i i")
            "chestplate" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("i i")
                .pattern("iii")
                .pattern("iii")
            "leggings" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("iii")
                .pattern("i i")
                .pattern("i i")
            "boots" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("i i")
                .pattern("i i")
            else -> error("Could not determine armor type from path.")
        }
        .input('i', ingot)
        .apply { this@ArmorRecipeFactory.criterion(this, ingot.asItem()) }.apply { offerShaped(this, data, loc(registry(entry))) }
    }

}
