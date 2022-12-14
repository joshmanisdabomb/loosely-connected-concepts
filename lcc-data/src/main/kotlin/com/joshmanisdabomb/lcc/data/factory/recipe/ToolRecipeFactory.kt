package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient

class ToolRecipeFactory(val ingot: ItemConvertible, val stick: Ingredient = Ingredient.ofItems(Items.STICK), criterion: (ShapedRecipeJsonBuilder.(entry: Item) -> Unit)? = null, val type: String? = null) : RecipeFactory {

    val criterion = criterion ?: { hasCriterionShaped(this, it) }

    override fun apply(data: DataAccessor, entry: Item) {
        val path = assetPath(entry)
        when (type ?: path.split('_').last()) {
            "sword" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("i")
                .pattern("i")
                .pattern("s")
            "pickaxe" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("iii")
                .pattern(" s ")
                .pattern(" s ")
            "shovel" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("i")
                .pattern("s")
                .pattern("s")
            "axe" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("ii")
                .pattern("is")
                .pattern(" s")
            "hoe" -> ShapedRecipeJsonBuilder.create(entry)
                .pattern("ii")
                .pattern(" s")
                .pattern(" s")
            else -> error("Could not determine tool type from path.")
        }
        .input('i', ingot)
        .input('s', stick)
        .apply { this@ToolRecipeFactory.criterion(this, ingot.asItem()) }.apply { offerShaped(this, data, loc(registry(entry))) }
    }

}
