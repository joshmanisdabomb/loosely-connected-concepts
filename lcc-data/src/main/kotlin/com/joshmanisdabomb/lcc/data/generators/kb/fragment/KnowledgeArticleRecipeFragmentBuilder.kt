package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.data.server.recipe.RecipeJsonProvider

class KnowledgeArticleRecipeFragmentBuilder(val supplier: (exporter: KnowledgeExporter) -> List<RecipeJsonProvider>) : KnowledgeArticleFragmentBuilder() {

    override val type = "recipe"

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val recipes = JsonArray()
        supplier(exporter).forEach {
            val recipe = it.toJson()
            val items = exporter.da.recipeStore.getItemsOf(it.recipeId)
            if (!recipe.has("translations")) recipe.add("translations", exporter.translator.itemTranslationsJson(*items.toTypedArray()))
            if (!recipe.has("links")) recipe.add("links", exporter.linker.itemLinksJson(*items.toTypedArray()))
            recipes.add(recipe)
        }

        val json = JsonObject()
        json.add("recipes", recipes)
        return json
    }

    companion object {

        fun fromOffers(getter: (exporter: KnowledgeExporter, (next: RecipeJsonProvider) -> Unit) -> Unit): KnowledgeArticleRecipeFragmentBuilder {
            return KnowledgeArticleRecipeFragmentBuilder {
                val recipes = mutableListOf<RecipeJsonProvider>()
                getter(it, recipes::add)
                recipes
            }
        }

    }

}
