package com.joshmanisdabomb.lcc.data.generators.kb.fragment

import com.joshmanisdabomb.lcc.data.generators.kb.section.KnowledgeArticleSectionBuilder

interface KnowledgeArticleFragmentContainer {

    val section: KnowledgeArticleSectionBuilder
    val defaultTranslationKey: String

    fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder): String

}
