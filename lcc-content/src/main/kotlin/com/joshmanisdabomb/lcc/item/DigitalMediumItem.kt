package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.abstracts.computing.medium.DigitalMedium
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.NBT_INT
import com.joshmanisdabomb.lcc.extensions.NBT_LIST
import com.joshmanisdabomb.lcc.lib.item.DefaultedColoredItem
import com.joshmanisdabomb.lcc.lib.item.DefaultedDyeableItem
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import kotlin.math.roundToInt

class DigitalMediumItem(val medium: DigitalMedium, settings: Settings) : ComputingItem(medium.initialSpace, medium.maxSpace, settings, medium.upgrader), DefaultedDyeableItem, PlasticCraftingResult {

    override fun isItemBarVisible(stack: ItemStack) = stack.getSubNbt("lcc-computing")?.containsUuid("id") == true || stack.getSubNbt("lcc-computing")?.contains("partitions", NBT_LIST) == true

    override fun getItemBarStep(stack: ItemStack): Int {
        val info = DiskInfo(stack)
        return 13.times(info.usedSpace.toFloat().div(getLevel(stack))).roundToInt()
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        val info = DiskInfo(stack)
        val fill = info.usedSpace.toFloat().div(getLevel(stack))

        if (fill >= 1f) return 0xFF0000
        else if (fill >= 0.9f) return 0xffa200
        else return 0x00a6ff
    }

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (this == LCCItems.floppy_disk) {
            if (isIn(group)) {
                stacks.add(ItemStack(this))
                stacks.add(ItemStack(this).also { it.getOrCreateSubNbt("display").also { it.putInt("color2", 0x303030); it.putInt("color", 0xA05000) }; DiskInfo(it).addPartition(DiskPartition(null, "Console OS", LCCPartitionTypes.console, LCCPartitionTypes.console.size)) })
            }
        } else {
            return super.appendStacks(group, stacks)
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val info = DiskInfo(stack)
        if (info.label != null) {
            tooltip.add(LiteralText(info.label).formatted(Formatting.WHITE))
        }
        if (Screen.hasShiftDown()) {
            tooltip.add(TranslatableText(TooltipConstants.computing_disk_space_advanced, info.usedSpace, info.allocatedSpace, getLevel(stack)).formatted(Formatting.AQUA))
            tooltip.add(TranslatableText(TooltipConstants.computing_disk_id, info.id?.toString() ?: TranslatableText(TooltipConstants.computing_disk_id_null)).formatted(Formatting.DARK_GRAY))
        } else {
            tooltip.add(TranslatableText(TooltipConstants.computing_disk_space, info.usedSpace, getLevel(stack)).formatted(Formatting.AQUA))
        }
        val partitions = info.partitions
        if (partitions.isNotEmpty()) {
            tooltip.add(LiteralText(""))
            tooltip.add(TranslatableText(TooltipConstants.computing_partitions).formatted(Formatting.GRAY))
            for (partition in partitions) {
                tooltip.add(TranslatableText(TooltipConstants.computing_partition_name, partition.label).formatted(partition.type.nameColor))
                tooltip.add(TranslatableText(TooltipConstants.computing_partition_space, partition.usedSpace, partition.size).formatted(Formatting.DARK_AQUA))
                if (Screen.hasShiftDown()) {
                    tooltip.add(TranslatableText(TooltipConstants.computing_partition_id, partition.id?.toString() ?: TranslatableText(TooltipConstants.computing_partition_id_null)).formatted(Formatting.DARK_GRAY))
                }
            }
        }
    }

    override fun defaultColor(stack: ItemStack) = 0xFFFFFF

    override fun modifyPlasticOutputStack(stack: ItemStack, resultColor: Int) {
        stack.getOrCreateSubNbt("display").putInt("color2", resultColor)
    }

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int) = when (tint) {
            1 -> {
                val display = stack.getSubNbt("display")
                if (display?.contains("color2", NBT_INT) == true) display.getInt("color2") else PlasticItem.defaultColor
            }
            2 -> DefaultedColoredItem.getTintColor(stack, 0)
            else -> -1
        }
    }

}