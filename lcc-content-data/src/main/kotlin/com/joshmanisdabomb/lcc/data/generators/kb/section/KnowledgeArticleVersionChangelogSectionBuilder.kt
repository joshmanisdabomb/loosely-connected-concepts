package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleLinkFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion

class KnowledgeArticleVersionChangelogSectionBuilder(val version: LCCVersion) : KnowledgeArticleSectionBuilder(KnowledgeConstants.versionChangelog) {

    override fun afterInit() {
        val f = KnowledgeArticleTableFragmentBuilder()
        val changelog = version.changelog
        if (changelog.isNotEmpty()) {
            changelog.forEach { (article, fragment) ->
                f.addRow {
                    addCell(KnowledgeArticleLinkFragmentBuilder(article.location).addFragment(KnowledgeArticleTextFragmentBuilder(article.name)))
                    addCell(fragment)
                }
            }
            addFragment(f)
        }
        return super.afterInit()
    }

}