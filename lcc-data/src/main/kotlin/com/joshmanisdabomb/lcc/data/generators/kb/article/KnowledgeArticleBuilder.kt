package com.joshmanisdabomb.lcc.data.generators.kb.article

import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import java.time.LocalDateTime

open class KnowledgeArticleBuilder(val location: KnowledgeArticleIdentifier, name: (defaultKey: String) -> Text) : Comparable<KnowledgeArticleBuilder> {

    constructor(location: KnowledgeArticleIdentifier, content: Text) : this(location, { content })
    constructor(location: KnowledgeArticleIdentifier, content: String, locale: String = "en_us") : this(location, locale to content)
    constructor(location: KnowledgeArticleIdentifier, vararg translations: Pair<String, String>) : this(location, { TranslatableText(it) }) {
        _translations += translations
    }

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

    var author: String? = null
    var published: LocalDateTime? = null
    var edited: LocalDateTime? = null
    var status = ArticleStatus.COMPLETE

    private val _translations: MutableMap<String, String> = mutableMapOf()
    val translations by lazy { _translations.toMap() }

    val defaultTranslationKey get() = "knowledge.lcc.${location.registry}.${location.key}"

    val name by lazy { name(defaultTranslationKey) }

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

    fun redirectsHere(block: Block): KnowledgeArticleBuilder {
        redirectsHere(KnowledgeArticleIdentifier.ofBlock(block), block.name)
        return redirectsHere(KnowledgeArticleIdentifier.ofItem(block.asItem()))
    }

    fun redirectsHere(item: Item): KnowledgeArticleBuilder {
        redirectsHere(KnowledgeArticleIdentifier.ofItem(item), item.name)
        return this
    }

    fun redirectsHere(entity: EntityType<*>): KnowledgeArticleBuilder {
        redirectsHere(KnowledgeArticleIdentifier.ofEntity(entity), entity.name)
        return this
    }

    fun tags(vararg tags: String): KnowledgeArticleBuilder {
        _tags.addAll(tags)
        return this
    }

    fun author(author: String?): KnowledgeArticleBuilder {
        this.author = author
        return this
    }

    fun status(status: ArticleStatus): KnowledgeArticleBuilder {
        this.status = status
        return this
    }

    fun published(time: LocalDateTime?): KnowledgeArticleBuilder {
        published = time
        return this
    }

    fun edited(time: LocalDateTime?): KnowledgeArticleBuilder {
        edited = time
        return this
    }

    fun meta(author: String? = null, published: LocalDateTime? = null, edited: LocalDateTime? = null): KnowledgeArticleBuilder {
        author(author)
        published(published)
        edited(edited)
        return this
    }

    fun addSection(section: KnowledgeArticleSectionBuilder): KnowledgeArticleBuilder {
        list += section
        section.article = this
        return this
    }

    fun exporterWalked(exporter: KnowledgeExporter) {

    }

    open fun afterInit() {
        sections.forEach { it.afterInit() }
    }

    override fun compareTo(other: KnowledgeArticleBuilder) = location.compareTo(other.location)

    enum class ArticleStatus {
        COMPLETE,
        INCOMPLETE,
        STUB;
    }

}