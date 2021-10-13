package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.*
import com.joshmanisdabomb.lcc.data.generators.kb.link.KnowledgeArticleLinkBuilder.Companion.link
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.item.CrowbarItem
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import com.joshmanisdabomb.lcc.mixin.data.common.BlockStateAccessor
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.Block
import net.minecraft.block.FireBlock
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.*
import net.minecraft.text.Text
import net.minecraft.util.registry.Registry

class KnowledgeArticleBlockInfoSectionBuilder(vararg models: Block, name: (defaultKey: String) -> Text = { IncludedTranslatableText(it).translation("Information") }, type: String = "info", val renewable: Boolean = false) : KnowledgeArticleInfoSectionBuilder<Block, Block>(*models, name = name, type = type) {

    override fun getList(provided: List<Block>) = if (provided.isNotEmpty()) provided.toList() else article.about.filterIsInstance<Block>()

    override fun getName(model: Block) = model.name

    override fun getStats(): MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        val map = mutableMapOf<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>()

        map[KnowledgeArticleTextFragmentBuilder("Image")] = listOf(KnowledgeArticleImageFragmentBuilder().apply { items.forEach { addArticle(KnowledgeArticleIdentifier.ofBlock(it)) } })
        map += addToolStat()
        map[KnowledgeArticleTextFragmentBuilder("Hardness")] = getTextStatFrom { (it.defaultState as BlockStateAccessor).hardness.toString() }
        map[KnowledgeArticleTextFragmentBuilder("Blast Resistance")] = getTextStatFrom { it.blastResistance.toString() }
        map[KnowledgeArticleTextFragmentBuilder("Luminance")] = getTextStatFrom {
            val array = it.stateManager.states.map { it.luminance }.toIntArray()
            val min = array.minOrNull()!!
            val max = array.maxOrNull()!!
            if (min == max) min.toString() else "$min-$max"
        }
        map[KnowledgeArticleTextFragmentBuilder("Friction")] = getTextStatFrom { if (it.slipperiness == 0.6f) "Normal" else it.slipperiness.toString() }
        map[KnowledgeArticleTextFragmentBuilder("Flammability")] = getFlammability()
        map[KnowledgeArticleTextFragmentBuilder("Random Ticks")] = getTextStatFrom { it.stateManager.states.map { it.hasRandomTicks() }.any().transform("Yes", "No") }

        map[KnowledgeArticleTextFragmentBuilder("Stack Size")] = getTextStatFrom { it.asItem().maxCount.toString() }
        map[KnowledgeArticleTextFragmentBuilder("Rarity")] = getTextStatFrom { it.asItem().getRarity(ItemStack(it)).name.toLowerCase().capitalize() }
        map[KnowledgeArticleTextFragmentBuilder("Renewable")] = listOf(KnowledgeArticleTextFragmentBuilder(renewable.transform("Yes", "No")))

        map[KnowledgeArticleTextFragmentBuilder { IncludedTranslatableText(it).translation("Map Colors", "en_us").translation("Map Colours", "en_gb") }] = getStatFrom({ it.values.flatMap { it }.distinct().map(::KnowledgeArticleColorFragmentBuilder) }) { it.stateManager.states.map { (it as BlockStateAccessor).mapColor.color } }
        return map
    }

    private fun addToolStat() : Pair<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        for (tool in tools) {
            val name = when (tool.identifier.namespace) {
                LCC.modid -> tool.identifier.path.split("_").joinToString(" ") { it.capitalize() }
                else -> I18n.translate(tool.translationKey)
            }
            if (article.tags.contains("$name Recommended")) return KnowledgeArticleTextFragmentBuilder("Recommended Tool") to listOf(KnowledgeArticleStackFragmentBuilder(tool.stack()) { it.addProperty("tool_recommended", true) })
            else if (article.tags.contains("$name Required")) return KnowledgeArticleTextFragmentBuilder("Required Tool") to listOf(KnowledgeArticleStackFragmentBuilder(tool.stack()) { it.addProperty("tool_required", true) })
        }
        return KnowledgeArticleTextFragmentBuilder("Required Tool") to listOf(KnowledgeArticleTextFragmentBuilder("Any"))
    }

    private fun getFlammability(): List<KnowledgeArticleFragmentBuilder> {
        val fires = Registry.BLOCK.filterIsInstance<FireBlock>().filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" }
        val effective = fires.filter { items.any { b -> val entry = FlammableBlockRegistry.getInstance(it).get(b) ?: return@any false; entry.burnChance > 0 || entry.spreadChance > 0 } }
        return if (effective.isEmpty()) return listOf(KnowledgeArticleTextFragmentBuilder("No"))
        else effective.map { KnowledgeArticleTextFragmentBuilder("%s").insertLink(it.name, KnowledgeArticleIdentifier.ofBlock(it).link) }
    }

    companion object {
        private val tools by lazy { Registry.ITEM.filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" }.filter { it is SwordItem || it is PickaxeItem || it is ShovelItem || it is AxeItem || it is HoeItem || it is ShearsItem || it is CrowbarItem } }
    }

}