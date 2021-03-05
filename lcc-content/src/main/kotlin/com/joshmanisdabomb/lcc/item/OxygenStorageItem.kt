package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.directory.LCCItems
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class OxygenStorageItem(val max: Int, settings: Settings) : Item(settings), OxygenStorage {

    override fun getMaxOxygen(stack: ItemStack) = max

    override fun isItemBarVisible(stack: ItemStack) = true

    override fun getItemBarStep(stack: ItemStack) = getOxygenBarStep(stack)

    override fun getItemBarColor(stack: ItemStack) = getOxygenBarColor(stack)

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (isIn(group)) {
            stacks.add(ItemStack(this))
            stacks.add(ItemStack(this).also { setOxygen(it, LCCItems.oxygen_tank.max); })
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(TranslatableText(translationKey.plus(".oxygen"), getOxygen(stack), getMaxOxygen(stack)).formatted(Formatting.BLUE))
    }

}
