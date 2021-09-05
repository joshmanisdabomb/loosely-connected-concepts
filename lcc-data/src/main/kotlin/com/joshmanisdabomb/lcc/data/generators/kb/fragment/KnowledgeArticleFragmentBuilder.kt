package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.google.gson.JsonElement
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.export.KnowledgeExporter
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder

abstract class KnowledgeArticleFragmentBuilder {

    abstract val type: String

    open fun onExport(article: KnowledgeArticleBuilder, section: KnowledgeArticleSectionBuilder, exporter: KnowledgeExporter) = Unit

    abstract fun toJson(article: KnowledgeArticleBuilder, section: KnowledgeArticleSectionBuilder, exporter: KnowledgeExporter): JsonElement

}