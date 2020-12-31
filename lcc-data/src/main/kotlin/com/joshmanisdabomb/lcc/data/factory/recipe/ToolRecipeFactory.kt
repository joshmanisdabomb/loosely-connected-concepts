package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient

class ToolRecipeFactory(val ingot: ItemConvertible, val stick: Ingredient = Ingredient.ofItems(Items.STICK), criterion: (ShapedRecipeJsonFactory.(entry: Item) -> Unit)? = null, val type: String? = null) : RecipeFactory {

    val criterion = criterion ?: { hasCriterion(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val path = assetPath(entry)
        when (type ?: path.split('_').last()) {
            "sword" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("i")
                .pattern("i")
                .pattern("s")
            "pickaxe" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("iii")
                .pattern(" s ")
                .pattern(" s ")
            "shovel" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("i")
                .pattern("s")
                .pattern("s")
            "axe" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("ii")
                .pattern("is")
                .pattern(" s")
            "hoe" -> ShapedRecipeJsonFactory.create(entry)
                .pattern("ii")
                .pattern(" s")
                .pattern(" s")
            else -> error("Could not determine tool type from path.")
        }
        .input('i', ingot)
        .input('s', stick)
        .apply { this@ToolRecipeFactory.criterion(this, ingot.asItem()) }.apply { offer(this, data, loc(registry(entry))) }
    }

}
