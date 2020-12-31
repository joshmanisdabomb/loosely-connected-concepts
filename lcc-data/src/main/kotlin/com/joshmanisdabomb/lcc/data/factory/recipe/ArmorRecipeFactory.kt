package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class ArmorRecipeFactory(val ingot: ItemConvertible, criterion: (ShapedRecipeJsonFactory.(entry: Item) -> Unit)? = null, val type: String? = null) : RecipeFactory {

    val criterion = criterion ?: { hasCriterion(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val path = assetPath(entry)
        when (type ?: path.split('_').last()) {
            "helmet" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("iii")
                .pattern("i i")
            "chestplate" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("i i")
                .pattern("iii")
                .pattern("iii")
            "leggings" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("iii")
                .pattern("i i")
                .pattern("i i")
            "boots" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("i i")
                .pattern("i i")
            else -> error("Could not determine armor type from path.")
        }
        .input('i', ingot)
        .apply { this@ArmorRecipeFactory.criterion(this, ingot.asItem()) }.apply { offer(this, data, loc(registry(entry))) }
    }

}
