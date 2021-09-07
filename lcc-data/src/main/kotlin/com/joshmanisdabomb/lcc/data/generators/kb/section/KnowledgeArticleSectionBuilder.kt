package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.KnowledgeArticleUtils
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentContainer
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

open class KnowledgeArticleSectionBuilder(val name: Text, val type: String = "main") : KnowledgeArticleFragmentContainer {

    lateinit var article: KnowledgeArticleBuilder

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()
    val fragments by lazy { list.toList() }

    val id get() = article.getSectionId(this)

    private val translations = mutableMapOf<String, String>()
    lateinit var finalName: String

    fun translation(content: String, locale: String = "en_us"): KnowledgeArticleSectionBuilder {
        translations[locale] = content
        return this
    }

    fun addFragment(fragment: KnowledgeArticleFragmentBuilder): KnowledgeArticleSectionBuilder {
        list += fragment
        fragment.container = this
        return this
    }

    fun onExport(exporter: KnowledgeExporter) {
        if (name is TranslatableText) {
            translations.forEach { (k, v) -> exporter.da.lang[k]?.set(name.key, v) }
        }
        finalName = KnowledgeArticleUtils.customTranslate(name) { exporter.da.lang["en_us"]!![it] ?: error("No English translation for article $it") }
    }

    override val defaultTranslationKey get() = "${article.defaultTranslationKey}.${article.getTranslationKeyAppend(this)}"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = fragments.indexOf(fragment).toString()

    open fun afterInit() {
        fragments.forEach { it.afterInit() }
    }

}