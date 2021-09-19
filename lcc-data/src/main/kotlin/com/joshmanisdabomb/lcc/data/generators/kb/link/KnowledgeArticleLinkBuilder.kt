package com.joshmanisdabomb.lcc.data.generators.kb.link

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier

abstract class KnowledgeArticleLinkBuilder {

    abstract val type: String

    fun toJsonFinal(exporter: KnowledgeExporter) = toJson(exporter).apply { addProperty("type", type) }

    abstract fun toJson(exporter: KnowledgeExporter) : JsonObject

    companion object {
        val KnowledgeArticleIdentifier.link get(): (exporter: KnowledgeExporter) -> KnowledgeArticleLinkBuilder = { it.linker.generateLink(this) ?: error("No link could be generated for article identifier $this") }
    }

}