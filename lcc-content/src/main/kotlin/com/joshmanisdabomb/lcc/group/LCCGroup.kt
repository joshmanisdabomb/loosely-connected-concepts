package com.joshmanisdabomb.lcc.group

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.creativeex.CreativeExCategory
import com.joshmanisdabomb.lcc.creativeex.CreativeExGroup
import com.joshmanisdabomb.lcc.creativeex.CreativeExItemStackDisplay
import com.joshmanisdabomb.lcc.creativeex.CreativeExStackDisplay
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.item.HeartContainerItem
import com.joshmanisdabomb.lcc.item.HeartItem
import net.minecraft.item.ItemStack

class LCCGroup : CreativeExGroup(LCC.id("group")) {

    override fun createIcon() = ItemStack(LCCBlocks.test_block)

    enum class LCCGroupCategory(override val groupColor: Long, override val comparator: (CreativeExStackDisplay) -> Int = { it.sortValue }) : CreativeExCategory {

        RESOURCES(0xFFFF00FF, label@{
            if (it !is CreativeExItemStackDisplay) return@label it.sortValue
            val id = it.stack.item.identifier.path!!
            it.sortValue + when {
                id.endsWith("ore") -> 0
                id.endsWith("nugget") -> 1
                id.endsWith("block") -> 3
                else -> 2
            }
        }),
        TOOLS(0xFFFF00FF, label@{
            if (it !is CreativeExItemStackDisplay) return@label it.sortValue
            val item = it.stack.item!!
            it.sortValue + when (item) {
                is net.minecraft.item.SwordItem -> 0
                is net.minecraft.item.PickaxeItem -> 1
                is net.minecraft.item.ShovelItem -> 2
                is net.minecraft.item.AxeItem -> 3
                is net.minecraft.item.HoeItem -> 4
                is net.minecraft.item.ArmorItem -> 8.minus(item.slotType.entitySlotId)
                else -> 9
            }
        }),
        BUILDING(0xFFD8D5C0),
        CRAFTERS(0xFFB97A57),
        POWER(0xFFC7B330),
        GIZMOS(0xFFFF00FF),
        RAINBOW(0xFFAD72AD),
        SPREADERS(0xFF5D473E),
        WASTELAND(0xFFFF00FF),
        NUCLEAR(0xFFFF00FF),
        COMPUTING(0xFF194a19),
        NOSTALGIA(0xFF69854E),
        SPECIAL(0xFFFF00FF),
        HEALTH(0xFFFF00FF, label@{
            if (it !is CreativeExItemStackDisplay) return@label it.sortValue
            it.sortValue.times(3).plus(if (it.stack.item is HeartContainerItem) 2 else (it.stack.item as? HeartItem)?.value?.toInt() ?: return@label it.sortValue)
        }),
        TESTING(0xFFFF00FF);

        override val sortValue = ordinal

    }

}