package com.joshmanisdabomb.lcc.kb.article

import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.fluid.Fluid
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.StructureFeature

class KnowledgeArticleIdentifier(val registry: Identifier, val key: Identifier) : Comparable<KnowledgeArticleIdentifier> {

    override fun toString() = "$registry::$key"

    companion object {

        fun <R> of(registry: Registry<R>, item: R) = KnowledgeArticleIdentifier(registry.key.value, registry.getId(item)!!)

        fun ofBlock(block: Block) = of(Registry.BLOCK, block)
        fun ofItem(item: Item) = of(Registry.ITEM, item)
        fun ofItemConvertible(item: ItemConvertible) : KnowledgeArticleIdentifier {
            if (item is Block) ofBlock(item)
            val i = item.asItem()
            return if (i is BlockItem) ofBlock(i.block) else ofItem(i)
        }
        fun ofEntity(entity: EntityType<*>) = KnowledgeArticleIdentifier(Identifier("entity"), Registry.ENTITY_TYPE.getId(entity))
        fun ofFluid(fluid: Fluid) = of(Registry.FLUID, fluid)

        fun ofEnchant(enchant: Enchantment) = of(Registry.ENCHANTMENT, enchant)
        fun ofEffect(effect: StatusEffect) = KnowledgeArticleIdentifier(Identifier("effect"), Registry.STATUS_EFFECT.getId(effect)!!)

        fun ofBiome(biome: Biome) = of(BuiltinRegistries.BIOME, biome)
        fun ofStructure(structure: StructureFeature<*>) = of(Registry.STRUCTURE_FEATURE, structure)

    }

    override fun compareTo(other: KnowledgeArticleIdentifier): Int {
        val comp = key.compareTo(other.key)
        if (comp != 0) return comp
        return registry.compareTo(other.registry)
    }

    override fun equals(other: Any?): Boolean {
        val o = other as? KnowledgeArticleIdentifier
        return if (o != null) registry == o.registry && key == o.key
        else super.equals(other)
    }

    override fun hashCode(): Int {
        return registry.hashCode().times(31) + key.hashCode()
    }

}