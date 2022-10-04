package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleLinkFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import net.minecraft.text.Text

class KnowledgeArticleChangelogSectionBuilder() : KnowledgeArticleSectionBuilder(KnowledgeConstants.changelog) {

    override fun afterInit() {
        val f = KnowledgeArticleTableFragmentBuilder()
        val versions = LCCVersion.values().mapNotNull { it.changelog[article]?.run { it to this } }.toMap()
        if (versions.isNotEmpty()) {
            versions.forEach { (ver, fragment) ->
                f.addRow {
                    addCell(KnowledgeArticleLinkFragmentBuilder(ver.page).addFragment(KnowledgeArticleTextFragmentBuilder(Text.literal(ver.shortname))))
                    addCell(fragment)
                }
            }
            addFragment(f)
        }
        return super.afterInit()
    }

}