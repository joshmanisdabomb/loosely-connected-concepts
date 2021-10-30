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
            val items = exporter.da.recipes.getItemsOf(it.recipeId)

            val tjson = exporter.translator.recipeTranslationsJson(it, *items.toTypedArray())
            recipe.get("translations")?.asJsonObject?.entrySet()?.forEach { (k, v) -> tjson.add(k, v) }
            recipe.add("translations", tjson)

            val ljson = exporter.linker.itemLinksJson(*items.toTypedArray())
            recipe.get("links")?.asJsonObject?.entrySet()?.forEach { (k, v) -> ljson.add(k, v) }
            recipe.add("links", ljson)

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
