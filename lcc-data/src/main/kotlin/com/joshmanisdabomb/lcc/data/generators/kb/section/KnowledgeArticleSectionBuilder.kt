package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.KnowledgeArticleUtils
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import kotlin.properties.Delegates

class KnowledgeArticleSectionBuilder(val name: Text, val type: String = "main") {

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()
    val fragments by lazy { list.toList() }

    var id by Delegates.notNull<Int>()

    private val translations = mutableMapOf<String, String>()
    lateinit var finalName: String

    fun translation(content: String, locale: String = "en_us"): KnowledgeArticleSectionBuilder {
        translations[locale] = content
        return this
    }

    fun addFragment(fragment: KnowledgeArticleFragmentBuilder): KnowledgeArticleSectionBuilder {
        list += fragment
        return this
    }

    fun onExport(article: KnowledgeArticleBuilder, exporter: KnowledgeExporter) {
        if (name is TranslatableText) {
            translations.forEach { (k, v) -> exporter.da.lang[k]?.set(name.key, v) }
        }
        finalName = KnowledgeArticleUtils.customTranslate(name) { exporter.da.lang["en_us"]!![it] ?: error("No English translation for article $it") }
    }

}