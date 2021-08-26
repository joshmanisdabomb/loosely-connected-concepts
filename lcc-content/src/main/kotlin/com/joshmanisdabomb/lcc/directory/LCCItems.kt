package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.group.LCCGroup.LCCGroupCategory.*
import com.joshmanisdabomb.lcc.item.*
import com.joshmanisdabomb.lcc.item.AxeItem
import com.joshmanisdabomb.lcc.item.HoeItem
import com.joshmanisdabomb.lcc.item.PickaxeItem
import com.joshmanisdabomb.lcc.item.ShovelItem
import com.joshmanisdabomb.lcc.item.SwordItem
import com.joshmanisdabomb.lcc.lib.item.DefaultedColoredItem
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

    override fun regId(name: String) = LCC.id(name)

    //Test Itens
    val test_item by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TESTING))

    //Resources
    val ruby by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(0)))
    val topaz_shard by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(100)))
    val sapphire by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(200)))
    val dull_sapphire by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(210)))
    val raw_tungsten by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(300)))
    val tungsten_ingot by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(RESOURCES, sortValueInt(310)))

    //Gizmos
    val asphalt_bucket by entry(::initialiser) { BucketItem(LCCFluids.asphalt_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(GIZMOS, sortValueInt(99)))
    val oxygen_tank by entry(::initialiser) { OxygenStorageItem(12000f, Item.Settings().maxCount(1).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(GIZMOS, sortValueInt(200)))
    val plastic_bag by entry(::initialiser) { PlasticBagItem(128, Item.Settings().defaults().maxCount(1)) }
        .setProperties(ItemExtraSettings().creativeEx(GIZMOS).stackColor(DefaultedColoredItem::getTintColor))

    //Materials
    val oil_bucket by entry(::initialiser) { BucketItem(LCCFluids.oil_still, Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val fuel_bucket by entry(::initialiser) { Item(Item.Settings().defaults().maxCount(1)) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val refined_oil_bucket by entry(::initialiser) { Item(Item.Settings().defaults().maxCount(1)) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val tar_ball by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val flexible_plastic by entry(::initialiser) { PlasticItem(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS).stackColor(DefaultedColoredItem::getTintColor))
    val rigid_plastic by entry(::initialiser) { PlasticItem(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS).stackColor(DefaultedColoredItem::getTintColor))
    val silicon by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val latex_bottle by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val flexible_rubber by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val heavy_duty_rubber by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS))
    val salt by entry(::initialiser) { SaltItem(8, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(MATERIALS, sortValueInt(100, 1)))

    val rubber_boat by entry(::initialiser) { LCCBoatItem(Item.Settings().defaults().maxCount(1)) { LCCEntities.rubber_boat } }
        .setProperties(ItemExtraSettings().creativeEx(SAP_PRODUCTION, sortValueInt(130)))

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

    val hazmat_helmet by entry(::initialiser) { HazmatArmorItem(EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueInt(500)).stackColor(DefaultedColoredItem::getTintColor))
    val hazmat_chestplate by entry(::initialiser) { HazmatTankArmorItem(EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::hazmat_helmet)).stackColor(DefaultedColoredItem::getTintColor))
    val hazmat_leggings by entry(::initialiser) { HazmatArmorItem(EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::hazmat_helmet)).stackColor(DefaultedColoredItem::getTintColor))
    val hazmat_boots by entry(::initialiser) { HazmatArmorItem(EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(TOOLS, sortValueFrom(::hazmat_helmet)).stackColor(DefaultedColoredItem::getTintColor))

    val deadwood_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.DEADWOOD, Item.Settings().defaults(), recipePriority = 1) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(2900, 1)))
    val deadwood_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.DEADWOOD, Item.Settings().defaults(), recipePriority = 1) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val deadwood_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.DEADWOOD, Item.Settings().defaults(), recipePriority = 1) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val deadwood_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.DEADWOOD, Item.Settings().defaults(), recipePriority = 1) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val deadwood_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.DEADWOOD, Item.Settings().defaults(), recipePriority = 1) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val fortstone_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.FORTSTONE, Item.Settings().defaults(), attackSpeed = -3.0f) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(3900, 1)))
    val fortstone_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.FORTSTONE, Item.Settings().defaults(), attackSpeed = -3.4f) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val fortstone_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.FORTSTONE, Item.Settings().defaults(), attackSpeed = -3.6f) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val fortstone_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.FORTSTONE, Item.Settings().defaults(), attackSpeed = -3.6f) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val fortstone_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.FORTSTONE, Item.Settings().defaults(), attackSpeed = -2.0f) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))

    val rusty_iron_sword by entry(::initialiser) { SwordItem(LCCToolMaterials.RUSTY_IRON, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(4900, 1)))
    val rusty_iron_pickaxe by entry(::initialiser) { PickaxeItem(LCCToolMaterials.RUSTY_IRON, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_shovel by entry(::initialiser) { ShovelItem(LCCToolMaterials.RUSTY_IRON, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_axe by entry(::initialiser) { AxeItem(LCCToolMaterials.RUSTY_IRON, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_hoe by entry(::initialiser) { HoeItem(LCCToolMaterials.RUSTY_IRON, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_helmet by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUSTY_IRON, EquipmentSlot.HEAD, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_chestplate by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUSTY_IRON, EquipmentSlot.CHEST, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_leggings by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUSTY_IRON, EquipmentSlot.LEGS, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val rusty_iron_boots by entry(::initialiser) { ArmorItem(LCCArmorMaterials.RUSTY_IRON, EquipmentSlot.FEET, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))

    val enhancing_dust_alpha by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val enhancing_dust_beta by entry(::initialiser) { object : Item(Item.Settings().defaults()) {
        override fun hasGlint(stack: ItemStack) = true
    } }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val enhancing_dust_omega by entry(::initialiser) { object : Item(Item.Settings().defaults()) {
        override fun hasGlint(stack: ItemStack) = true
    } }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    //TODO enhancing dust for imbuing weapons with poison from stinger, teleporting with obelisk orbs (beta), applying enchantments 1 level higher than max (omega), crafting heart containers

    //Special
    val gauntlet by entry(::initialiser) { GauntletItem(Item.Settings().maxCount(1).rarity(Rarity.EPIC).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(SPECIAL))

    //Power
    val turbine_blades by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(POWER, sortValueInt(99)))
    val nuclear_fuel by entry(::initialiser) { RadioactiveItem(2, 2, Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(POWER, sortValueInt(10)))
    val redstone_battery by entry(::initialiser) { BatteryItem(LooseEnergy.fromCoals(4f), Item.Settings().maxCount(1).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(POWER, sortValueInt(5001)).stackColor(BatteryItem::getTintColor))

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

    val radiation_detector by entry(::initialiser) { RadiationDetectorItem(LooseEnergy.toStandard(8000f), Item.Settings().defaults().maxCount(1)) }
        .setProperties(ItemExtraSettings().creativeEx(NUCLEAR, sortValueInt(1000)).modelPredicate(LCC.id("winter")) { (it as RadiationDetectorItem)::getWinterPredicate })

    //Health
    val heart_half by entryMap(::initialiser, *HeartType.values()) { HeartItem(it, 1.0F, Item.Settings().defaults()) }
        .setPropertySupplier { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }
    val heart_full by entryMap(::initialiser, *HeartType.values()) { HeartItem(it, 2.0F, Item.Settings().defaults()) }
        .setPropertySupplier { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }
    val heart_container by entryMap(::initialiser, *HeartType.values().filter { it.container }.toTypedArray()) { HeartContainerItem(it, 2.0F, Item.Settings().defaults()) }
        .setPropertySupplier { ItemExtraSettings().creativeEx(HEALTH, sortValueInt(it.ordinal)) }

    //Wasteland
    val iron_oxide by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(4000, 1)))
    val iron_oxide_nugget by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
    val deadwood_boat by entry(::initialiser) { LCCBoatItem(Item.Settings().defaults().maxCount(1)) { LCCEntities.deadwood_boat } }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(2030)))

    val crowbar by entry(::initialiser) { CrowbarItem(Item.Settings().maxDamage(72).defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(4050, 1)))

    val tongue_tissue by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(80000, 1)))

    val stinger by entry(::initialiser) { StingerItem(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(90100, 1)))

    val altar_challenge_key by entry(::initialiser) { Item(Item.Settings().defaults()) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))

    val baby_skeleton_spawn_egg by entry(::initialiser) { VariableTintSpawnEggItem(LCCEntities.baby_skeleton, Item.Settings().defaults(), 0xC1C1C1, 0x494949, 0x684E1E) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND, sortValueInt(100000, 1)))
        .addTags("wasteland_spawn_egg")
    val consumer_spawn_egg by entry(::initialiser) { VariableTintSpawnEggItem(LCCEntities.consumer, Item.Settings().defaults(), 0x444444, 0x11EE11, 0xB78987) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
        .addTags("wasteland_spawn_egg")
    val wasp_spawn_egg by entry(::initialiser) { VariableTintSpawnEggItem(LCCEntities.wasp, Item.Settings().defaults(), 0xEDC343, 0x43241B, 0x4E9331) }
        .setProperties(ItemExtraSettings().creativeEx(WASTELAND))
        .addTags("wasteland_spawn_egg")

    //IDEA forget me not, forget villager trades and entity hostility

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