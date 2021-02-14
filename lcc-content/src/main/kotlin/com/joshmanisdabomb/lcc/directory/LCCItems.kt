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
    val test_item by create(ItemExtraSettings().creativeEx(TESTING)) { Item(Item.Settings().defaults()) }

    //Resources
    val ruby by create(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(0))) { Item(Item.Settings().defaults()) }
    val topaz_shard by create(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(100))) { Item(Item.Settings().defaults()) }
    val sapphire by create(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(200))) { Item(Item.Settings().defaults()) } //TODO from dungeon, temple, etc chests

    //Gizmos
    val asphalt_bucket by create(ItemExtraSettings().creativeEx(GIZMOS, sortValueInt(99))) { BucketItem(LCCFluids.asphalt_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }

    //Materials
    val silicon by create(ItemExtraSettings().creativeEx(MATERIALS)) { Item(Item.Settings().defaults()) }

    //Tools
    val ruby_sword by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { SwordItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_pickaxe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { PickaxeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_shovel by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ShovelItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_axe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { AxeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_hoe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { HoeItem(LCCToolMaterials.RUBY, Item.Settings().defaults()) }
    val ruby_helmet by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val ruby_chestplate by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val ruby_leggings by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val ruby_boots by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::ruby))) { ArmorItem(LCCArmorMaterials.RUBY, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val topaz_sword by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { SwordItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_pickaxe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { PickaxeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_shovel by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { ShovelItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_axe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { AxeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_hoe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { HoeItem(LCCToolMaterials.TOPAZ, Item.Settings().defaults()) }
    val topaz_helmet by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val topaz_chestplate by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val topaz_leggings by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val topaz_boots by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::topaz_shard))) { ArmorItem(LCCArmorMaterials.TOPAZ, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val emerald_sword by create(ItemExtraSettings().creativeEx(TOOLS, sortValueInt(150))) { SwordItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_pickaxe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { PickaxeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_shovel by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ShovelItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_axe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { AxeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_hoe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { HoeItem(LCCToolMaterials.EMERALD, Item.Settings().defaults()) }
    val emerald_helmet by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val emerald_chestplate by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val emerald_leggings by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val emerald_boots by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::emerald_sword))) { ArmorItem(LCCArmorMaterials.EMERALD, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val sapphire_sword by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { SwordItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_pickaxe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { PickaxeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_shovel by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ShovelItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_axe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { AxeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_hoe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { HoeItem(LCCToolMaterials.SAPPHIRE, Item.Settings().defaults()) }
    val sapphire_helmet by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val sapphire_chestplate by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val sapphire_leggings by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val sapphire_boots by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::sapphire))) { ArmorItem(LCCArmorMaterials.SAPPHIRE, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val amethyst_sword by create(ItemExtraSettings().creativeEx(TOOLS, sortValueInt(250))) { SwordItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_pickaxe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { PickaxeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_shovel by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ShovelItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_axe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { AxeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_hoe by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { HoeItem(LCCToolMaterials.AMETHYST, Item.Settings().defaults()) }
    val amethyst_helmet by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val amethyst_chestplate by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val amethyst_leggings by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val amethyst_boots by create(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::amethyst_sword))) { ArmorItem(LCCArmorMaterials.AMETHYST, EquipmentSlot.FEET, Item.Settings().defaults()) }

    //Special
    val gauntlet by create(ItemExtraSettings().creativeEx(SPECIAL)) { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }

    //Power
    val turbine_blades by create(ItemExtraSettings().creativeEx(POWER, sortValueInt(3))) { Item(Item.Settings().defaults()) }
    val redstone_battery by create(ItemExtraSettings().creativeEx(POWER, sortValueInt(100)).stackColor(BatteryItem::getTintColor)) { BatteryItem(LooseEnergy.fromCoals(4f), Item.Settings().maxCount(1).defaults()) }

    //Nuclear
    val uranium by create(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(10))) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
    val uranium_nugget by create(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(9))) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
    val enriched_uranium by create(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(20))) { RadioactiveItem(1, 1, Item.Settings().defaults()) }
    val enriched_uranium_nugget by create(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(19))) { RadioactiveItem(1, 1, Item.Settings().defaults()) }
    val heavy_uranium by create(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(30))) { RadioactiveItem(1, 0, Item.Settings().defaults()) }
    val heavy_uranium_nugget by create(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(29))) { RadioactiveItem(1, 0, Item.Settings().defaults()) }

    //Health
    val heart_half by createMap(*HeartType.values(), propertySupplier = { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }) { key, name, properties -> HeartItem(key, 1.0F, Item.Settings().defaults()) }
    val heart_full by createMap(*HeartType.values(), propertySupplier = { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }) { key, name, properties -> HeartItem(key, 2.0F, Item.Settings().defaults()) }
    val heart_container by createMap(*HeartType.values().filter { it.container }.toTypedArray(), propertySupplier = { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }) { key, name, properties -> HeartContainerItem(key, 2.0F, Item.Settings().defaults()) }

    //Wasteland
    val oil_bucket by create(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(100))) { BucketItem(LCCFluids.oil_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }

    //Nostalgia
    val simulation_fabric by create(ItemExtraSettings().creativeEx(NOSTALGIA, sortValueInt(-2))) { Item(Item.Settings().defaults()) }
    val classic_leather_helmet by create(ItemExtraSettings().creativeEx(NOSTALGIA, sortValueInt(1000, 1))) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val classic_leather_chestplate by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val classic_leather_leggings by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val classic_leather_boots by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_LEATHER, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val classic_studded_leather_helmet by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.HEAD, Item.Settings().defaults()) }
    val classic_studded_leather_chestplate by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.CHEST, Item.Settings().defaults()) }
    val classic_studded_leather_leggings by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.LEGS, Item.Settings().defaults()) }
    val classic_studded_leather_boots by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ArmorItem(LCCArmorMaterials.CLASSIC_STUDDED_LEATHER, EquipmentSlot.FEET, Item.Settings().defaults()) }
    val classic_raw_porkchop by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.PORKCHOP)) }
    val classic_cooked_porkchop by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.COOKED_PORKCHOP)) }
    val classic_raw_fish by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.COD)) }
    val classic_cooked_fish by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.COOKED_COD)) }
    val classic_apple by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponents.APPLE)) }
    val classic_golden_apple by create(ItemExtraSettings().creativeEx(NOSTALGIA)) { ClassicFoodItem(Item.Settings().defaults().maxCount(1).food(FoodComponent.Builder().hunger(20).saturationModifier(1.2F).build())) }

    val quiver by create(ItemExtraSettings().creativeEx(NOSTALGIA).modelPredicate(LCC.id("filled")) { (it as BagItem)::getBagPredicate }) { QuiverItem(192, Item.Settings().defaults().maxCount(1)) }

    //TODO pills

    //IDEA calendar that can be put on item frame

    fun Item.Settings.defaults(): Item.Settings = this.group(LCCGroups.group)

}