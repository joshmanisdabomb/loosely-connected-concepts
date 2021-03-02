package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class SingleShapelessFromItemRecipeFactory(val input: ItemConvertible, val outputCount: Int = 1, val inputCount: Int = 1) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapelessRecipeJsonFactory.create(entry, outputCount)
            .input(input, inputCount)
            .apply { hasCriterionShapeless(this, input) }
            .apply { offerShapeless(this, data) }
    }

}