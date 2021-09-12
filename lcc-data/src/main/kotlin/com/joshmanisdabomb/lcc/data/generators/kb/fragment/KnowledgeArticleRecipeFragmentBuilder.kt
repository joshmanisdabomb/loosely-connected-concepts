package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.json.recipe.RecipeStore
import net.minecraft.data.server.recipe.RecipeJsonProvider

class KnowledgeArticleRecipeFragmentBuilder(val provider: (store: RecipeStore) -> List<RecipeJsonProvider>) : KnowledgeArticleFragmentBuilder() {

    override val type = "recipe"

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val rjson = JsonArray()
        provider(exporter.da.recipeStore).forEach { rjson.add(it.toJson()) }

        val json = JsonObject()
        json.add("recipes", rjson)
        return json
    }

}
