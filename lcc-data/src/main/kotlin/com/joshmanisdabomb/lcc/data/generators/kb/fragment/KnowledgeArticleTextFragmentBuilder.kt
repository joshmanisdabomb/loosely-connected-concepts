package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class KnowledgeArticleTextFragmentBuilder() : KnowledgeArticleFragmentBuilder() {

    constructor(content: String) : this() {
        translation(content)
    }

    override val type = "text"

    private val translations = mutableMapOf<String, String>()
    private val inserts = mutableListOf<Text>()
    private val extra = mutableListOf<JsonObject>()

    fun insert(text: Text, extra: JsonObject = JsonObject()) : KnowledgeArticleTextFragmentBuilder {
        this.inserts += text
        this.extra += extra
        return this
    }

    fun insertLink(text: Text, link: KnowledgeArticleIdentifier, extra: JsonObject = JsonObject().apply { addProperty("link", link.toString()) }) = insert(text, extra)

    fun translation(content: String, locale: String = "en_us") : KnowledgeArticleTextFragmentBuilder {
        translations[locale] = content
        return this
    }

    override fun onExport(exporter: KnowledgeExporter) {
        val key = defaultTranslationKey
        translations.forEach { (k, v) -> exporter.da.lang[k]?.set(key, v) }
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val key = defaultTranslationKey

        val json = JsonObject()
        json.add("content", Text.Serializer.toJsonTree(TranslatableText(key)))

        val inserts = JsonArray()
        this.inserts.forEachIndexed { k, v ->
            val extra = extra[k]
            inserts.add(Text.Serializer.toJsonTree(v).asJsonObject.apply {
                extra.entrySet().forEach { (k, v) -> this.add(k, v) }
            })
        }
        json.add("inserts", inserts)

        if (exporter.hasTranslations()) {
            val t = JsonObject()
            exporter.getTranslations(key, *this.inserts.filterIsInstance<TranslatableText>().map(TranslatableText::getKey).toTypedArray()).forEach { (k, v) ->
                val tl = JsonObject()
                v.forEach { (k2, v2) ->
                    tl.addProperty(k2, v2)
                }
                t.add(k, tl)
            }

            json.add("translations", t)
        }

        return json
    }

}
