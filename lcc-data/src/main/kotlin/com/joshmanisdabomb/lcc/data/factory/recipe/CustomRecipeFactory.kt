package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class CustomRecipeFactory(val name: Identifier? = null, val provider: CustomRecipeFactory.(data: DataAccessor, entry: Item) -> Unit) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) = provider(data, entry)

}