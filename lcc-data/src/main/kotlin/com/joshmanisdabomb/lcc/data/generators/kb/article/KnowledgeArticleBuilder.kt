package com.joshmanisdabomb.lcc.data.generators.kb.article

import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.text.Text

open class KnowledgeArticleBuilder(val location: KnowledgeArticleIdentifier, name: (defaultKey: String) -> Text) : Comparable<KnowledgeArticleBuilder> {

    constructor(location: KnowledgeArticleIdentifier, name: String, locale: String = "en_us") : this(location, { IncludedTranslatableText(it).translation(name, locale) })
    constructor(location: KnowledgeArticleIdentifier, name: Text) : this(location, { name })

    constructor(block: Block, name: Text = block.name) : this(KnowledgeArticleIdentifier.ofBlock(block), { name }) {
        about(block, block.asItem())
        redirectsHere(KnowledgeArticleIdentifier.ofItem(block.asItem()))
    }
    constructor(item: Item, name: Text = item.name) : this(KnowledgeArticleIdentifier.ofItem(item), { name }) {
        about(item)
    }
    constructor(entity: EntityType<*>, name: Text = entity.name) : this(KnowledgeArticleIdentifier.ofEntity(entity), { name }) {
        about(entity)
    }

    val defaultTranslationKey get() = "knowledge.lcc.${location.registry}.${location.key}"

    val name by lazy { name(defaultTranslationKey) }
    lateinit var finalName: String

    private val list = mutableListOf<KnowledgeArticleSectionBuilder>()
    val sections by lazy { list.toList() }

    private val _about = mutableListOf<Any>()
    val about by lazy { _about.toList() }
    private val _redirects = mutableMapOf<KnowledgeArticleIdentifier, Text?>()
    val redirects by lazy { _redirects.toMap() }
    private val _tags = mutableListOf<String>()
    val tags by lazy { _tags.toList() }

    fun getSectionId(section: KnowledgeArticleSectionBuilder) = sections.indexOf(section)

    fun getTranslationKeyAppend(section: KnowledgeArticleSectionBuilder) = getSectionId(section).toString()

    fun about(vararg objects: Any): KnowledgeArticleBuilder {
        _about.addAll(objects)
        return this
    }

    fun redirectsHere(link: KnowledgeArticleIdentifier, title: Text? = null): KnowledgeArticleBuilder {
        if (link == location) return this
        _redirects[link] = title
        return this
    }

    fun tags(vararg tags: String): KnowledgeArticleBuilder {
        _tags.addAll(tags)
        return this
    }

    fun addSection(section: KnowledgeArticleSectionBuilder): KnowledgeArticleBuilder {
        list += section
        section.article = this
        return this
    }

    fun onExport(exporter: KnowledgeExporter) {
        val name = exporter.translator.textResolve(name)
        finalName = name.plus(if (exporter.articles.count { it.location.key.path == this.location.key.path } > 1) " ${location.registry.path.split('_').joinToString(" ", " (", ")") { it.capitalize() }}" else "")
    }

    open fun afterInit() {
        sections.forEach { it.afterInit() }
    }

    override fun compareTo(other: KnowledgeArticleBuilder) = location.compareTo(other.location)

}