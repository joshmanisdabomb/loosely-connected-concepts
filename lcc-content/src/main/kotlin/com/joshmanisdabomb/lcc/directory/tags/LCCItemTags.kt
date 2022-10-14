package com.joshmanisdabomb.lcc.directory.tags

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object LCCItemTags : AdvancedDirectory<Identifier?, TagKey<Item>, Unit, Unit>() {

    val wasteland_equipment by entry(::initialiser) { null }

    val gold_blocks by entry(::initialiser) { null }

    val generators by entry(::initialiser) { null }
    val furnace_generator_double by entry(::initialiser) { null }

    val rubber_logs by entry(::initialiser) { null }
    val rubber_tree by entry(::initialiser) { null }

    val enriched_uranium by entry(::initialiser) { null }
    val airlocked_suits by entry(::initialiser) { null }

    val deadwood_logs by entry(::initialiser) { null }

    val plastic by entry(::initialiser) { null }

    val red_hearts by entry(::initialiser) { null }
    val iron_hearts by entry(::initialiser) { null }
    val crystal_hearts by entry(::initialiser) { null }
    val temporary_hearts by entry(::initialiser) { null }
    val hearts by entry(::initialiser) { null }
    val heart_condenser_fuel by entry(::initialiser) { null }

    val enhancing_pyre by entry(::initialiser) { null }
    val imbuable by entry(::initialiser) { null }

    fun initialiser(input: Identifier?, context: DirectoryContext<Unit>, parameters: Unit) = TagKey.of(Registry.ITEM_KEY, input ?: context.id)

    override fun defaultProperties(name: String) = Unit

    override fun defaultContext() = Unit

    override fun id(name: String) = LCC.id(name)

}