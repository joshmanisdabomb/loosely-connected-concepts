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

    val furnace_generator_double by createWithName { TagRegistry.item(LCC.id(it)) }

    val oil by createWithName { TagRegistry.fluid(LCC.id(it)) }
    val asphalt by createWithName { TagRegistry.fluid(LCC.id(it)) }

}