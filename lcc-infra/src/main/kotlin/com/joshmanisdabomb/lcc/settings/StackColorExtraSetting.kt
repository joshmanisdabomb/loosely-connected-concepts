package com.joshmanisdabomb.lcc.settings

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class StackColorExtraSetting(val provider: (stack: ItemStack, tintIndex: Int) -> Int) : ExtraSetting {

    override fun initBlock(block: Block) = Unit

    override fun initBlockClient(block: Block) = Unit

    override fun initItem(item: Item) = Unit

    override fun initItemClient(item: Item) {
        ColorProviderRegistry.ITEM.register(provider, item)
    }

    companion object {
        fun <T : ItemExtraSettings> T.stackColor(provider: (stack: ItemStack, tintIndex: Int) -> Int) = this.add(StackColorExtraSetting(provider)).let { this }
    }

}