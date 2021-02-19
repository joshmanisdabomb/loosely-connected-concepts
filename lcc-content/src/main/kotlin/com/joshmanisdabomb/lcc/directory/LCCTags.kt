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

    val geothermal_warm by entry(::blockInitialiser) {}
    val geothermal_hot by entry(::blockInitialiser) {}
    val geothermal_heated by entry(::blockInitialiser) {}
    val geothermal_soul_heated by entry(::blockInitialiser) {}
    val geothermal_burning by entry(::blockInitialiser) {}
    val geothermal_soul_burning by entry(::blockInitialiser) {}
    val geothermal_flaming by entry(::blockInitialiser) {}
    val geothermal_soul_flaming by entry(::blockInitialiser) {}
    val geothermal_full by entry(::blockInitialiser) {}

    val geothermal_magma by entry(::entityTypeInitialiser) {}
    val geothermal_blaze by entry(::entityTypeInitialiser) {}

    val enriched_uranium by entry(::itemInitialiser) {}

    val oil by entry(::fluidInitialiser) {}
    val asphalt by entry(::fluidInitialiser) {}

    fun blockInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.block(context.id)
    fun entityTypeInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.entityType(context.id)
    fun itemInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.item(context.id)
    fun fluidInitialiser(input: Unit, context: DirectoryContext<Unit>, parameters: Unit) = TagRegistry.fluid(context.id)

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}