package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.data.DataProvider

abstract class KnowledgeExporter(val da: DataAccessor, val articles: Iterable<KnowledgeArticleBuilder>) : DataProvider {

    fun walk(article: KnowledgeArticleBuilder) = Unit
    fun walk(section: KnowledgeArticleSectionBuilder) = Unit
    fun walk(fragment: KnowledgeArticleFragmentBuilder, parents: List<KnowledgeArticleFragmentBuilder>) = Unit

    fun normaliseLink(to: KnowledgeArticleIdentifier): KnowledgeArticleIdentifier {
        //TODO if necessary
        return to
    }

}