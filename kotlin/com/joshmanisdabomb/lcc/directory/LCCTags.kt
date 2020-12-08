package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.tag.Tag

object LCCTags : ThingDirectory<Tag<*>, Unit>() {

    val wasteland_effective by createWithName { TagRegistry.block(LCC.id(it)) }
    val wasteland_required by createWithName { TagRegistry.block(LCC.id(it)) }

}