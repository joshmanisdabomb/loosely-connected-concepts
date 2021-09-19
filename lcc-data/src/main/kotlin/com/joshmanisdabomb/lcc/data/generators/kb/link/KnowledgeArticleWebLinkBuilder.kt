package com.joshmanisdabomb.lcc.data.generators.kb.link

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter

class KnowledgeArticleWebLinkBuilder(val url: (exporter: KnowledgeExporter) -> String) : KnowledgeArticleLinkBuilder() {

    override val type = "web"

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()
        json.addProperty("url", url(exporter))
        return json
    }

}