package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.lib.item.DefaultedColoredItem
import com.joshmanisdabomb.lcc.trait.LCCItemTrait
import net.minecraft.block.Block
import net.minecraft.entity.ItemEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction

class ComputingItem(val module: ComputerModule, settings: Settings) : BlockItem(LCCBlocks.computing, settings), DefaultedColoredItem, LCCItemTrait {

    override fun defaultColor(stack: ItemStack) = 0xFFF7EE

    override fun lcc_doesDespawn(stack: ItemStack, entity: ItemEntity) = false

    override fun getTranslationKey() = getOrCreateTranslationKey()

    override fun appendBlocks(map: MutableMap<Block, Item>, item: Item) = Unit

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (!this.isIn(group)) return
        stacks.add(ItemStack(this))
    }

    fun getComputingHalf(stack: ItemStack, direction: Direction, top: Boolean) = ComputingBlockEntity.ComputingHalf(this.module, direction, this.getTintColor(stack), top)

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int) = if (tint != 1) -1 else (stack.item as DefaultedColoredItem).getTintColor(stack)
    }

}