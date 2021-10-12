package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import net.minecraft.util.Identifier

class KnowledgeArticleQueryFragmentBuilder : KnowledgeArticleFragmentBuilder() {

    private val tagCriteria: MutableList<List<String>> = mutableListOf()
    private val registryCriteria: MutableList<List<Identifier>> = mutableListOf()

    override val type = "query"

    fun addTagCriteria(vararg or: String): KnowledgeArticleQueryFragmentBuilder {
        tagCriteria.add(or.toList())
        return this
    }

    fun addRegistryCriteria(vararg or: Identifier): KnowledgeArticleQueryFragmentBuilder {
        registryCriteria.add(or.toList())
        return this
    }

    override fun shouldInclude(exporter: KnowledgeExporter): Boolean {
        return exporter.articles.any { a ->
            (tagCriteria.isEmpty() || tagCriteria.all { it.any { a.tags.contains(it) } }) &&
            (registryCriteria.isEmpty() || registryCriteria.all { it.any { a.location.registry == it } })
        }
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()

        val tags = JsonArray()
        for (t in tagCriteria) {
            val tagOr = JsonArray()
            for (t2 in t) tagOr.add(t2)
            tags.add(tagOr)
        }
        json.add("tags", tags)

        val registries = JsonArray()
        for (r in registryCriteria) {
            val registryOr = JsonArray()
            for (r2 in r) registryOr.add(r2.toString())
            registries.add(registryOr)
        }
        json.add("registries", registries)

        return json
    }

}
