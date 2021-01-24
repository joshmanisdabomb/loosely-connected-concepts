package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.MathHelper.ceil
import net.minecraft.world.World

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

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (isIn(group)) {
            stacks.add(ItemStack(this))
            stacks.add(ItemStack(this).also { setRawEnergy(StackEnergyContext(it), max); })
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        with (StackEnergyContext(stack)) {
            tooltip.add(TranslatableText(translationKey.plus(".energy"), LooseEnergy.display(getEnergy(LooseEnergy, this) ?: 0f), LooseEnergy.display(getMaximumEnergy(LooseEnergy, this) ?: 0f), " ".plus(LooseEnergy.units)).formatted(Formatting.GOLD))
        }
    }

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int): Int {
            if (tint != 1) return 0xFFFFFF
            val fill = (stack.item as? StackEnergyStorage)?.getEnergyFill(StackEnergyContext(stack))?.coerceAtMost(1f) ?: 0f
            return MathHelper.hsvToRgb(fill.times(0.03f), fill.times(0.4f).plus(0.6f), fill.times(0.5f).plus(0.3f))
        }
    }

}