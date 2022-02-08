package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter

class KnowledgeArticleBulletedFragmentBuilder : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    override val type = "bullet"

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()

    fun add(content: KnowledgeArticleFragmentBuilder) : KnowledgeArticleBulletedFragmentBuilder {
        list.add(content)
        content.container = this
        return this
    }

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = list.indexOf(fragment).toString()

    override val section get() = container.section

    override fun shouldInclude(exporter: KnowledgeExporter) = list.isNotEmpty()

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val fjson = JsonArray()
        list.forEach {
            fjson.add(it.toJsonFinal(exporter))
        }
        val json = JsonObject()
        json.add("fragments", fjson)
        return json
    }

    override fun exporterWalked(exporter: KnowledgeExporter) = super.exporterWalked(exporter) + list.flatMap { it.exporterWalked(exporter) }

    override fun afterInit() {
        list.forEach { it.afterInit() }
    }

}
