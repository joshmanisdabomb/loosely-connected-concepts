package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class StairsRecipeFactory(val item: ItemConvertible, val output: Int = 4, val group: String? = null) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapedRecipeJsonBuilder.create(entry, output)
            .pattern("w  ")
            .pattern("ww ")
            .pattern("www")
            .input('w', item)
            .group(group)
            .apply { hasCriterionShaped(this, item) }
            .apply { offerShaped(this, data) }
    }

}