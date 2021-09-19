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

    fun <T> addRows(vararg items: T, callback: Row.(obj: T) -> Unit): KnowledgeArticleTableFragmentBuilder {
        items.forEach { rows.add(Row(rows.size).apply { callback(it) }) }
        return this
    }

    override fun onExport(exporter: KnowledgeExporter) {
        for (row in rows) row.onExport(exporter)
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()
        val rjson = JsonArray()
        for (row in rows) {
            rjson.add(row.toJson(exporter))
        }
        json.add("rows", rjson)
        return json
    }

    inner class Row internal constructor(val index: Int) {

        private val cells = mutableListOf<Cell>()

        fun addCell(callback: Cell.() -> Unit): Row {
            cells.add(Cell(cells.size, false).apply(callback))
            return this
        }

        fun addCell(vararg content: KnowledgeArticleFragmentBuilder): Row {
            val cell = Cell(cells.size, false)
            for (f in content) cell.addFragment(f)
            cells.add(cell)
            return this
        }

        fun <T> addCells(vararg items: T, callback: Cell.(obj: T) -> Unit): Row {
            items.forEach { cells.add(Cell(cells.size, false).apply { callback(it) }) }
            return this
        }

        fun addHeadingCell(callback: Cell.() -> Unit): Row {
            cells.add(Cell(cells.size, true).apply(callback))
            return this
        }

        fun addHeadingCell(vararg content: KnowledgeArticleFragmentBuilder): Row {
            val cell = Cell(cells.size, true)
            for (f in content) cell.addFragment(f)
            cells.add(cell)
            return this
        }

        internal fun onExport(exporter: KnowledgeExporter) {
            for (cell in cells) cell.onExport(exporter)
        }

        internal fun toJson(exporter: KnowledgeExporter): JsonObject {
            val json = JsonObject()
            val cjson = JsonArray()
            for (cell in cells) {
                cjson.add(cell.toJson(exporter))
            }
            json.add("cells", cjson)
            return json
        }

        inner class Cell internal constructor(val index: Int, val heading: Boolean) : KnowledgeArticleFragmentContainer {

            private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()

            fun addFragment(content: KnowledgeArticleFragmentBuilder) : Cell {
                list.add(content)
                content.container = this
                return this
            }

            override val defaultTranslationKey get() = this@KnowledgeArticleTableFragmentBuilder.defaultTranslationKey.plus(".${this@Row.index}.$index")

            override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = list.indexOf(fragment).toString()

            internal fun onExport(exporter: KnowledgeExporter) {
                list.forEach { it.onExport(exporter) }
            }

            internal fun toJson(exporter: KnowledgeExporter): JsonObject {
                val fjson = JsonArray()
                list.forEach {
                    fjson.add(it.toJsonFinal(exporter))
                }
                val json = JsonObject()
                json.add("fragments", fjson)
                json.addProperty("heading", heading)
                return json
            }

        }

    }

}
