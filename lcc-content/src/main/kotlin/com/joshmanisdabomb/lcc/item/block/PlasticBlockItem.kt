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

open class PlasticBlockItem(block: Block, settings: Settings) : BlockItem(block, settings), DefaultedColoredItem, LCCItemTrait {

    override fun defaultColor(stack: ItemStack) = 0xFFF7EE

    override fun lcc_doesDespawn(stack: ItemStack, entity: ItemEntity) = false

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int) = if (tint != 1) -1 else (stack.item as DefaultedColoredItem).getTintColor(stack)
    }

}