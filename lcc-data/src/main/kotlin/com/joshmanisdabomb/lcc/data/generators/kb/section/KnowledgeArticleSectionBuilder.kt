package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentContainer
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleParagraphFragmentBuilder
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

open class KnowledgeArticleSectionBuilder(name: (defaultKey: String) -> Text) : KnowledgeArticleFragmentContainer {

    constructor() : this({ TranslatableText(it) })
    constructor(content: Text) : this({ content })
    constructor(content: String, locale: String = "en_us") : this(locale to content)
    constructor(vararg translations: Pair<String, String>) : this() {
        _translations += translations
    }

    var type: String = "main"

    private val _translations: MutableMap<String, String> = mutableMapOf()
    val translations by lazy { _translations.toMap() }

    lateinit var article: KnowledgeArticleBuilder
    override val section get() = this

    private val list = mutableListOf<KnowledgeArticleFragmentBuilder>()
    val fragments by lazy { list.toList() }

    val id get() = article.getSectionId(this)

    val name by lazy { name(defaultTranslationKey) }

    fun addFragment(fragment: KnowledgeArticleFragmentBuilder): KnowledgeArticleSectionBuilder {
        list += fragment
        fragment.container = this
        return this
    }

    fun addParagraph(alter: KnowledgeArticleParagraphFragmentBuilder.() -> Unit): KnowledgeArticleSectionBuilder {
        val paragraph = KnowledgeArticleParagraphFragmentBuilder()
        paragraph.alter()
        return addFragment(paragraph)
    }

    fun setLayout(type: String) {
        this.type = type
    }

    fun exporterWalked(exporter: KnowledgeExporter) {

    }

    override val defaultTranslationKey get() = "${article.defaultTranslationKey}.${article.getTranslationKeyAppend(this)}"

    override fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder) = fragments.indexOf(fragment).toString()

    open fun shouldInclude(exporter: KnowledgeExporter) = fragments.any { it.shouldInclude(exporter) }

    open fun afterInit() {
        fragments.forEach { it.afterInit() }
    }

}