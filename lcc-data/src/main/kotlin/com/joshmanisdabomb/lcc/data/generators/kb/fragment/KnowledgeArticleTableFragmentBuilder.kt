package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter

class KnowledgeArticleTableFragmentBuilder : KnowledgeArticleFragmentBuilder() {

    override val type = "table"

    private val rows = mutableListOf<Row>()

    fun addRow(callback: Row.() -> Unit): KnowledgeArticleTableFragmentBuilder {
        rows.add(Row(rows.size).apply(callback))
        return this
    }

    override fun onExport(exporter: KnowledgeExporter) {
        for (row in rows) {
            row.onExport(exporter)
        }
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()
        val rjson = JsonArray()
        rows.forEach {
            rjson.add(it.toJson(exporter))
        }
        json.add("rows", rjson)
        return json
    }

    inner class Row internal constructor(val index: Int) : KnowledgeArticleFragmentContainer {

        private val cells = mutableListOf<KnowledgeArticleFragmentBuilder>()

        fun addCell(content: KnowledgeArticleFragmentBuilder) {
            cells.add(content)
            content.container = this
        }

        override val defaultTranslationKey get() = this@KnowledgeArticleTableFragmentBuilder.defaultTranslationKey.plus(".$index")

        override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = cells.indexOf(fragment).toString()

        internal fun onExport(exporter: KnowledgeExporter) {
            cells.forEach { it.onExport(exporter) }
        }

        internal fun toJson(exporter: KnowledgeExporter): JsonArray {
            val json = JsonArray()
            cells.forEach {
                json.add(it.toJsonFinal(exporter))
            }
            return json
        }

    }

}
