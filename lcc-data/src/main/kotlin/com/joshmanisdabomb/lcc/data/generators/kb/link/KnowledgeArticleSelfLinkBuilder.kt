package com.joshmanisdabomb.lcc.data.generators.kb.link

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier

class KnowledgeArticleSelfLinkBuilder(val to: KnowledgeArticleIdentifier) : KnowledgeArticleLinkBuilder() {

    override val type = "self"

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()
        json.addProperty("to", exporter.linker.resolve(to).toString())
        return json
    }

}