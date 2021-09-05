package com.joshmanisdabomb.lcc.kb.section

import com.joshmanisdabomb.lcc.kb.fragment.KnowledgeArticleFragment
import net.minecraft.text.Text

class KnowledgeArticleSection(val name: Text, val type: String = "main") {

    private val list = mutableListOf<KnowledgeArticleFragment>()
    val fragments by lazy { list.toList() }

    fun addFragment(fragment: KnowledgeArticleFragment): KnowledgeArticleSection {
        list += fragment
        return this
    }

}