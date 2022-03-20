package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.tag.TagKey

class BlockTagFactory(vararg val tags: TagKey<Block>) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        tags.forEach { data.tags.block(it).attach(entry) }
    }

}
