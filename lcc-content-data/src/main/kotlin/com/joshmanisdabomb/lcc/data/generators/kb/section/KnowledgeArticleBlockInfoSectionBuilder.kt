package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.*
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.item.CrowbarItem
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import com.joshmanisdabomb.lcc.mixin.data.common.BlockStateAccessor
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FireBlock
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.*
import net.minecraft.util.registry.Registry

class KnowledgeArticleBlockInfoSectionBuilder(vararg models: Block, val renewable: Boolean = false) : KnowledgeArticleInfoSectionBuilder<Block, Block>(*models) {

    override fun getList(provided: List<Block>) = if (provided.isNotEmpty()) provided.toList() else article.about.filterIsInstance<Block>()

    override fun getName(model: Block) = model.name

    override fun getStats(map: MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>): MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.image)] = listOf(KnowledgeArticleImageFragmentBuilder().apply { items.forEach { addModel(KnowledgeArticleIdentifier.ofBlock(it)) } })

        map += addToolStat()
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.hardness)] = getTextStatFrom { (it.defaultState as BlockStateAccessor).hardness.toString() }
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.blastResistance)] = getTextStatFrom { it.blastResistance.toString() }
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.luminance)] = getTextStatFrom {
            val array = it.stateManager.states.map { it.luminance }.toIntArray()
            val min = array.minOrNull()!!
            val max = array.maxOrNull()!!
            if (min == max) min.toString() else "$min-$max"
        }
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.friction)] = getTextStatFrom { if (it.slipperiness == 0.6f) "Normal" else it.slipperiness.toString() }
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.flammability)] = getFlammability()
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.randomTicks)] = getTextStatFrom { it.stateManager.states.map(BlockState::hasRandomTicks).any().transform("Yes", "No") }

        map += KnowledgeArticleItemInfoSectionBuilder(*items.map(Block::asItem).toTypedArray(), renewable = renewable).getStats().toList().drop(1)

        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.mapColors)] = getStatFrom({ it.values.flatMap { it }.distinct().map(::KnowledgeArticleColorFragmentBuilder) }) { it.stateManager.states.map { (it as BlockStateAccessor).mapColor.color } }
        return map
    }

    private fun addToolStat() : Pair<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        for (tool in tools) {
            val name = when (tool.identifier.namespace) {
                LCC.modid -> tool.identifier.path.split("_").joinToString(" ") { it.capitalize() }
                else -> I18n.translate(tool.translationKey)
            }
            if (article.tags.contains("$name Recommended")) return KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.recommendedTool) to listOf(KnowledgeArticleStackFragmentBuilder(tool.stack()) { it.addProperty("tool_recommended", true) })
            else if (article.tags.contains("$name Required")) return KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.requiredTool) to listOf(KnowledgeArticleStackFragmentBuilder(tool.stack()) { it.addProperty("tool_required", true) })
        }
        return KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.requiredTool) to listOf(KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.anyTool))
    }

    private fun getFlammability(): List<KnowledgeArticleFragmentBuilder> {
        val fires = Registry.BLOCK.filterIsInstance<FireBlock>().filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" }
        val effective = fires.filter { items.any { b -> val entry = FlammableBlockRegistry.getInstance(it).get(b) ?: return@any false; entry.burnChance > 0 || entry.spreadChance > 0 } }
        return if (effective.isEmpty()) return listOf(KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.notFlammable))
        else effective.map { KnowledgeArticleLinkFragmentBuilder(KnowledgeArticleIdentifier.ofBlock(it)).addFragment(KnowledgeArticleTextFragmentBuilder(it.name)) }
    }

    companion object {
        private val tools by lazy { Registry.ITEM.filter { it.identifier.namespace == LCC.modid || it.identifier.namespace == "minecraft" }.filter { it is SwordItem || it is PickaxeItem || it is ShovelItem || it is AxeItem || it is HoeItem || it is ShearsItem || it is CrowbarItem } }
    }

}