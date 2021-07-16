package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.trait.LCCItemTrait
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack

class PlasticItem(settings: Settings) : ColoredItem(settings), LCCItemTrait {

    override fun defaultColor(stack: ItemStack) = 0xFFF7EE

    override fun lcc_doesDespawn(stack: ItemStack, entity: ItemEntity) = false

    companion object {

        fun getColorBlend(vararg colors: LCCExtendedDyeColor): Int {
            val floats = colors.map { LCCExtendedDyeColor.getComponents(it.plasticColor) }.fold(FloatArray(3) { 0f }) { a, b -> a.zip(b) { c, d -> c.plus(d.div(colors.count())) }.toFloatArray() }
            val r = (floats[0] * 255).toInt()
            val g = (floats[1] * 255).toInt()
            val b = (floats[2] * 255).toInt()
            return b + (g shl 8) + (r shl 16)
        }

    }

}