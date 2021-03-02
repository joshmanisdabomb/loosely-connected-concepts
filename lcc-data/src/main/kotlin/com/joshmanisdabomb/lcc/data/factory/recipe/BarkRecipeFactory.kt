package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class BarkRecipeFactory(val item: ItemConvertible, val output: Int = 3) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapedRecipeJsonFactory.create(entry, output)
            .pattern("ww")
            .pattern("ww")
            .input('w', item)
            .apply { hasCriterionShaped(this, item) }
            .apply { offerShaped(this, data) }
    }

}