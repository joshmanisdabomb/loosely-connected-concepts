package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.lib.item.DefaultedColoredItem
import net.minecraft.item.Item

abstract class ColoredItem(settings: Settings) : Item(settings), DefaultedColoredItem {

}
