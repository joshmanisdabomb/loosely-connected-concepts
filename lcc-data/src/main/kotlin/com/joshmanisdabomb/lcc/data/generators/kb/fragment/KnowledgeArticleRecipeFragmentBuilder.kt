package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.recipe.Ingredient
import net.minecraft.tag.TagKey
import net.minecraft.text.Text

class KnowledgeArticleRecipeFragmentBuilder(val supplier: KnowledgeArticleRecipeFragmentBuilder.(exporter: KnowledgeExporter) -> List<RecipeJsonProvider>) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    private var note: KnowledgeArticleFragmentBuilder? = null
    private var obsolete = false

    private var recipes: List<RecipeJsonProvider>? = null

    override val type = "recipe"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = "fragment"

    override val section get() = container.section

    override fun exporterWalked(exporter: KnowledgeExporter) = super.exporterWalked(exporter) + (note?.exporterWalked(exporter) ?: emptyList())

    override fun shouldInclude(exporter: KnowledgeExporter): Boolean {
        this.recipes = (this.recipes ?: supplier(exporter))
        return this.recipes?.isNotEmpty() == true
    }

    fun markObsolete(): KnowledgeArticleRecipeFragmentBuilder {
        obsolete = true
        return this
    }

    fun setNote(note: KnowledgeArticleFragmentBuilder): KnowledgeArticleRecipeFragmentBuilder {
        note.container = this
        this.note = note
        return this
    }



    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val recipes = JsonArray()
        this.recipes = (this.recipes ?: supplier(exporter)).onEach {
            val analysis = exporter.da.recipes.analyse(it)
            val json = analysis.json.deepCopy()

            if (!json.has("translations")) json.add("translations", getTranslationTree(exporter, *analysis.items.toTypedArray()))
            if (!json.has("links")) json.add("links", getLinkTree(exporter, *analysis.items.toTypedArray()))
            if (!json.has("tags")) json.add("tags", getTagTree(exporter, *analysis.inputTags.toTypedArray()))

            recipes.add(json)
        }

        val json = JsonObject()
        json.add("recipes", recipes)
        note?.also { json.add("note", it.toJsonFinal(exporter)) }
        if (obsolete) json.addProperty("obsolete", obsolete)
        return json
    }

    companion object {
        fun getTranslationTree(exporter: KnowledgeExporter, vararg items: Item) = exporter.da.gson.toJsonTree(items.associate { it.identifier.toString() to Text.Serializer.toJsonTree(it.name) })
        fun getLinkTree(exporter: KnowledgeExporter, vararg items: Item) = exporter.da.gson.toJsonTree(items.associate { it.identifier.toString() to KnowledgeArticleIdentifier.ofItemConvertible(it).toString() })
        fun getTagTree(exporter: KnowledgeExporter, vararg tags: TagKey<Item>): JsonObject {
            val json = JsonObject()
            tags.forEach {
                val items = JsonArray()
                exporter.da.recipes.getItemsInTag(it.id).forEach { items.add(Ingredient.ofItems(it).toJson()) }
                json.add(it.id.toString(), items)
            }
            return json
        }
    }

}
