package com.joshmanisdabomb.lcc.settings

import net.minecraft.block.Block

class BlockExtraSettings : ItemExtraSettings() {

    override fun add(setting: ExtraSetting) = super.add(setting).let { this }

    fun initBlock(block: Block) = list.forEach { it.initBlock(block) }

    fun initBlockClient(block: Block) = list.forEach { it.initBlockClient(block) }

}