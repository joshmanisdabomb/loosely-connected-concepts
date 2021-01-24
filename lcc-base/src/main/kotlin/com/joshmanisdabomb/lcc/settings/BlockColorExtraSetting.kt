package com.joshmanisdabomb.lcc.settings

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.block.Block
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.item.Item

class BlockColorExtraSetting(val provider: () -> () -> BlockColorProvider) : ExtraSetting {

    override fun initBlock(block: Block) = Unit

    override fun initBlockClient(block: Block) {
        ColorProviderRegistry.BLOCK.register(provider()(), block)
    }

    override fun initItem(item: Item) = Unit

    override fun initItemClient(item: Item) = Unit

    companion object {
        fun <T : ItemExtraSettings> T.blockColor(provider: () -> () -> BlockColorProvider) = this.add(BlockColorExtraSetting(provider)).let { this }
    }

}