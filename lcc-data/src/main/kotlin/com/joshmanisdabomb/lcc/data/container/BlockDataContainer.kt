package com.joshmanisdabomb.lcc.data.container

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block

class BlockDataContainer : DataContainer<Block, BlockDataFactory>() {

    override fun add(factory: BlockDataFactory) = super.add(factory).let { this }

    override fun apply(factory: BlockDataFactory, entry: Block) {
        factory.apply(LCCData.accessor, entry)
    }

}
