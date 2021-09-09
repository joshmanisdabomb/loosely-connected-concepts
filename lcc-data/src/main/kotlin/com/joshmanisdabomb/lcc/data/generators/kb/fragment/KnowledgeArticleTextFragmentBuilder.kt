package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.text.Text

class KnowledgeArticleTextFragmentBuilder(content: (defaultKey: String) -> Text) : KnowledgeArticleFragmentBuilder() {

    constructor(content: String, locale: String = "en_us") : this({ IncludedTranslatableText(it).translation(content, locale) })
    constructor(content: Text) : this({ content })

    override val type = "text"
    val content by lazy { content(defaultTranslationKey) }

    private val inserts = mutableListOf<(String) -> Text>()
    private val extra = mutableListOf<JsonObject>()

    fun insert(text: (defaultKey: String) -> Text, extra: JsonObject = JsonObject()) : KnowledgeArticleTextFragmentBuilder {
        this.inserts += text
        this.extra += extra
        return this
    }

    fun insert(text: Text, extra: JsonObject = JsonObject()) = insert({ text }, extra)
    fun insert(content: String, locale: String = "en_us", extra: JsonObject = JsonObject()) = insert({ IncludedTranslatableText(it).translation(content, locale) }, extra)

    fun insertLink(text: (defaultKey: String) -> Text, link: KnowledgeArticleIdentifier, extra: JsonObject = JsonObject()) = insert(text, extra.apply { addProperty("link", link.toString()) })

    fun insertLink(text: Text, link: KnowledgeArticleIdentifier, extra: JsonObject = JsonObject()) = insertLink({ text }, link, extra)
    fun insertLink(content: String, link: KnowledgeArticleIdentifier, locale: String = "en_us", extra: JsonObject = JsonObject()) = insertLink({ IncludedTranslatableText(it).translation(content, locale) }, link, extra)

    override fun onExport(exporter: KnowledgeExporter) {
        (content as? IncludedTranslatableText)?.onExport(exporter)
    }

    override fun toJson(exporter: KnowledgeExporter): JsonObject {
        val key = defaultTranslationKey

        val json = JsonObject()
        json.add("content", exporter.translator.textSerialize(content))

        val inserts = JsonArray()
        this.inserts.forEachIndexed { k, v ->
            val extra = extra[k]
            inserts.add(exporter.translator.textSerialize(v("$key.$k")).apply {
                extra.entrySet().forEach { (k, v) -> this.add(k, v) }
            })
        }
        json.add("inserts", inserts)

        return json
    }

}
