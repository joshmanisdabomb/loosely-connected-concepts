package com.joshmanisdabomb.lcc.data.json.recipe

import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.util.Identifier
import java.util.function.Consumer

interface JsonFactoryAccess {

    fun offerTo(exporter: Consumer<RecipeJsonProvider>)

    fun offerTo(exporter: Consumer<RecipeJsonProvider>, recipeId: Identifier)

    fun criterion(criterionName: String, conditions: CriterionConditions)

}