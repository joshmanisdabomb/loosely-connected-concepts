package com.joshmanisdabomb.lcc.data.generators.kb.section

import com.joshmanisdabomb.lcc.data.generators.kb.IncludedTranslatableText
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleImageFragmentBuilder
import com.joshmanisdabomb.lcc.data.generators.kb.fragment.KnowledgeArticleTextFragmentBuilder
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
import net.minecraft.text.Text
import kotlin.math.sign

class KnowledgeArticleItemInfoSectionBuilder(vararg models: ItemConvertible, name: (defaultKey: String) -> Text = { IncludedTranslatableText(it).translation("Information") }, type: String = "info", val renewable: Boolean = false) : KnowledgeArticleInfoSectionBuilder<Item, ItemConvertible>(*models, name = name, type = type) {

    override fun getList(provided: List<ItemConvertible>) = (if (provided.isNotEmpty()) provided.toList() else article.about.filterIsInstance<ItemConvertible>()).map(ItemConvertible::asItem)

    override fun getName(model: Item) = model.name

    public override fun getStats(map: MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>>): MutableMap<KnowledgeArticleTextFragmentBuilder, List<KnowledgeArticleFragmentBuilder>> {
        map[KnowledgeArticleTextFragmentBuilder("Image")] = listOf(KnowledgeArticleImageFragmentBuilder().apply { items.forEach { addArticle(KnowledgeArticleIdentifier.ofItemConvertible(it)) } })
        map[KnowledgeArticleTextFragmentBuilder("Stack Size")] = getTextStatFrom { it.maxCount.toString() }
        if (items.any { it.isDamageable }) {
            map[KnowledgeArticleTextFragmentBuilder("Durability")] = getTextStatFrom { it.maxDamage.toString() }
        }
        if (items.any { it is ToolItem }) {
            map[KnowledgeArticleTextFragmentBuilder("Mining Speed")] = getTextStatFrom { (it as? ToolItem)?.material?.miningSpeedMultiplier?.toString() }
            map[KnowledgeArticleTextFragmentBuilder("Mining Level")] = getTextStatFrom { (it as? ToolItem)?.material?.miningLevel?.toString() }
            map[KnowledgeArticleTextFragmentBuilder("Enchantability")] = getTextStatFrom { (it as? ToolItem)?.material?.enchantability?.toString() }
        }

        map[KnowledgeArticleTextFragmentBuilder("Attack Damage")] = getToolModifiers(EntityAttributes.GENERIC_ATTACK_DAMAGE) { a, m -> "${a?.plus(1.0)?.decimalFormat(2) ?: ""} ${if (m != null) "× ".plus(m) else ""}".trim() }
        map[KnowledgeArticleTextFragmentBuilder("Attack Speed")] = getToolModifiers(EntityAttributes.GENERIC_ATTACK_SPEED) { a, m -> "${a?.let { 4.0 + it }?.decimalFormat(2) ?: ""} ${if (m != null) "× ".plus(m) else ""}".trim() }

        map[KnowledgeArticleTextFragmentBuilder("Renewable")] = listOf(KnowledgeArticleTextFragmentBuilder(renewable.transform("Yes", "No")))
        map[KnowledgeArticleTextFragmentBuilder("Rarity")] = getTextStatFrom { it.getRarity(it.stack()).name.toLowerCase().capitalize() }
        return map
    }

    private fun getToolModifiers(type: EntityAttribute, serialise: (plus: Double?, times: Double?) -> String = { a, m -> "${if (a != null) (if (a.sign > 0) "+" else "").plus(a) else ""} ${if (m != null) "× ".plus(m) else ""}".trim() }): List<KnowledgeArticleTextFragmentBuilder> {
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