package com.joshmanisdabomb.lcc.settings

import net.minecraft.block.Block
import net.minecraft.item.Item

interface ExtraSetting {

    fun initBlock(block: Block)

    fun initBlockClient(block: Block)

    fun initItem(item: Item)

    fun initItemClient(item: Item)

}