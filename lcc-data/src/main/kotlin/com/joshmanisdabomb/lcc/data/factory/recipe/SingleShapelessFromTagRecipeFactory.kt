package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.TagKey

class SingleShapelessFromTagRecipeFactory(val tag: TagKey<Item>, val criterion: Pair<String, CriterionConditions>, val outputCount: Int = 1, val inputCount: Int = 1, val group: String? = null) : RecipeFactory {

    val ingredient = Ingredient.fromTag(tag)

    override fun apply(data: DataAccessor, entry: Item) {
        ShapelessRecipeJsonBuilder.create(entry, outputCount)
            .input(ingredient, inputCount)
            .criterion(criterion.first, criterion.second)
            .group(group)
            .apply { offerShapeless(this, data) }
    }

}