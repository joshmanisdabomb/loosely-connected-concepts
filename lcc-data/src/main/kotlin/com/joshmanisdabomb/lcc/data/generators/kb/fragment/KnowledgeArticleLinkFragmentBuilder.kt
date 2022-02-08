package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier

class KnowledgeArticleLinkFragmentBuilder(val to: KnowledgeArticleIdentifier) : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    override val type = "link"

    override val section get() = container.section

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()

    fun addFragment(content: KnowledgeArticleFragmentBuilder) : KnowledgeArticleLinkFragmentBuilder {
        list.add(content)
        content.container = this
        return this
    }

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = list.indexOf(fragment).toString()

    override fun exporterWalked(exporter: KnowledgeExporter) = super.exporterWalked(exporter) + list.flatMap { it.exporterWalked(exporter) }

    override fun shouldInclude(exporter: KnowledgeExporter) = list.any { it.shouldInclude(exporter) }

    override fun afterInit() = list.forEach { it.afterInit() }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val fjson = JsonArray()
        list.forEach {
            fjson.add(it.toJsonFinal(exporter))
        }
        val json = JsonObject()
        json.add("fragments", fjson)
        json.addProperty("link", exporter.normaliseLink(to).toString())
        return json
    }

}
