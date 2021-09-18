package com.joshmanisdabomb.lcc.data.generators.kb.export

import com.google.gson.JsonObject
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleSelfLinkBuilder
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

open class KnowledgeLinker {

    lateinit var exporter: KnowledgeExporter

    private val sources = mutableListOf<(KnowledgeArticleIdentifier) -> KnowledgeArticleLinkBuilder?>()

    fun addProvider(provider: (article: KnowledgeArticleIdentifier) -> KnowledgeArticleLinkBuilder?) : KnowledgeLinker {
        sources.add(provider)
        return this
    }

    fun addSelfProvider(modid: String) = addProvider { if (it.key.namespace == modid) KnowledgeArticleSelfLinkBuilder(it) else null }

    fun resolve(location: KnowledgeArticleIdentifier): KnowledgeArticleIdentifier {
        if (exporter.articles.any { it.location == location }) {
            return location
        }
        return exporter.articles.firstOrNull { it.redirects.contains(location) }?.location ?: location
    }

    fun generateLink(identifier: KnowledgeArticleIdentifier) : KnowledgeArticleLinkBuilder? {
        for (source in sources) {
            val link = source(identifier)
            if (link != null) return link
        }
        return null
    }

    fun itemLinksJson(vararg items: Item) : JsonObject {
        val ljson = JsonObject()
        items.forEach { i ->
            val article = exporter.articles.firstOrNull { it.about.contains(i) }?.location ?: KnowledgeArticleIdentifier.ofItem(i)
            generateLink(article)?.apply { ljson.add(i.identifier.toString(), this.toJsonFinal(exporter)) }
        }
        return ljson
    }

    fun stackLinksJson(vararg stacks: ItemStack) : JsonObject {
        return itemLinksJson(*stacks.map { it.item }.distinct().toTypedArray())
    }

}