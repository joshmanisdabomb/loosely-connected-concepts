package com.joshmanisdabomb.lcc.item

import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.MobEntity
import net.minecraft.item.SpawnEggItem

open class VariableTintSpawnEggItem(type: EntityType<out MobEntity>, settings: Settings, vararg val colors: Int) : SpawnEggItem(type, colors[0], colors.getOrElse(1) { colors[0] }, settings) {

    override fun getColor(num: Int): Int {
        return colors.getOrElse(num) { colors[0] }
    }

}