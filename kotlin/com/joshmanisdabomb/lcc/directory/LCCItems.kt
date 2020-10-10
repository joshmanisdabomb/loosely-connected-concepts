package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.item.GauntletItem
import net.minecraft.item.Item
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

object LCCItems : RegistryDirectory<Item, Unit>() {

    override val _registry by lazy { Registry.ITEM }

    val test_item by create { Item(Item.Settings().defaults()) }

    val gauntlet by create { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }

    fun Item.Settings.defaults(): Item.Settings {
        return this.group(LCCGroups.group)
    }

}