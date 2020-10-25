package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.concepts.hearts.HeartType
import com.joshmanisdabomb.lcc.item.GauntletItem
import com.joshmanisdabomb.lcc.item.HeartItem
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object LCCItems : RegistryDirectory<Item>() {

    override val _registry by lazy { Registry.ITEM }

    val test_item by create { Item(Item.Settings().defaults()) }

    val gauntlet by create { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }

    val heart_half by createMap(*HeartType.values()) { key, name, properties -> HeartItem(key, Item.Settings().defaults()) }
    val heart_full by createMap(*HeartType.values()) { key, name, properties -> HeartItem(key, Item.Settings().defaults()) }
    val heart_container by createMap(*HeartType.values().filter { it.container }.toTypedArray()) { key, name, properties -> HeartItem(key, Item.Settings().defaults()) }

    fun Item.Settings.defaults(): Item.Settings {
        return this.group(LCCGroups.group)
    }

}