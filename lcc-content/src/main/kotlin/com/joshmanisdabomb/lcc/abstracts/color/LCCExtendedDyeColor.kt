package com.joshmanisdabomb.lcc.abstracts.color

import com.joshmanisdabomb.lcc.creativeex.CreativeExSetKey
import net.minecraft.block.MapColor
import net.minecraft.item.Item
import net.minecraft.util.StringIdentifiable

interface LCCExtendedDyeColor : StringIdentifiable, CreativeExSetKey {

    abstract val name: String

    val lcc_color: Int
    val lcc_colorComponents: FloatArray

    val lcc_signColor: Int

    val lcc_fireworkColor: Int

    val lcc_mapColor: MapColor

    val lcc_dye: Item?

    override fun asString() = this.name.toLowerCase()

    override val selectionColor get() = floatArrayOf(lcc_colorComponents[0], lcc_colorComponents[1], lcc_colorComponents[2], 1.0f)

    //Custom Colors
    val plasticColor: Int

    companion object {
        fun getComponents(color: Int): FloatArray {
            val i = color and 16711680 shr 16
            val j = color and '\uff00'.toInt() shr 8
            val k = color and 255
            return floatArrayOf(i.toFloat() / 255.0f, j.toFloat() / 255.0f, k.toFloat() / 255.0f, 1.0f)
        }
    }

}