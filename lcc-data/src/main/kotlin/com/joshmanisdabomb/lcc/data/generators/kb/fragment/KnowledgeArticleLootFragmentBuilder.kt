package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.loot.LootManager
import net.minecraft.loot.LootTable

class KnowledgeArticleLootFragmentBuilder(val note: KnowledgeArticleFragmentBuilder? = null, val obsolete: Boolean = false, val supplier: (exporter: KnowledgeExporter) -> List<LootTable.Builder>) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    init {
        note?.container = this
    }

    override val type = "loot"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = "fragment"

    override fun onExport(exporter: KnowledgeExporter) {
        note?.onExport(exporter)
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val tables = JsonArray()
        supplier(exporter).forEach {
            val id = exporter.da.lootTableStore[it]!!
            val table = exporter.da.lootTableStore.getTable(id)!!
            val json = LootManager.toJson(table).asJsonObject

            val items = exporter.da.lootTableStore.getItemsOf(id)

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