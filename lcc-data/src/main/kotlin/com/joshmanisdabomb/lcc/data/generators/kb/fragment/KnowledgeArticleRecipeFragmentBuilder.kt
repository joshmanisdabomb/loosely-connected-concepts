package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.recipe.Ingredient

class KnowledgeArticleRecipeFragmentBuilder(val note: KnowledgeArticleFragmentBuilder? = null, val obsolete: Boolean = false, val supplier: (exporter: KnowledgeExporter) -> List<RecipeJsonProvider>) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    private var recipes: List<RecipeJsonProvider>? = null

    init {
        note?.container = this
    }

    override val type = "recipe"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = "fragment"

    override val section get() = container.section

    override fun onExport(exporter: KnowledgeExporter) {
        note?.onExport(exporter)
    }

    override fun shouldInclude(exporter: KnowledgeExporter): Boolean {
        this.recipes = (this.recipes ?: supplier(exporter))
        return this.recipes?.isNotEmpty() == true
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val recipes = JsonArray()
        this.recipes = (this.recipes ?: supplier(exporter)).onEach {
            val recipe = it.toJson()
            val items = exporter.da.recipes.getItemsOf(it.recipeId)

            val tjson = exporter.translator.recipeTranslationsJson(it, *items.toTypedArray())
            recipe.get("translations")?.asJsonObject?.entrySet()?.forEach { (k, v) -> tjson.add(k, v) }
            recipe.add("translations", tjson)

            val ljson = exporter.linker.recipeLinksJson(it, *items.toTypedArray())
            recipe.get("links")?.asJsonObject?.entrySet()?.forEach { (k, v) -> ljson.add(k, v) }
            recipe.add("links", ljson)

            val tags = JsonObject()
            exporter.da.recipeStore.getTagIngredientsOf(it.recipeId).forEach {
                val items = JsonArray()
                exporter.da.recipeStore.getItemsInTag(it).forEach { items.add(Ingredient.ofItems(it.asItem()).toJson()) }
                tags.add(it.toString(), items)
            }
            recipe.add("tags", tags)

            recipes.add(recipe)
        }

        val json = JsonObject()
        json.add("recipes", recipes)
        if (note != null) json.add("note", note.toJsonFinal(exporter))
        if (obsolete) json.addProperty("obsolete", obsolete)
        return json
    }

}
