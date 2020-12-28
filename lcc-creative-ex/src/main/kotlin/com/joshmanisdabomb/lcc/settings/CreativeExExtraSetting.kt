package com.joshmanisdabomb.lcc.settings

import com.joshmanisdabomb.lcc.creativeex.CreativeExCategory
import com.joshmanisdabomb.lcc.creativeex.CreativeExGroup
import com.joshmanisdabomb.lcc.creativeex.CreativeExSetKey
import com.joshmanisdabomb.lcc.creativeex.CreativeExSortValue
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.util.Util
import net.minecraft.util.collection.DefaultedList

class CreativeExExtraSetting(val category: CreativeExCategory, val sortValue: (sort: CreativeExSortValue, category: CreativeExCategory) -> Int = sortValueDefault(), val set: String? = null, val setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) : ExtraSetting {

    override fun initBlock(block: Block) = Unit

    override fun initBlockClient(block: Block) = Unit

    override fun initItem(item: Item) {
        val group = item.group as? CreativeExGroup ?: return
        val list = Util.make(DefaultedList.of<ItemStack>()) { item.appendStacks(item.group, it) }
        val sortValue = sortValue(group.sortValue, category).also { sortValues[item] = it }
        if (set != null && setKey != null) {
            list.forEachIndexed { k, v -> group.addToSet(v, set, setKey.invoke(v), sortValue, category) }
        } else {
            list.forEachIndexed { k, v -> group.addToCategory(v, category, sortValue) }
        }
    }

    override fun initItemClient(item: Item) = Unit

    companion object {

        internal var sortValues = mutableMapOf<Item, Int>()

        fun sortValueDefault(next: Int = 1) = { sort: CreativeExSortValue, category: CreativeExCategory -> sort.increment(category, next) }

        fun sortValueInt(value: Int, next: Int? = null) = { sort: CreativeExSortValue, category: CreativeExCategory -> if (next != null) sort[category] = value + next; value }

        fun sortValueFrom(item: () -> ItemConvertible, next: Int? = null) = { sort: CreativeExSortValue, category: CreativeExCategory -> val value = sortValues[item().asItem()]!!; if (next != null) sort[category] = value + next; value }

        fun <T : ItemExtraSettings> T.creativeEx(category: CreativeExCategory, sortValue: (sort: CreativeExSortValue, category: CreativeExCategory) -> Int = sortValueDefault(), set: String? = null, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) = this.add(CreativeExExtraSetting(category, sortValue, set, setKey)).let { this }

        fun <T : ItemExtraSettings> T.creativeEx(category: CreativeExCategory, set: String? = null, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) = this.add(CreativeExExtraSetting(category, sortValueDefault(), set, setKey)).let { this }

    }

}