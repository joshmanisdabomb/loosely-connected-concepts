package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier

class KnowledgeArticleListFragmentBuilder : KnowledgeArticleFragmentBuilder() {

    override val type = "list"

    private val articles: MutableList<KnowledgeArticleIdentifier> = mutableListOf()
    private val reroute: MutableList<Boolean> = mutableListOf()
    private val link: MutableList<Boolean> = mutableListOf()

    fun add(vararg articles: KnowledgeArticleIdentifier, reroute: Boolean = true, link: Boolean = true): KnowledgeArticleListFragmentBuilder {
        this.articles += articles
        this.reroute += List(articles.size) { reroute }
        this.link += List(articles.size) { link }
        return this
    }

    override fun shouldInclude(exporter: KnowledgeExporter) = articles.isNotEmpty()

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()

        val articles = JsonArray()
        for ((k, v) in this.articles.withIndex()) {
            val a = JsonObject()

            a.addProperty("location", v.toString())
            a.addProperty("reroute", reroute[k])
            a.addProperty("link", link[k])

            articles.add(a)
        }
        json.add("articles", articles)

        return json
    }

}
