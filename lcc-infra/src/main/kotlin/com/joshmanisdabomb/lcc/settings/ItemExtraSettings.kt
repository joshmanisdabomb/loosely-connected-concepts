package com.joshmanisdabomb.lcc.settings

import net.minecraft.item.Item

open class ItemExtraSettings : ExtraSettings() {

    override fun add(setting: ExtraSetting) = super.add(setting).let { this }

    fun initItem(item: Item) = list.forEach { it.initItem(item) }

    fun initItemClient(item: Item) = list.forEach { it.initItemClient(item) }

}