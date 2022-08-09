package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants
import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class OxygenStorageItem(val max: Float, settings: Settings) : Item(settings), OxygenStorage {

    override fun getMaxOxygen(stack: ItemStack) = max

    override fun isItemBarVisible(stack: ItemStack) = true

    override fun getItemBarStep(stack: ItemStack) = getOxygenBarStep(stack)

    override fun getItemBarColor(stack: ItemStack) = getOxygenBarColor(stack)

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (isIn(group)) {
            stacks.add(ItemStack(this))
            stacks.add(ItemStack(this).also { setOxygen(it, max); })
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(Text.translatable(TooltipConstants.oxygen, getOxygen(stack).decimalFormat(force = true), getMaxOxygen(stack).decimalFormat(force = true)).formatted(Formatting.BLUE))
    }

}
