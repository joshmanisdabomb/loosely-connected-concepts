package com.joshmanisdabomb.lcc.data.factory.tag

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.item.HeartContainerItem
import com.joshmanisdabomb.lcc.item.HeartItem
import net.minecraft.item.Item
import net.minecraft.tag.Tag

object HeartItemTagFactory : ItemDataFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        if (entry !is HeartItem) return
        if (entry is HeartContainerItem) return
        data.tags.item(getTag(entry.heart)).attach(entry)
    }

    fun getTag(heart: HeartType) = LCCTags[heart.asString().plus("_hearts")] as Tag<Item>

}
