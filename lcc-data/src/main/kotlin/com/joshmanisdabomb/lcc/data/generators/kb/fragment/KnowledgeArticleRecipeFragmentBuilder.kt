package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.json.recipe.RecipeStore
import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.data.server.recipe.RecipeJsonProvider

class KnowledgeArticleRecipeFragmentBuilder(val provider: (store: RecipeStore) -> List<RecipeJsonProvider>) : KnowledgeArticleFragmentBuilder() {

    override val type = "recipe"

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val recipes = JsonArray()
        provider(exporter.da.recipeStore).forEach {
            val recipe = it.toJson()
            val tjson = JsonObject()
            val items = exporter.da.recipeStore.getItemsOf(it.recipeId)
            for (i in items) {
                val tijson = JsonObject()
                val translations = exporter.translator.getTranslations(i.translationKey)
                for ((k, v) in translations) tijson.addProperty(k, v)
                tjson.add(i.identifier.toString(), tijson)
            }
            recipe.add("translations", tjson)
            recipes.add(recipe)
        }

        val json = JsonObject()
        json.add("recipes", recipes)
        return json
    }

}
