package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.creativeex.CreativeExCategory
import com.joshmanisdabomb.creativeex.CreativeExGroup
import com.joshmanisdabomb.creativeex.CreativeExSetKey
import com.joshmanisdabomb.lcc.concepts.heart.HeartType
import com.joshmanisdabomb.lcc.directory.LCCItems.ExtraSettings.Companion.sortValueFrom
import com.joshmanisdabomb.lcc.directory.LCCItems.ExtraSettings.Companion.sortValueInt
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import com.joshmanisdabomb.lcc.item.*
import com.joshmanisdabomb.lcc.item.AxeItem
import com.joshmanisdabomb.lcc.item.HoeItem
import com.joshmanisdabomb.lcc.item.PickaxeItem
import net.minecraft.block.Block
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.*
import net.minecraft.util.Rarity
import net.minecraft.util.Util
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry
import kotlin.reflect.KProperty0

object LCCItems : RegistryDirectory<Item, LCCItems.ExtraSettings>() {

    override val _registry by lazy { Registry.ITEM }

    //Test Itens
    val test_item by create(ExtraSettings().creativeEx(TESTING)) { Item(Item.Settings().defaults()) }

    //Resources
    val ruby by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(0))) { Item(Item.Settings().defaults()) } //TODO from time rift
    val topaz by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(100))) { Item(Item.Settings().defaults()) } //TODO from fishing
    val sapphire by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(200))) { Item(Item.Settings().defaults()) } //TODO from dungeon, temple, etc chests
    val uranium by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(300))) { Item(Item.Settings().defaults()) }
    val uranium_nugget by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(::uranium))) { Item(Item.Settings().defaults()) }
    val enriched_uranium by create(ExtraSettings().creativeEx(RESOURCES, sortValueInt(400))) { Item(Item.Settings().defaults()) }
    val enriched_uranium_nugget by create(ExtraSettings().creativeEx(RESOURCES, sortValueFrom(::enriched_uranium))) { Item(Item.Settings().defaults()) }

    //Gizmos
    val asphalt_bucket by create(ExtraSettings().creativeEx(GIZMOS, sortValueInt(99))) { BucketItem(LCCFluids.asphalt_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }

    //Tools
    val ruby_sword by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { SwordItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_pickaxe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { PickaxeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_shovel by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ShovelItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_axe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { AxeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_hoe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { HoeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_helmet by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val ruby_chestplate by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val ruby_leggings by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val ruby_boots by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val topaz_sword by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { SwordItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_pickaxe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { PickaxeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_shovel by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { ShovelItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_axe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { AxeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_hoe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { HoeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_helmet by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val topaz_chestplate by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val topaz_leggings by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val topaz_boots by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val emerald_sword by create(ExtraSettings().creativeEx(TOOLS, sortValueInt(150))) { SwordItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_pickaxe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { PickaxeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_shovel by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ShovelItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_axe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { AxeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_hoe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { HoeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_helmet by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val emerald_chestplate by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val emerald_leggings by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val emerald_boots by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val sapphire_sword by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { SwordItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_pickaxe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { PickaxeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_shovel by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ShovelItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_axe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { AxeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_hoe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { HoeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_helmet by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val sapphire_chestplate by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val sapphire_leggings by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val sapphire_boots by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val amethyst_sword by create(ExtraSettings().creativeEx(TOOLS, sortValueInt(250))) { SwordItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_pickaxe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { PickaxeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_shovel by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ShovelItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_axe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { AxeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_hoe by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { HoeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_helmet by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val amethyst_chestplate by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val amethyst_leggings by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val amethyst_boots by create(ExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.FEET, Item.Settings().defaults()) }

    //Special
    val gauntlet by create(ExtraSettings().creativeEx(SPECIAL)) { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }

    //Health
    val heart_half by createMap(*HeartType.values(), propertySupplier = { ExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }) { key, name, properties -> HeartItem(key, 1.0F, Item.Settings().defaults()) }
    val heart_full by createMap(*HeartType.values(), propertySupplier = { ExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }) { key, name, properties -> HeartItem(key, 2.0F, Item.Settings().defaults()) }
    val heart_container by createMap(*HeartType.values().filter { it.container }.toTypedArray(), propertySupplier = { ExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }) { key, name, properties -> HeartContainerItem(key, 2.0F, Item.Settings().defaults()) }

    //Wasteland
    val oil_bucket by create(ExtraSettings().creativeEx(WASTELAND, sortValueInt(100))) { BucketItem(LCCFluids.oil_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }

    //Nostalgia
    val simulation_fabric by create(ExtraSettings().creativeEx(NOSTALGIA, sortValueInt(0))) { Item(Item.Settings().defaults()) }

    //TODO pills

    override fun register(key: String, thing: Item, properties: ExtraSettings) = super.register(key, thing, properties).apply { properties.initItem(thing) }

    override fun getDefaultProperty() = ExtraSettings()

    fun Item.Settings.defaults(): Item.Settings = this.group(LCCGroups.group)

    open class ExtraSettings internal constructor (private var category: CreativeExCategory? = null, private var sortValue: (default: Int, item: Item) -> Int = sortValueDefault(), private var set: String? = null, private var setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null) {

        open fun creativeEx(category: CreativeExCategory, sortValue: (default: Int, item: Item) -> Int = sortValueDefault(), set: String? = null, setKey: ((stack: ItemStack) -> CreativeExSetKey)? = null): ExtraSettings {
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
            val sortValue = sortValue(_sortValue++, item).also { sortValues[item] = it }
            if (set != null && setKey != null) {
                list.forEachIndexed { k, v -> group.addToSet(v, set!!, setKey!!.invoke(v), sortValue, category!!) }
            } else {
                list.forEachIndexed { k, v -> group.addToCategory(v, category!!, sortValue) }
            }
        }

        companion object {

            internal var _sortValue = 0
            internal var sortValues = mutableMapOf<Item, Int>()

            fun sortValueDefault() = { default: Int, _: Item -> default }

            fun sortValueInt(sortValue: Int) = { _: Int, _: Item -> sortValue }

            fun sortValueFrom(item: KProperty0<Item>) = { _: Int, _: Item -> sortValues[item.get()]!! }

            @JvmName("sortValueFromBlock")
            fun sortValueFrom(block: KProperty0<Block>) = { _: Int, _: Item -> sortValues[block.get().asItem()]!! }

        }

    }

}