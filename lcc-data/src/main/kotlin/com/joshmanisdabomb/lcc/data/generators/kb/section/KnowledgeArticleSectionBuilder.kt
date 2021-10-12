package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentContainer
import net.minecraft.text.Text

open class KnowledgeArticleSectionBuilder(name: (defaultKey: String) -> Text, val type: String = "main") : KnowledgeArticleFragmentContainer {

    constructor(name: String, type: String = "main", locale: String = "en_us") : this({ IncludedTranslatableText(it).translation(name, locale) }, type)
    constructor(name: Text, type: String = "main") : this({ name }, type)

    lateinit var article: KnowledgeArticleBuilder
    override val section get() = this

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()
    val fragments by lazy { list.toList() }

    val id get() = article.getSectionId(this)

    val name by lazy { name(defaultTranslationKey) }
    lateinit var finalName: String

    fun addFragment(fragment: KnowledgeArticleFragmentBuilder): KnowledgeArticleSectionBuilder {
        list += fragment
        fragment.container = this
        return this
    }

    fun onExport(exporter: KnowledgeExporter) {
        finalName = exporter.translator.textResolve(name)
    }

    override val defaultTranslationKey get() = "${article.defaultTranslationKey}.${article.getTranslationKeyAppend(this)}"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = fragments.indexOf(fragment).toString()

    open fun shouldInclude(exporter: KnowledgeExporter) = fragments.any { it.shouldInclude(exporter) }

    open fun afterInit() {
        fragments.forEach { it.afterInit() }
    }

}