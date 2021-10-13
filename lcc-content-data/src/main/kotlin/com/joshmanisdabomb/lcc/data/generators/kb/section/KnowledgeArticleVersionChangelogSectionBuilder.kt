package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder.Companion.link
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import net.minecraft.text.Text

class KnowledgeArticleVersionChangelogSectionBuilder(val version: LCCVersion, name: (defaultKey: String) -> Text = { IncludedTranslatableText(it).translation("Full Changelog") }, type: String = "main") : KnowledgeArticleSectionBuilder(name, type) {

    override fun afterInit() {
        val f = KnowledgeArticleTableFragmentBuilder()
        val changelog = version.changelog
        if (changelog.isNotEmpty()) {
            changelog.forEach { (article, fragment) ->
                f.addRow {
                    addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink(article.name, article.location.link))
                    addCell(fragment)
                }
            }
            addFragment(f)
        }
        return super.afterInit()
    }

}