package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.Tag

class SingleShapelessFromTagRecipeFactory(val tag: Tag<Item>, val criterion: Pair<String, CriterionConditions>, val outputCount: Int = 1, val inputCount: Int = 1) : RecipeFactory {

    val ingredient = Ingredient.fromTag(tag)

    override fun apply(data: DataAccessor, entry: Item) {
        ShapelessRecipeJsonFactory.create(entry, outputCount)
            .input(ingredient, inputCount)
            .criterion(criterion.first, criterion.second)
            .apply { offerShapeless(this, data) }
    }

}