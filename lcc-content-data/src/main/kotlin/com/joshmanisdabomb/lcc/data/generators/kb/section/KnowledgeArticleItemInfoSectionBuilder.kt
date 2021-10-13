package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.text.Text

class KnowledgeArticleItemInfoSectionBuilder(vararg models: ItemConvertible, name: (defaultKey: String) -> Text = { IncludedTranslatableText(it).translation("Information") }, type: String = "info", val renewable: Boolean = false) : KnowledgeArticleInfoSectionBuilder<Item, ItemConvertible>(*models, name = name, type = type) {

    override fun getList(provided: List<ItemConvertible>) = (if (provided.isNotEmpty()) provided.toList() else article.about.filterIsInstance<ItemConvertible>()).map(ItemConvertible::asItem)

    override fun getName(model: Item) = model.name

    override fun getStats(): MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        val map = mutableMapOf<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>()
        map[KnowledgeArticleTextFragmentBuilder("Stack Size")] = getTextStatFrom { it.maxCount.toString() }
        map[KnowledgeArticleTextFragmentBuilder("Rarity")] = getTextStatFrom { it.getRarity(it.stack()).name.toLowerCase().capitalize() }
        map[KnowledgeArticleTextFragmentBuilder("Renewable")] = listOf(KnowledgeArticleTextFragmentBuilder(renewable.transform("Yes", "No")))
        return map
    }

}