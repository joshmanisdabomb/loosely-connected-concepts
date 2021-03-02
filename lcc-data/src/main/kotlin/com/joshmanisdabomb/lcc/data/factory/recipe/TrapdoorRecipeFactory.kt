package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class TrapdoorRecipeFactory(val item: ItemConvertible, val output: Int = 2) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapedRecipeJsonFactory.create(entry, output)
            .pattern("www")
            .pattern("www")
            .input('w', item)
            .apply { hasCriterionShaped(this, item) }
            .apply { offerShaped(this, data) }
    }

}