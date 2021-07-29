package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.tag.Tag

object LCCTags : AdvancedDirectory<Unit, Tag<*>, Unit, Unit>() {

    val wasteland_effective by entry(::blockInitialiser) {}
    val wasteland_required by entry(::blockInitialiser) {}

    val nether_reactor_base by entry(::blockInitialiser) {}
    val nether_reactor_shell by entry(::blockInitialiser) {}

    val gold_blocks by entry(::itemInitialiser) {}

    val generators by entry(::itemInitialiser) {}
    val furnace_generator_double by entry(::itemInitialiser) {}

    val rubber_logs by entry(::blockInitialiser) {}
    val rubber_logs_i by entry(::itemInitialiser) {}
    val rubber_tree by entry(::itemInitialiser) {}

    val temperature_lukewarm by entry(::blockInitialiser) {}
    val temperature_warm by entry(::blockInitialiser) {}
    val temperature_hot by entry(::blockInitialiser) {}
    val temperature_scalding by entry(::blockInitialiser) {}
    val temperature_soul_scalding by entry(::blockInitialiser) {}
    val temperature_burning by entry(::blockInitialiser) {}
    val temperature_soul_burning by entry(::blockInitialiser) {}
    val temperature_scorching by entry(::blockInitialiser) {}
    val temperature_soul_scorching by entry(::blockInitialiser) {}
    val temperature_red_hot by entry(::blockInitialiser) {}
    val temperature_nuclear by entry(::blockInitialiser) {}

    val temperature_scalding_e by entry(::entityTypeInitialiser) {}
    val temperature_red_hot_e by entry(::entityTypeInitialiser) {}

    val enriched_uranium by entry(::itemInitialiser) {}
    val airlocked_suits by entry(::itemInitialiser) {}

    val salt_weakness by entry(::entityTypeInitialiser) {}
    val radioactive by entry(::blockInitialiser) {}

    val oil by entry(::fluidInitialiser) {}
    val asphalt by entry(::fluidInitialiser) {}

    val deadwood_logs by entry(::blockInitialiser) {}
    val deadwood_logs_i by entry(::itemInitialiser) {}

    val wasteland_equipment by entry(::itemInitialiser) {}

    fun blockInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.block(context.id)
    fun entityTypeInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.entityType(context.id)
    fun itemInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.item(context.id)
    fun fluidInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.fluid(context.id)

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}