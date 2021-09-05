package com.joshmanisdabomb.lcc.data.generators.kb.article

import com.joshmanisdabomb.lcc.data.generators.kb.KnowledgeArticleUtils
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class KnowledgeArticleBuilder(val location: KnowledgeArticleIdentifier, val name: Text = TranslatableText("knowledge.lcc.${location.registry}.${location.key}")) {

    constructor(block: Block, name: Text = block.name) : this(KnowledgeArticleIdentifier.ofBlock(block), name)
    constructor(item: Item, name: Text = item.name) : this(KnowledgeArticleIdentifier.ofItem(item), name)
    constructor(entity: EntityType<*>, name: Text = entity.name) : this(KnowledgeArticleIdentifier.ofEntity(entity), name)

    private val list = mutableListOf<KnowledgeArticleSectionBuilder>()
    val sections by lazy { list.toList() }

    private val translations = mutableMapOf<String, String>()
    lateinit var finalName: String

    fun translation(content: String, locale: String = "en_us") : KnowledgeArticleBuilder {
        translations[locale] = content
        return this
    }

    fun addSection(section: KnowledgeArticleSectionBuilder): KnowledgeArticleBuilder {
        list += section
        section.id = list.count()
        return this
    }

    fun onExport(exporter: KnowledgeExporter) {
        if (name is TranslatableText) {
            translations.forEach { (k, v) -> exporter.da.lang[k]?.set(name.key, v) }
        }
        val name = KnowledgeArticleUtils.customTranslate(name) { exporter.da.lang["en_us"]!![it] ?: error("No English translation for article $it") }
        finalName = name.plus(if (exporter.articles.count { it.location.registry.path == this.location.registry.path } > 1) " (${location.registry.path.split('_').joinToString(" ") { it.capitalize() }}" else "")
    }

}