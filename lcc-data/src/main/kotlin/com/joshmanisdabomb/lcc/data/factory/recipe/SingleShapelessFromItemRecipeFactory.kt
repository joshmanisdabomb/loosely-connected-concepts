package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class SingleShapelessFromItemRecipeFactory(val input: ItemConvertible, val outputCount: Int = 1, val inputCount: Int = 1, val group: String? = null) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapelessRecipeJsonBuilder.create(entry, outputCount)
            .input(input, inputCount)
            .group(group)
            .apply { hasCriterionShapeless(this, input) }
            .apply { offerShapeless(this, data) }
    }

}