package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.Tag

class ItemTagFactory(vararg val tags: Tag<Item>) : BlockDataFactory, ItemDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        apply(data, entry.asItem())
    }

    override fun apply(data: DataAccessor, entry: Item) {
        tags.forEach { data.tags.item(it).attach(entry) }
    }

}
