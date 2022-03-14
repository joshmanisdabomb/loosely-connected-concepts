package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.recipe.Ingredient
import net.minecraft.text.Text

class KnowledgeArticleRecipeFragmentBuilder(val note: KnowledgeArticleFragmentBuilder? = null, val obsolete: Boolean = false, val supplier: (exporter: KnowledgeExporter) -> List<RecipeJsonProvider>) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    private var recipes: List<RecipeJsonProvider>? = null

    init {
        note?.container = this
    }

    override val type = "recipe"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = "fragment"

    override val section get() = container.section

    override fun exporterWalked(exporter: KnowledgeExporter) = super.exporterWalked(exporter) + (note?.exporterWalked(exporter) ?: emptyList())

    override fun shouldInclude(exporter: KnowledgeExporter): Boolean {
        this.recipes = (this.recipes ?: supplier(exporter))
        return this.recipes?.isNotEmpty() == true
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val recipes = JsonArray()
        this.recipes = (this.recipes ?: supplier(exporter)).onEach {
            val recipe = it.toJson()

            val translations = exporter.da.recipes.getItemsOf(it.recipeId).associate { it.identifier.toString() to Text.Serializer.toJsonTree(it.name) }
            recipe.add("translations", exporter.da.gson.toJsonTree(translations))

            val links = exporter.da.recipes.getItemsOf(it.recipeId).associate { it.identifier.toString() to KnowledgeArticleIdentifier.ofItemConvertible(it).toString() }
            recipe.add("links", exporter.da.gson.toJsonTree(links))

            val tags = JsonObject()
            exporter.da.recipes.getTagIngredientsOf(it.recipeId).forEach {
                val items = JsonArray()
                exporter.da.recipes.getItemsInTag(it).forEach { items.add(Ingredient.ofItems().toJson()) }
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
