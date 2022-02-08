package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter

abstract class KnowledgeArticleFragmentBuilder {

    abstract val type: String
    lateinit var container: KnowledgeArticleFragmentContainer

    val defaultTranslationKey get() = "${container.defaultTranslationKey}.${container.getTranslationKeyAppend(this)}"

    open fun exporterWalked(exporter: KnowledgeExporter) : List<KnowledgeArticleFragmentBuilder> = listOf(this)

    protected abstract fun toJson(exporter: KnowledgeExporter): JsonObject

    fun toJsonFinal(exporter: KnowledgeExporter): JsonObject {
        val json = toJson(exporter)
        json.addProperty("fragment", type)
        return json
    }

    open fun shouldInclude(exporter: KnowledgeExporter) = true

    open fun afterInit() {

    }

}