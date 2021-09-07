package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTableFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.knowledge.LCCVersion
import net.minecraft.text.LiteralText
import net.minecraft.text.Text

class KnowledgeArticleVersionChangelogSectionBuilder(val version: LCCVersion, name: Text = LiteralText("Full Changelog")) : KnowledgeArticleSectionBuilder(name) {

    override fun afterInit() {
        val f = KnowledgeArticleTableFragmentBuilder()
        val changelog = version.changelog
        if (changelog.isNotEmpty()) {
            changelog.forEach { (article, fragment) ->
                f.addRow {
                    addCell(KnowledgeArticleTextFragmentBuilder("%s").insertLink(article.name, article.location))
                    addCell(fragment)
                }
            }
            addFragment(f)
        }
        return super.afterInit()
    }

}