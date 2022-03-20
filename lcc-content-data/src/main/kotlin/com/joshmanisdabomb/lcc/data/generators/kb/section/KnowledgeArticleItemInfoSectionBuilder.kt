package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleImageFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleParagraphFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
import com.joshmanisdabomb.lcc.data.knowledge.KnowledgeConstants
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.kb.article.KnowledgeArticleIdentifier
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ToolItem
import kotlin.math.sign

class KnowledgeArticleItemInfoSectionBuilder(vararg models: ItemConvertible, val renewable: Boolean = false) : KnowledgeArticleInfoSectionBuilder<Item, ItemConvertible>(*models) {

    override fun getList(provided: List<ItemConvertible>) = (if (provided.isNotEmpty()) provided.toList() else article.about.filterIsInstance<ItemConvertible>()).map(ItemConvertible::asItem)

    override fun getName(model: Item) = model.name

    public override fun getStats(map: MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>): MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.image)] = listOf(KnowledgeArticleImageFragmentBuilder().apply { items.forEach { addModel(KnowledgeArticleIdentifier.ofItemConvertible(it)) } })
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.stackSize)] = getTextStatFrom { it.maxCount.toString() }
        if (items.any { it.isDamageable }) {
            map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.durability)] = getTextStatFrom { it.maxDamage.toString() }
        }
        if (items.any { it is ToolItem }) {
            map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.miningSpeed)] = getTextStatFrom { (it as? ToolItem)?.material?.miningSpeedMultiplier?.toString() }
            map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.miningLevel)] = getTextStatFrom { (it as? ToolItem)?.material?.miningLevel?.toString() }
        }
        if (items.any { it.enchantability > 0 }) {
            map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.enchantability)] = getTextStatFrom { it.enchantability.toString() }
        }

        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.attackDamage)] = getToolModifiers(EntityAttributes.GENERIC_ATTACK_DAMAGE) { a, m -> "${a?.plus(1.0)?.decimalFormat(2) ?: ""} ${if (m != null) "× ".plus(m) else ""}".trim() }
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.attackSpeed)] = getToolModifiers(EntityAttributes.GENERIC_ATTACK_SPEED) { a, m -> "${a?.let { 4.0 + it }?.decimalFormat(2) ?: ""} ${if (m != null) "× ".plus(m) else ""}".trim() }

        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.renewable)] = listOf(KnowledgeArticleTextFragmentBuilder(renewable.transform("Yes", "No")))
        map[KnowledgeArticleTextFragmentBuilder(KnowledgeConstants.rarity)] = getTextStatFrom { it.getRarity(it.stack()).name.toLowerCase().capitalize() }
        return map
    }

    private fun getToolModifiers(type: EntityAttribute, serialise: (plus: Double?, times: Double?) -> String = { a, m -> "${if (a != null) (if (a.sign > 0) "+" else "").plus(a) else ""} ${if (m != null) "× ".plus(m) else ""}".trim() }): List<KnowledgeArticleParagraphFragmentBuilder> {
        val modifiers = items.associateWith { EquipmentSlot.values().associateWith { e -> it.getAttributeModifiers(e) } }
        val map = mutableMapOf<Item, String>()
        modifiers.forEach { (k, v) ->
            val plus = mutableMapOf<EquipmentSlot, MutableList<Double>>()
            val times = mutableMapOf<EquipmentSlot, MutableList<Double>>()
            v.forEach { (k2, v2) ->
                v2.get(type).forEach {
                    when (it.operation) {
                        EntityAttributeModifier.Operation.MULTIPLY_BASE -> times.computeIfAbsent(k2) { mutableListOf() } += it.value
                        EntityAttributeModifier.Operation.MULTIPLY_TOTAL -> times.computeIfAbsent(k2) { mutableListOf() } += it.value.plus(1.0)
                        else -> plus.computeIfAbsent(k2) { mutableListOf() } += it.value
                    }
                }
            }
            val display = serialise(plus.map { (k, v) -> v.sum() }.maxOrNull(), times.map { (k, v) -> v.reduce(Double::times) }.maxOrNull())
            if (display.isNotBlank()) map.put(k, display)
        }
        return getTextStatFrom { map[it] }
    }

}