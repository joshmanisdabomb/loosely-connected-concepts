package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.recipe.SpecialRecipeSerializer
import net.minecraft.util.Identifier

class ComplexRecipeFactory(val serialiser: SpecialRecipeSerializer<*>, val id: Identifier? = null) : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        ComplexRecipeJsonBuilder.create(serialiser).offerTo(data.recipes, (id ?: loc(entry)).toString())
    }

}
