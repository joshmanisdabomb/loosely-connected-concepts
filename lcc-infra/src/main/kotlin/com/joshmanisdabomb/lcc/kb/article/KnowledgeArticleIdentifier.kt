package com.joshmanisdabomb.lcc.kb.article

import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.feature.StructureFeature

class KnowledgeArticleIdentifier(val registry: Identifier, val key: Identifier) {

    override fun toString() = "$registry/$key"

    companion object {

        fun <R> of(registry: Registry<R>, item: R) = KnowledgeArticleIdentifier(registry.key.value, registry.getId(item)!!)

        fun ofBlock(block: Block) = of(Registry.BLOCK, block)
        fun ofItem(item: Item) = of(Registry.ITEM, item)
        fun ofEntity(entity: EntityType<*>) = of(Registry.ENTITY_TYPE, entity)

        fun ofStructure(structure: StructureFeature<*>) = of(Registry.STRUCTURE_FEATURE, structure)

    }

}