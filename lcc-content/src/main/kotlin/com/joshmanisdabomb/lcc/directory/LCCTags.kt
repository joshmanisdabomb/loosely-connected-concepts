package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.tag.Tag

object LCCTags : ThingDirectory<Tag<*>, Unit>() {

    val wasteland_effective by createWithName { TagRegistry.block(LCC.id(it)) }
    val wasteland_required by createWithName { TagRegistry.block(LCC.id(it)) }

    val nether_reactor_base by createWithName { TagRegistry.block(LCC.id(it)) }
    val nether_reactor_shell by createWithName { TagRegistry.block(LCC.id(it)) }

    val gold_blocks by createWithName { TagRegistry.item(LCC.id(it)) }

    val generators by createWithName { TagRegistry.item(LCC.id(it)) }
    val furnace_generator_double by createWithName { TagRegistry.item(LCC.id(it)) }

    val geothermal_warm by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_hot by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_heated by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_soul_heated by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_burning by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_soul_burning by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_flaming by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_soul_flaming by createWithName { TagRegistry.block(LCC.id(it)) }
    val geothermal_full by createWithName { TagRegistry.block(LCC.id(it)) }

    val geothermal_magma by createWithName { TagRegistry.entityType(LCC.id(it)) }
    val geothermal_blaze by createWithName { TagRegistry.entityType(LCC.id(it)) }

    val enriched_uranium by createWithName { TagRegistry.item(LCC.id(it)) }

    val oil by createWithName { TagRegistry.fluid(LCC.id(it)) }
    val asphalt by createWithName { TagRegistry.fluid(LCC.id(it)) }

}