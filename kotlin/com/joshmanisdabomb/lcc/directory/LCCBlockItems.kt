package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCItems.defaults
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object LCCBlockItems : ThingDirectory<LCCBlockItems.Replacement, Unit>() {

    val oil by create { Replacement() }

    override fun init(predicate: (name: String, properties: Unit) -> Boolean) {
        super.init(predicate)
        LCCBlocks.all.forEach { (k, v) -> Registry.register(Registry.ITEM, LCC.id(k), all.getOrDefault(k, Replacement(BlockItem(v, Item.Settings().defaults()))).bi ?: return@forEach) }
    }

    data class Replacement(val bi: BlockItem? = null)

}