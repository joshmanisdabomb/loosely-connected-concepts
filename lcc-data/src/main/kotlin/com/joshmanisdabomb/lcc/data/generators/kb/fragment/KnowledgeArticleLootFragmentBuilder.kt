package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable

class KnowledgeArticleLootFragmentBuilder(val note: KnowledgeArticleFragmentBuilder? = null, val obsolete: Boolean = false, val supplier: (exporter: KnowledgeExporter) -> List<LootTable.Builder>) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    private var tables: List<LootTable.Builder>? = null

    init {
        note?.container = this
    }

    override val type = "loot"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = "fragment"

    override val section get() = container.section

    override fun onExport(exporter: KnowledgeExporter) {
        note?.onExport(exporter)
    }

    override fun shouldInclude(exporter: KnowledgeExporter): Boolean {
        this.tables = (this.tables ?: supplier(exporter))
        return this.tables?.isNotEmpty() == true
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val tables = JsonArray()
        this.tables = (this.tables ?: supplier(exporter)).onEach {
            val id = exporter.da.lootTables[it]!!
            val table = exporter.da.lootTables.getTable(id)!!
            val json = LootManager.toJson(table).asJsonObject

            val items = exporter.da.lootTables.getItemsOf(id)

            val tjson = exporter.translator.lootTranslationsJson(it, *items.toTypedArray())
            json.get("translations")?.asJsonObject?.entrySet()?.forEach { (k, v) -> tjson.add(k, v) }
            json.add("translations", tjson)

            val ljson = exporter.linker.lootLinksJson(it, *items.toTypedArray())
            json.get("links")?.asJsonObject?.entrySet()?.forEach { (k, v) -> ljson.add(k, v) }
            json.add("links", ljson)

            json.addProperty("id", id.toString())

            tables.add(json)
        }

        val json = JsonObject()
        json.add("tables", tables)
        if (note != null) json.add("note", note.toJsonFinal(exporter))
        if (obsolete) json.addProperty("obsolete", obsolete)
        return json
    }

}