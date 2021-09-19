package com.joshmanisdabomb.lcc.data.container

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block

class BlockDataContainer(accessor: DataAccessor) : DataContainer<Block, BlockDataFactory>(accessor) {

    override fun affects(entry: Block) = super.affects(entry).let { this }

    override fun affects(entries: List<Block>) = super.affects(entries).let { this }

    override fun add(factory: BlockDataFactory) = super.add(factory).let { this }

    override fun apply(factory: BlockDataFactory, entry: Block) {
        factory.apply(accessor, entry)
    }

}
