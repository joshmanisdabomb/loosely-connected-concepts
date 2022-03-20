package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable

class KnowledgeArticleLootFragmentBuilder(val supplier: (exporter: KnowledgeExporter) -> List<LootTable.Builder>) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    private var tables: List<LootTable.Builder>? = null

    private var note: KnowledgeArticleFragmentBuilder? = null
    private var obsolete = false

    override val type = "loot"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = "fragment"

    override val section get() = container.section

    override fun exporterWalked(exporter: KnowledgeExporter) = super.exporterWalked(exporter) + (note?.exporterWalked(exporter) ?: emptyList())

    override fun shouldInclude(exporter: KnowledgeExporter): Boolean {
        this.tables = (this.tables ?: supplier(exporter))
        return this.tables?.isNotEmpty() == true
    }

    fun markObsolete(): KnowledgeArticleLootFragmentBuilder {
        obsolete = true
        return this
    }

    fun setNote(note: KnowledgeArticleFragmentBuilder): KnowledgeArticleLootFragmentBuilder {
        note.container = this
        this.note = note
        return this
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val tables = JsonArray()
        this.tables = (this.tables ?: supplier(exporter)).onEach {
            val id = exporter.da.lootTables[it]!!
            val table = exporter.da.lootTables.getTable(id)!!
            val json = LootManager.toJson(table).asJsonObject

            val items = exporter.da.lootTables.getItemsOf(id)

            if (!json.has("translations")) json.add("translations", KnowledgeArticleRecipeFragmentBuilder.getTranslationTree(exporter, *items.toTypedArray()))
            if (!json.has("links")) json.add("links", KnowledgeArticleRecipeFragmentBuilder.getLinkTree(exporter, *items.toTypedArray()))

            json.addProperty("id", id.toString())

            tables.add(json)
        }

        val json = JsonObject()
        json.add("tables", tables)
        note?.also { json.add("note", it.toJsonFinal(exporter)) }
        if (obsolete) json.addProperty("obsolete", obsolete)
        return json
    }

}