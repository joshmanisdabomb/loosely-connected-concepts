package com.joshmanisdabomb.lcc.item.block

import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.block.entity.DungeonTableBlockEntity
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

class ComputingBlockItem(val module: ComputerModule, settings: Settings) : PlasticBlockItem(LCCBlocks.computing, settings), DefaultedColoredItem, LCCItemTrait {

    override fun getTranslationKey() = getOrCreateTranslationKey()

    override fun appendBlocks(map: MutableMap<Block, Item>, item: Item) = Unit

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (!this.isIn(group)) return
        stacks.add(ItemStack(this))
    }

    fun getComputingHalf(be: ComputingBlockEntity, stack: ItemStack, direction: Direction, top: Boolean): ComputingBlockEntity.ComputingHalf {
        val half = be.ComputingHalf(this.module, direction, this.getTintColor(stack), top)
        if (stack.hasCustomName()) half.customName = stack.name
        return half
    }

}