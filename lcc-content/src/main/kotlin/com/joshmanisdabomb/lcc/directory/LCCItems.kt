package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import com.joshmanisdabomb.lcc.item.*
import com.joshmanisdabomb.lcc.item.AxeItem
import com.joshmanisdabomb.lcc.item.HoeItem
import com.joshmanisdabomb.lcc.item.PickaxeItem
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.creativeEx
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.sortValueFrom
import com.joshmanisdabomb.lcc.settings.CreativeExExtraSetting.Companion.sortValueInt
import com.joshmanisdabomb.lcc.settings.ItemExtraSettings
import com.joshmanisdabomb.lcc.settings.ModelPredicateExtraSetting.Companion.modelPredicate
import com.joshmanisdabomb.lcc.settings.StackColorExtraSetting.Companion.stackColor
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.*
import net.minecraft.util.Rarity

object LCCItems : ItemDirectory() {

    //TODO dispenser behaviours

    override fun id(path: String) = LCC.id(path)

    //Test Itens
    val test_item by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TESTING))

    //Resources
    val ruby by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(0)))
    val topaz_shard by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(100)))
    val sapphire by entry(::initialiser) { Item(Item.Settings().defaults()) } //TODO from dungeon, temple, etc chests
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(200)))

    //Gizmos
    val asphalt_bucket by entry(::initialiser) { BucketItem(LCCFluids.asphalt_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(GIZMOS, sortValueInt(99)))

    //Materials
    val silicon by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))

    //Tools
    val ruby_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val ruby_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby)))
    val topaz_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val topaz_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard)))
    val emerald_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueInt(150)))
    val emerald_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val emerald_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword)))
    val sapphire_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val sapphire_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire)))
    val amethyst_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueInt(250)))
    val amethyst_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))
    val amethyst_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword)))

    //Special
    val gauntlet by entry(::initialiser) { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(SPECIAL))

    //Power
    val turbine_blades by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(POWER, sortValueInt(3)))
    val redstone_battery by entry(::initialiser) { BatteryItem(LooseEnergy.fromCoals(4f), Item.Settings().maxCount(1).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(POWER, sortValueInt(100)).stackColor(BatteryItem::getTintColor))

    //Nuclear
    val uranium by entry(::initialiser) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(10)))
    val uranium_nugget by entry(::initialiser) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(9)))
    val enriched_uranium by entry(::initialiser) { RadioactiveItem(1, 1, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(20)))
    val enriched_uranium_nugget by entry(::initialiser) { RadioactiveItem(1, 1, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(19)))
    val heavy_uranium by entry(::initialiser) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(30)))
    val heavy_uranium_nugget by entry(::initialiser) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(29)))

    //Health
    val heart_half by entryMap(::initialiser, *HeartType.values()) { HeartItem(it, 1.0F, Item.Settings().defaults()) }
        .setPropertySupplier { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }
    val heart_full by entryMap(::initialiser, *HeartType.values()) { HeartItem(it, 2.0F, Item.Settings().defaults()) }
        .setPropertySupplier { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }
    val heart_container by entryMap(::initialiser, *HeartType.values().filter { it.container }.toTypedArray()) { HeartContainerItem(it, 2.0F, Item.Settings().defaults()) }
        .setPropertySupplier { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }

    //Wasteland
    val oil_bucket by entry(::initialiser) { BucketItem(LCCFluids.oil_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(100)))

    //Nostalgia
    val simulation_fabric by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA, sortValueInt(-2)))
    val classic_leather_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA, sortValueInt(1000, 1)))
    val classic_leather_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_leather_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_leather_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_studded_leather_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_studded_leather_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_studded_leather_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_studded_leather_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_raw_porkchop by entry(::initialiser) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.PORKCHOP)) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_cooked_porkchop by entry(::initialiser) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.COOKED_PORKCHOP)) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_raw_fish by entry(::initialiser) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.COD)) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_cooked_fish by entry(::initialiser) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.COOKED_COD)) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_apple by entry(::initialiser) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.APPLE)) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))
    val classic_golden_apple by entry(::initialiser) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponent.Builder().hunger(20).saturationModifier(1.2F).build())) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA))

    val quiver by entry(::initialiser) { QuiverItem(192, Item.Settings().defaults().maxCount(1)) }
        .setProperties(ItemExtraSettings().creativeEx(NOSTALGIA).modelPredicate(LCC.id("filled")) { (it as BagItem)::getBagPredicate })

    //TODO pills

    //IDEA calendar that can be put on item frame

    fun Item.Settings.defaults(): Item.Settings = this.group(LCCGroups.group)

}