package com.joshmanisdabomb.lcc.abstracts.computing.medium

import com.joshmanisdabomb.lcc.directory.LCCRegistries
import net.minecraft.util.StringIdentifiable

class DigitalMedium(val initialSpace: Int, val maxSpace: Int = initialSpace, val amountCost: Int = 1, val amountLimit: Int = 0, val typeCost: Int = 64, val typeLimit: Int = 0, val durability: Int = 0, val upgrader: ((space: Int) -> Int?)? = null) : StringIdentifiable {

    val id get() = LCCRegistries.computer_mediums.getKey(this).orElseThrow(::RuntimeException).value

    override fun asString() = id.path

}