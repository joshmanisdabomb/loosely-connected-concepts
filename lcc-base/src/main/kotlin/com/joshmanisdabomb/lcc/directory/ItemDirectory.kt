package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.settings.ItemExtraSettings
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

abstract class ItemDirectory : RegistryDirectory<Item, ItemExtraSettings>() {

    override val _registry by lazy { Registry.ITEM }

    override fun register(key: String, thing: Item, properties: ItemExtraSettings) = super.register(key, thing, properties).apply { properties.initItem(thing) }

    fun initClient() {
        all.forEach { (k, v) -> allProperties[k]!!.initItemClient(v) }
    }

    override fun getDefaultProperty() = ItemExtraSettings()

}