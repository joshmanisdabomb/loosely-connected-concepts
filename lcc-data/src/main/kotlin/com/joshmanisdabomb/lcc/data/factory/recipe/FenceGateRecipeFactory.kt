package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient

class FenceGateRecipeFactory(val item: ItemConvertible, val stick: Ingredient, val output: Int = 1, val group: String? = null) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapedRecipeJsonFactory.create(entry, output)
            .pattern("sws")
            .pattern("sws")
            .input('w', item)
            .input('s', stick)
            .group(group)
            .apply { hasCriterionShaped(this, item) }
            .apply { offerShaped(this, data) }
    }

}