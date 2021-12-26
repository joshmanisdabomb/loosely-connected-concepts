package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.computing.medium.DigitalMedium
import net.minecraft.item.Item

class DigitalMediumItem(val medium: DigitalMedium, settings: Settings) : ComputingItem(medium.initialSpace, medium.maxSpace, settings, medium.upgrader) {

}
