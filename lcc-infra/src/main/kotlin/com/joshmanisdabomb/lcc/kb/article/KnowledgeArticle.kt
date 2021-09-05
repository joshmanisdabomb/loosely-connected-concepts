package com.joshmanisdabomb.lcc.kb.article

import com.joshmanisdabomb.lcc.kb.section.KnowledgeArticleSection
import net.minecraft.text.Text

class KnowledgeArticle(val location: KnowledgeArticleIdentifier, val name: Text) {

    private val list = mutableListOf<KnowledgeArticleSection>()
    val sections by lazy { list.toList() }

}
