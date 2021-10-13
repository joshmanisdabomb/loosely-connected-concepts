package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier

class KnowledgeArticleImageFragmentBuilder() : KnowledgeArticleFragmentBuilder(), KnowledgeArticleFragmentContainer {

    override val type = "image"
    private val images = mutableMapOf<Image, KnowledgeArticleFragmentBuilder?>()

    private fun add(image: Image, caption: KnowledgeArticleFragmentBuilder? = null) : KnowledgeArticleImageFragmentBuilder {
        images[image] = caption
        caption?.container = this
        return this
    }

    fun addStatic(relativePath: String, caption: KnowledgeArticleFragmentBuilder? = null) = add(StaticImage(relativePath), caption)

    fun addArticle(article: KnowledgeArticleIdentifier, caption: KnowledgeArticleFragmentBuilder? = null) = add(ArticleImage(article), caption)

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = images.values.indexOf(fragment).toString()

    override val section get() = container.section

    override fun shouldInclude(exporter: KnowledgeExporter) = images.isNotEmpty()

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val json = JsonObject()

        val images = JsonArray()
        this.images.forEach { (k, v) ->
            val ijson = k.toJson()
            if (v != null) ijson.add("caption", v.toJsonFinal(exporter))
            images.add(ijson)
        }
        json.add("images", images)

        return json
    }

    override fun onExport(exporter: KnowledgeExporter) {
        images.values.forEach { it?.onExport(exporter) }
    }

    private abstract class Image {
        abstract fun toJson(): JsonObject
    }

    private class ArticleImage(val location: KnowledgeArticleIdentifier) : Image() {

        override fun toJson(): JsonObject {
            val json = JsonObject()
            json.addProperty("type", "article")
            json.addProperty("location", location.toString())
            return json
        }

    }

    private class StaticImage(val path: String) : Image() {

        override fun toJson(): JsonObject {
            val json = JsonObject()
            json.addProperty("type", "static")
            json.addProperty("location", path)
            return json
        }

    }

}