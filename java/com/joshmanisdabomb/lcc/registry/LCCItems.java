package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.item.*;
import com.joshmanisdabomb.lcc.item.render.teisr.TEISRGauntlet;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;

public abstract class LCCItems {

	public static final ArrayList<Item> all = new ArrayList<>();

	//Test Items
	public static Item test_item;

	//Resources
	public static Item ruby;
	public static Item topaz;
	public static Item sapphire;
	public static Item amethyst;
	public static Item uranium_nugget;
	public static Item uranium;
	public static Item enriched_uranium_nugget;
	public static Item enriched_uranium;

	//Tools
	public static Item ruby_sword;
	public static Item ruby_pickaxe;
	public static Item ruby_shovel;
	public static Item ruby_axe;
	public static Item ruby_hoe;
	public static Item ruby_helmet;
	public static Item ruby_chestplate;
	public static Item ruby_leggings;
	public static Item ruby_boots;

	public static Item topaz_sword;
	public static Item topaz_pickaxe;
	public static Item topaz_shovel;
	public static Item topaz_axe;
	public static Item topaz_hoe;
	public static Item topaz_helmet;
	public static Item topaz_chestplate;
	public static Item topaz_leggings;
	public static Item topaz_boots;

	public static Item emerald_sword;
	public static Item emerald_pickaxe;
	public static Item emerald_shovel;
	public static Item emerald_axe;
	public static Item emerald_hoe;
	public static Item emerald_helmet;
	public static Item emerald_chestplate;
	public static Item emerald_leggings;
	public static Item emerald_boots;

	public static Item sapphire_sword;
	public static Item sapphire_pickaxe;
	public static Item sapphire_shovel;
	public static Item sapphire_axe;
	public static Item sapphire_hoe;
	public static Item sapphire_helmet;
	public static Item sapphire_chestplate;
	public static Item sapphire_leggings;
	public static Item sapphire_boots;

	public static Item amethyst_sword;
	public static Item amethyst_pickaxe;
	public static Item amethyst_shovel;
	public static Item amethyst_axe;
	public static Item amethyst_hoe;
	public static Item amethyst_helmet;
	public static Item amethyst_chestplate;
	public static Item amethyst_leggings;
	public static Item amethyst_boots;

	//Power
	public static Item gauntlet;

	//Health
	public static Item red_heart_half;
	public static Item red_heart;
	public static Item red_heart_container;

	public static Item iron_heart_half;
	public static Item iron_heart;
	public static Item iron_heart_container;

	public static Item crystal_heart_half;
	public static Item crystal_heart;
	public static Item crystal_heart_container;

	public static Item temporary_heart_half;
	public static Item temporary_heart;

	public static void init(Register<Item> e) {
		//Test Items
		all.add(test_item = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "test_item"));

		//Resources
		all.add(ruby = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby"));
		all.add(topaz = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz"));
		all.add(sapphire = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire"));
		all.add(amethyst = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst"));
		all.add(uranium_nugget = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "uranium_nugget"));
		all.add(uranium = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "uranium"));
		all.add(enriched_uranium_nugget = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "enriched_uranium_nugget"));
		all.add(enriched_uranium = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "enriched_uranium"));

