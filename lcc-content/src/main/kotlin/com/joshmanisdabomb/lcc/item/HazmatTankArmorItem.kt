package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedItem
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.toInt
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class HazmatTankArmorItem(slot: EquipmentSlot, settings: Settings) : HazmatArmorItem(slot, settings), LCCExtendedItem, OxygenStorage {

    override fun getMaxOxygen(stack: ItemStack) = LCCItems.oxygen_tank.max

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        if (entity.armorItems.any { it.item !is HazmatArmorItem }) return
        if (entity.armorItems.indexOf(stack) <= -1) return
        this.addOxygen(stack, -1)
    }

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (isIn(group)) {
            stacks.add(ItemStack(this))
            stacks.add(ItemStack(this).also { setOxygen(it, LCCItems.oxygen_tank.max); })
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(TranslatableText(translationKey.plus(".oxygen"), getOxygen(stack), getMaxOxygen(stack)).formatted(Formatting.BLUE))
    }

    override fun lcc_getAdditionalItemBarIndexes(stack: ItemStack) = intArrayOf(0)

    override fun lcc_getAdditionalItemBarOffset(stack: ItemStack, index: Int) = stack.isItemBarVisible.toInt(3, 0)

    override fun lcc_getAdditionalItemBarStep(stack: ItemStack, index: Int) = getOxygenBarStep(stack)

    override fun lcc_getAdditionalItemBarColor(stack: ItemStack, index: Int) = getOxygenBarColor(stack)

}