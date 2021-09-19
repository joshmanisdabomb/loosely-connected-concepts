package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import net.minecraft.data.DataProvider

abstract class KnowledgeExporter(val da: DataAccessor, val articles: Iterable<KnowledgeArticleBuilder>, val translator: KnowledgeTranslator, val linker: KnowledgeLinker) : DataProvider {

    init {
        linker.exporter = this
    }

}