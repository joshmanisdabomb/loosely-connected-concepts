package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.item.Item
import net.minecraft.tag.Tag
import net.minecraft.util.registry.Registry

open class ToolItemTagFactory(val type: String? = null) : ItemDataFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        val path = Registry.ITEM.getId(entry).path
        data.tags.item(when (type ?: path.split('_').last()) {
            "sword" -> FabricToolTags.SWORDS
            "pickaxe" -> FabricToolTags.PICKAXES
            "shovel" -> FabricToolTags.SHOVELS
            "axe" -> FabricToolTags.AXES
            "hoe" -> FabricToolTags.HOES
            else -> error("Could not determine tool type from path.")
        } as Tag.Identified<Item>).append(entry)
    }

    companion object : ToolItemTagFactory()

}
