package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import net.minecraft.data.DataProvider

abstract class KnowledgeExporter(val da: DataAccessor, val articles: Iterable<KnowledgeArticleBuilder>) : DataProvider {

    abstract fun hasTranslations() : Boolean

    fun getTranslations(vararg keys: String) = da.lang.mapValues { (_, v) -> keys.mapNotNull { k -> v[k]?.let { k to it } }.toMap() }

}
