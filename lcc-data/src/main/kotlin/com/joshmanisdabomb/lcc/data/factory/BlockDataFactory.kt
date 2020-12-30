package com.joshmanisdabomb.lcc.data.factory

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.container.BlockDataContainer
import net.minecraft.block.Block

interface BlockDataFactory {

    fun init(container: BlockDataContainer) = this

    fun apply(data: DataAccessor, entry: Block)

}