package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleParagraphFragmentBuilder

object KnowledgeExtensions {

    fun KnowledgeArticleParagraphFragmentBuilder.addLink(version: LCCVersion) : KnowledgeArticleParagraphFragmentBuilder {
        addLink(version.page, version.label)
        return this
    }

}