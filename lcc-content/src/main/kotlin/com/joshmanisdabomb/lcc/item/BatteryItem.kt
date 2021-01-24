package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.MathHelper.ceil

class BatteryItem(val max: Float, settings: Settings) : Item(settings), StackEnergyStorage {

    override fun isItemBarVisible(stack: ItemStack) = true

    override fun getItemBarStep(stack: ItemStack): Int {
        return ceil((getEnergyFill(StackEnergyContext(stack))?.coerceAtMost(1f) ?: 0f).times(13f))
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        val fill = getEnergyFill(StackEnergyContext(stack))?.coerceAtMost(1f) ?: 0f
        return MathHelper.hsvToRgb(fill.times(0.1f).plus(0.05f), fill.times(0.7f).plus(0.3f), fill.times(0.2f).plus(0.8f))
    }

    override fun getRawEnergyMaximum(context: StackEnergyContext) = max

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int): Int {
            if (tint != 1) return 0xFFFFFF
            val fill = (stack.item as? StackEnergyStorage)?.getEnergyFill(StackEnergyContext(stack))?.coerceAtMost(1f) ?: 0f
            return MathHelper.hsvToRgb(fill.times(0.03f), fill.times(0.4f).plus(0.6f), fill.times(0.5f).plus(0.3f))
        }
    }

}