package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.batches.LangBatch
import com.joshmanisdabomb.lcc.data.generators.kb.article.KnowledgeArticleBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import net.minecraft.data.DataCache

open class LangBatchKnowledgeExporter(val batch: LangBatch, da: DataAccessor, articles: Iterable<KnowledgeArticleBuilder>) : KnowledgeExporter(da, articles) {

    override fun run(cache: DataCache) {
        articles.forEach { a ->
            a.exporterWalked(this)
            a.translations.forEach { (k, v) -> batch[k, a.defaultTranslationKey] = v }
            a.sections.forEach { s ->
                s.exporterWalked(this)
                s.translations.forEach { (k, v) -> batch[k, s.defaultTranslationKey] = v }
                s.fragments.forEach { f ->
                    f.exporterWalked(this).forEach {
                        val text = it as? KnowledgeArticleTextFragmentBuilder
                        text?.translations?.forEach { (k, v) -> batch[k, text.defaultTranslationKey] = v }
                    }
                }
            }
        }
    }

    override fun getName() = "${da.modid} Language Batch Exporter"

}