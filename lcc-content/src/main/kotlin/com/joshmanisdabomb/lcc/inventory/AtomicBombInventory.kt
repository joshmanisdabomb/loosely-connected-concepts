package com.joshmanisdabomb.lcc.inventory

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

class AtomicBombInventory : LCCInventory(7) {

    val uraniumCount get() = map { when (it.item) {
        LCCItems.enriched_uranium -> 1
        LCCBlocks.enriched_uranium_block.asItem() -> 4
        else -> 0
    }.times(it.count) }.sum()
    val canDetonate get() = this[0].count > 0 && this[0].item == Blocks.TNT.asItem() && this[1].count > 0 && this[1].item == LCCItems.enriched_uranium_nugget && uraniumCount > 0

    override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
        0 -> stack.isOf(Items.TNT)
        1 -> stack.isOf(LCCItems.enriched_uranium_nugget)
        else -> stack.isOf(LCCItems.enriched_uranium) || stack.isOf(LCCBlocks.enriched_uranium_block.asItem())
    }

    override fun getMaxCountPerStack() = 1

}