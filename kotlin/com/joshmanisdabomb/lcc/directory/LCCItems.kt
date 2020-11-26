package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.creativeex.CreativeExCategory
import com.joshmanisdabomb.creativeex.CreativeExGroup
import com.joshmanisdabomb.creativeex.CreativeExSetKey
import com.joshmanisdabomb.lcc.concepts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCItems.ExtraSettings.Companion.intSortValue
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import com.joshmanisdabomb.lcc.item.GauntletItem
import com.joshmanisdabomb.lcc.item.HeartContainerItem
import com.joshmanisdabomb.lcc.item.HeartItem
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Rarity
import net.minecraft.util.Util
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry

object LCCItems : RegistryDirectory<Item, LCCItems.ExtraSettings>() {

    override val _registry by lazy { Registry.ITEM }

    val test_item by create(ExtraSettings().creativeEx(TESTING)) { Item(Item.Settings().defaults()) }

    val ruby by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(0))) { Item(Item.Settings().defaults()) } //TODO from time rift
    val topaz by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(100))) { Item(Item.Settings().defaults()) } //TODO from fishing
    val sapphire by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(200))) { Item(Item.Settings().defaults()) } //TODO from dungeon, temple, etc chests
    val uranium by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(300))) { Item(Item.Settings().defaults()) } //TODO very rare ore, more common in wasteland
    val uranium_nugget by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(300))) { Item(Item.Settings().defaults()) }
    val enriched_uranium by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(400))) { Item(Item.Settings().defaults()) }
    val enriched_uranium_nugget by create(ExtraSettings().creativeEx(RESOURCES, intSortValue(400))) { Item(Item.Settings().defaults()) }

    val gauntlet by create(ExtraSettings().creativeEx(SPECIAL)) { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }

    val heart_half by createMap(*HeartType.values(), propertySupplier = { ExtraSettings().creativeEx(HEALTH, intSortValue(it.ordinal)) }) { key, name, properties -> HeartItem(key, 1.0F, Item.Settings().defaults()) }
    val heart_full by createMap(*HeartType.values(), propertySupplier = { ExtraSettings().creativeEx(HEALTH, intSortValue(it.ordinal)) }) { key, name, properties -> HeartItem(key, 2.0F, Item.Settings().defaults()) }
    val heart_container by createMap(*HeartType.values().filter { it.container }.toTypedArray(), propertySupplier = { ExtraSettings().creativeEx(HEALTH, intSortValue(it.ordinal)) }) { key, name, properties -> HeartContainerItem(key, 2.0F, Item.Settings().defaults()) }

    val oil_bucket by create(ExtraSettings().creativeEx(WASTELAND, sortValue = intSortValue(100))) { BucketItem(LCCFluids.oil_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }

    //

    override fun register(key: String, thing: Item, properties: ExtraSettings) = super.register(key, thing, properties).apply { properties.initItem(thing) }

    override fun getDefaultProperty() = ExtraSettings()

    fun Item.Settings.defaults(): Item.Settings = this.group(LCCGroups.group)

    open class ExtraSettings internal constructor (private var category: CreativeExCategory? = null, private var sortValue: (default: Int, item: Item) -> Int = defaultSortValue(), private var set: String? = null, private var setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) {

        open fun creativeEx(category: CreativeExCategory, sortValue: (default: Int, item: Item) -> Int = defaultSortValue(), set: String? = null, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null): ExtraSettings {
            this.category = category
            this.set = set
            this.setKey = setKey
            this.sortValue = sortValue
            return this
        }

        open fun initItem(item: Item) {
            if (category == null) return
            val group = item.group as? CreativeExGroup ?: return
            val list = Util.make(DefaultedList.of<ItemStack>()) { item.appendStacks(item.group, it) }
            if (set != null && setKey != null) {
                list.forEachIndexed { k, v -> group.addToSet(v, set!!, setKey!!.invoke(v), sortValue(_sortValue++, item), category!!) }
            } else {
                list.forEachIndexed { k, v -> group.addToCategory(v, category!!, sortValue(_sortValue++, item)) }
            }
        }

        companion object {
            internal var _sortValue = 0

            fun defaultSortValue() = { default: Int, _: Item -> default }

            fun intSortValue(sortValue: Int) = { _: Int, _: Item -> sortValue }
        }

    }

}