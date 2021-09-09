package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import net.minecraft.data.DataProvider
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

abstract class KnowledgeExporter(val da: DataAccessor, val articles: Iterable<KnowledgeArticleBuilder>, val translator: KnowledgeTranslator) : DataProvider {

}