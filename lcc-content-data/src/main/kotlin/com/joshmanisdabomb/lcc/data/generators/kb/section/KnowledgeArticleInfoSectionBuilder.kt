package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleParagraphFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import net.minecraft.text.Text

abstract class KnowledgeArticleInfoSectionBuilder<T, U>(vararg models: U) : KnowledgeArticleSectionBuilder(KnowledgeConstants.information) {

    init {
        setLayout("info")
    }

    protected val items by lazy { getList(models.toList()).distinct() }
    private var alter: (MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>) -> Unit = {}

    protected abstract fun getList(provided: List<U>): List<T>

    protected abstract fun getName(model: T): Text

    protected fun <S, F : KnowledgeArticleFragmentBuilder> getStatFrom(fragment: (stat: Map<T?, S>) -> List<F>, from: (model: T) -> S?): List<F> {
        val stats = items.mapNotNull { m -> from(m)?.let { m to it } }
        val distinct = stats.map { it.second }.distinct()
        return when (distinct.size) {
            0 -> emptyList()
            1 -> fragment(mapOf(null to distinct.first<S>()))
            else -> fragment(stats.toMap<T?, S>())
        }
    }

    protected fun getTextStatFrom(from: (model: T) -> String?): List<KnowledgeArticleParagraphFragmentBuilder> {
        return getStatFrom({
            it[null]?.let { listOf(KnowledgeArticleParagraphFragmentBuilder().addText(it)) } ?: it.map { (k, v) -> KnowledgeArticleParagraphFragmentBuilder().addText(getName(k!!)).addText(": $v") }
        }, from)
    }

    protected abstract fun getStats(map: MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> = mutableMapOf()): MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>

    fun alterStats(alter: (MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>) -> Unit): KnowledgeArticleInfoSectionBuilder<T, U> {
        this.alter = alter
        return this
    }

    override fun afterInit() {
        val fragment = KnowledgeArticleTableFragmentBuilder()
        val stats = getStats().apply(alter).filterValues(List<KnowledgeArticleFragmentBuilder>::isNotEmpty)
        for ((title, fragments) in stats) {
            fragment.addRow {
                addHeadingCell(title)
                addCell(*fragments.toTypedArray())
            }
        }
        addFragment(fragment)
        return super.afterInit()
    }

}