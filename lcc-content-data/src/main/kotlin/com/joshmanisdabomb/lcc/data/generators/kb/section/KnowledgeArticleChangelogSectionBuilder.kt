package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder.Companion.link
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import net.minecraft.text.LiteralText
import net.minecraft.text.Text

class KnowledgeArticleChangelogSectionBuilder(name: (defaultKey: String) -> Text = { IncludedTranslatableText(it).translation("Changelog") }) : KnowledgeArticleSectionBuilder(name) {

    override fun afterInit() {
        val f = KnowledgeArticleTableFragmentBuilder()
        val versions = LCCVersion.values().mapNotNull { it.changelog[article]?.run { it to this } }.toMap()
        if (versions.isNotEmpty()) {
            versions.forEach { (ver, fragment) ->
                f.addRow {
                    addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink(LiteralText(ver.shortname), ver.page.link))
                    addCell(fragment)
                }
            }
            addFragment(f)
        }
        return super.afterInit()
    }

}