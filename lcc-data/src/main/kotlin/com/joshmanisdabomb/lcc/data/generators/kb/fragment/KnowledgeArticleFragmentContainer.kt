package com.joshmanisdabomb.lcc.data.generators.kb.fragment

interface KnowledgeArticleFragmentContainer {

    val defaultTranslationKey: String

    fun getTranslationKeyAppend(fragment: KnowledgeArticleFragmentBuilder): String

}
