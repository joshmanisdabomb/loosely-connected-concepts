package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry

open class ToolItemTagFactory(val type: String? = null, val sword: Tag<Item> = FabricToolTags.SWORDS, val pickaxe: Tag<Item> = FabricToolTags.PICKAXES, val shovel: Tag<Item> = FabricToolTags.SHOVELS, val axe: Tag<Item> = FabricToolTags.AXES, val hoe: Tag<Item> = FabricToolTags.HOES) : ItemDataFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val path = Registry.ITEM.getId(entry).path
        data.tags.item(when (type ?: path.split('_').last()) {
            "sword" -> sword
            "pickaxe" -> pickaxe
            "shovel" -> shovel
            "axe" -> axe
            "hoe" -> hoe
            else -> error("Could not determine tool type from path.")
        }).attach(entry)
    }

    companion object : ToolItemTagFactory()

}
