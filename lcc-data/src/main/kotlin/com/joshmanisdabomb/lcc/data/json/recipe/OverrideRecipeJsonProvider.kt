package com.joshmanisdabomb.lcc.data.json.recipe

import com.google.gson.JsonObject
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier

class OverrideRecipeJsonProvider(val newSerializer: RecipeSerializer<*>, val provider: RecipeJsonProvider, val alterations: (original: JsonObject) -> Unit = {}) : RecipeJsonProvider {

    override fun serialize(json: JsonObject) {
        provider.serialize(json)
        alterations(json)
    }

    override fun getRecipeId() = provider.recipeId

    override fun getSerializer() = newSerializer

    override fun toAdvancementJson() = provider.toAdvancementJson()

    override fun getAdvancementId() = provider.advancementId

    companion object {
        fun <R> fromFactory(newSerializer: RecipeSerializer<*>, recipe: R, offer: R.((RecipeJsonProvider) -> Unit) -> Unit, alterations: (original: JsonObject) -> Unit = {}) : OverrideRecipeJsonProvider {
            var provider: RecipeJsonProvider? = null
            recipe.offer { provider = it }
            return OverrideRecipeJsonProvider(newSerializer, provider!!, alterations)
        }

        fun <R> fromFactoryWithId(newSerializer: RecipeSerializer<*>, recipe: R, name: Identifier, offerAs: R.((RecipeJsonProvider) -> Unit, Identifier) -> Unit, alterations: (original: JsonObject) -> Unit = {}) : OverrideRecipeJsonProvider {
            var provider: RecipeJsonProvider? = null
            recipe.offerAs({ provider = it }, name)
            return OverrideRecipeJsonProvider(newSerializer, provider!!, alterations)
        }
    }

}