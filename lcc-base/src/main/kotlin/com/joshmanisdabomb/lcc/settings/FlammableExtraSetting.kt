package com.joshmanisdabomb.lcc.settings

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item

class FlammableExtraSetting(val burn: Int, val chance: Int, vararg val fires: Block) : ExtraSetting {

    override fun initBlock(block: Block) {
        fires.forEach { FlammableBlockRegistry.getInstance(it).add(block, burn, chance) }
    }

    override fun initBlockClient(block: Block) = Unit

    override fun initItem(item: Item) = Unit

    override fun initItemClient(item: Item) = Unit

    companion object {
        fun <T : BlockExtraSettings> T.flammability(burn: Int, chance: Int, vararg fires: Block) = this.add(FlammableExtraSetting(burn, chance, *fires)).let { this }
    }

}