		//Tools
		all.add(ruby_sword = new ItemSword(LCCItemTier.RUBY, 3, -2.4F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_sword"));
		all.add(ruby_pickaxe = new ItemSillySubclassPickaxe(LCCItemTier.RUBY, 1, -2.8F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_pickaxe"));
		all.add(ruby_shovel = new ItemSpade(LCCItemTier.RUBY, 1.5F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_shovel"));
		all.add(ruby_axe = new ItemSillySubclassAxe(LCCItemTier.RUBY, 5.0F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_axe"));
		all.add(ruby_hoe = new ItemHoe(LCCItemTier.RUBY, 0.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_hoe"));
		all.add(ruby_helmet = new ItemCustomArmor(LCCArmorMaterial.RUBY, EntityEquipmentSlot.HEAD, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_helmet"));
		all.add(ruby_chestplate = new ItemCustomArmor(LCCArmorMaterial.RUBY, EntityEquipmentSlot.CHEST, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_chestplate"));
		all.add(ruby_leggings = new ItemCustomArmor(LCCArmorMaterial.RUBY, EntityEquipmentSlot.LEGS, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_leggings"));
		all.add(ruby_boots = new ItemCustomArmor(LCCArmorMaterial.RUBY, EntityEquipmentSlot.FEET, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_boots"));

		all.add(topaz_sword = new ItemSword(LCCItemTier.TOPAZ, 3, -2.4F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_sword"));
		all.add(topaz_pickaxe = new ItemSillySubclassPickaxe(LCCItemTier.TOPAZ, 1, -2.8F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_pickaxe"));
		all.add(topaz_shovel = new ItemSpade(LCCItemTier.TOPAZ, 1.5F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_shovel"));
		all.add(topaz_axe = new ItemSillySubclassAxe(LCCItemTier.TOPAZ, 5.0F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_axe"));
		all.add(topaz_hoe = new ItemHoe(LCCItemTier.TOPAZ, 0.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_hoe"));
		all.add(topaz_helmet = new ItemCustomArmor(LCCArmorMaterial.TOPAZ, EntityEquipmentSlot.HEAD, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_helmet"));
		all.add(topaz_chestplate = new ItemCustomArmor(LCCArmorMaterial.TOPAZ, EntityEquipmentSlot.CHEST, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_chestplate"));
		all.add(topaz_leggings = new ItemCustomArmor(LCCArmorMaterial.TOPAZ, EntityEquipmentSlot.LEGS, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_leggings"));
		all.add(topaz_boots = new ItemCustomArmor(LCCArmorMaterial.TOPAZ, EntityEquipmentSlot.FEET, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_boots"));

		all.add(emerald_sword = new ItemSword(LCCItemTier.EMERALD, 3, -2.4F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_sword"));
		all.add(emerald_pickaxe = new ItemSillySubclassPickaxe(LCCItemTier.EMERALD, 1, -2.8F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_pickaxe"));
		all.add(emerald_shovel = new ItemSpade(LCCItemTier.EMERALD, 1.5F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_shovel"));
		all.add(emerald_axe = new ItemSillySubclassAxe(LCCItemTier.EMERALD, 5.0F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_axe"));
		all.add(emerald_hoe = new ItemHoe(LCCItemTier.EMERALD, 0.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_hoe"));
		all.add(emerald_helmet = new ItemCustomArmor(LCCArmorMaterial.EMERALD, EntityEquipmentSlot.HEAD, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_helmet"));
		all.add(emerald_chestplate = new ItemCustomArmor(LCCArmorMaterial.EMERALD, EntityEquipmentSlot.CHEST, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_chestplate"));
		all.add(emerald_leggings = new ItemCustomArmor(LCCArmorMaterial.EMERALD, EntityEquipmentSlot.LEGS, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_leggings"));
		all.add(emerald_boots = new ItemCustomArmor(LCCArmorMaterial.EMERALD, EntityEquipmentSlot.FEET, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_boots"));

		all.add(sapphire_sword = new ItemSword(LCCItemTier.SAPPHIRE, 3, -2.4F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_sword"));
		all.add(sapphire_pickaxe = new ItemSillySubclassPickaxe(LCCItemTier.SAPPHIRE, 1, -2.8F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_pickaxe"));
		all.add(sapphire_shovel = new ItemSpade(LCCItemTier.SAPPHIRE, 1.5F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_shovel"));
		all.add(sapphire_axe = new ItemSillySubclassAxe(LCCItemTier.SAPPHIRE, 5.0F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_axe"));
		all.add(sapphire_hoe = new ItemHoe(LCCItemTier.SAPPHIRE, 0.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_hoe"));
		all.add(sapphire_helmet = new ItemCustomArmor(LCCArmorMaterial.SAPPHIRE, EntityEquipmentSlot.HEAD, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_helmet"));
		all.add(sapphire_chestplate = new ItemCustomArmor(LCCArmorMaterial.SAPPHIRE, EntityEquipmentSlot.CHEST, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_chestplate"));
		all.add(sapphire_leggings = new ItemCustomArmor(LCCArmorMaterial.SAPPHIRE, EntityEquipmentSlot.LEGS, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_leggings"));
		all.add(sapphire_boots = new ItemCustomArmor(LCCArmorMaterial.SAPPHIRE, EntityEquipmentSlot.FEET, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_boots"));

		all.add(amethyst_sword = new ItemSword(LCCItemTier.AMETHYST, 3, -2.4F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_sword"));
		all.add(amethyst_pickaxe = new ItemSillySubclassPickaxe(LCCItemTier.AMETHYST, 1, -2.8F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_pickaxe"));
		all.add(amethyst_shovel = new ItemSpade(LCCItemTier.AMETHYST, 1.5F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_shovel"));
		all.add(amethyst_axe = new ItemSillySubclassAxe(LCCItemTier.AMETHYST, 5.0F, -3.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_axe"));
		all.add(amethyst_hoe = new ItemHoe(LCCItemTier.AMETHYST, 0.0F, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_hoe"));
		all.add(amethyst_helmet = new ItemCustomArmor(LCCArmorMaterial.AMETHYST, EntityEquipmentSlot.HEAD, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_helmet"));
		all.add(amethyst_chestplate = new ItemCustomArmor(LCCArmorMaterial.AMETHYST, EntityEquipmentSlot.CHEST, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_chestplate"));
		all.add(amethyst_leggings = new ItemCustomArmor(LCCArmorMaterial.AMETHYST, EntityEquipmentSlot.LEGS, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_leggings"));
		all.add(amethyst_boots = new ItemCustomArmor(LCCArmorMaterial.AMETHYST, EntityEquipmentSlot.FEET, (new Item.Properties()).group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_boots"));

		//Power
		all.add(gauntlet = new ItemGauntlet(new Item.Properties().group(LCC.itemGroup).setTEISR(() -> TEISRGauntlet::new)).setRegistryName(LCC.MODID, "gauntlet"));

		//Health
		all.add(red_heart_half = new ItemHeart(HeartsFunctionality.HeartType.RED, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "red_heart_half"));
		all.add(red_heart = new ItemHeart(HeartsFunctionality.HeartType.RED, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "red_heart"));
		all.add(red_heart_container = new ItemHeartContainer(HeartsFunctionality.HeartType.RED, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "red_heart_container"));
		all.add(iron_heart_half = new ItemHeart(HeartsFunctionality.HeartType.IRON, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "iron_heart_half"));
		all.add(iron_heart = new ItemHeart(HeartsFunctionality.HeartType.IRON, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "iron_heart"));
		all.add(iron_heart_container = new ItemHeartContainer(HeartsFunctionality.HeartType.IRON, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "iron_heart_container"));
		all.add(crystal_heart_half = new ItemHeart(HeartsFunctionality.HeartType.CRYSTAL, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "crystal_heart_half"));
		all.add(crystal_heart = new ItemHeart(HeartsFunctionality.HeartType.CRYSTAL, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "crystal_heart"));
		all.add(crystal_heart_container = new ItemHeartContainer(HeartsFunctionality.HeartType.CRYSTAL, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "crystal_heart_container"));
		all.add(temporary_heart_half = new ItemHeart(HeartsFunctionality.HeartType.TEMPORARY, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "temporary_heart_half"));
		all.add(temporary_heart = new ItemHeart(HeartsFunctionality.HeartType.TEMPORARY, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "temporary_heart"));
	}
	
}
