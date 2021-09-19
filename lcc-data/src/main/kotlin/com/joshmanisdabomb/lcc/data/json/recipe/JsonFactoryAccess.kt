package com.joshmanisdabomb.lcc.data.json.recipe

import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.util.Identifier

interface JsonFactoryAccess {

    fun offerTo(exporter: (RecipeJsonProvider) -> Unit): JsonFactoryAccess

    fun offerAs(exporter: (RecipeJsonProvider) -> Unit, recipeId: Identifier): JsonFactoryAccess

    fun criterion(criterionName: String, conditions: CriterionConditions): JsonFactoryAccess

}
