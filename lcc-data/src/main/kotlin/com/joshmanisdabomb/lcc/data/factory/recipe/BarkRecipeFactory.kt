package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible

class BarkRecipeFactory(val item: ItemConvertible, val output: Int = 3) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ShapedRecipeJsonBuilder.create(entry, output)
            .pattern("ww")
            .pattern("ww")
            .input('w', item)
            .group("bark")
            .apply { hasCriterionShaped(this, item) }
            .apply { offerShaped(this, data) }
    }

}