package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.SpreaderBlock;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.item.*;
import com.joshmanisdabomb.lcc.item.render.GauntletRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;
import java.util.HashMap;

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

	//Rainbow
	public static Item chromatic_core;

	//Computing
	public static HashMap<DyeColor, Item> computer_casing = new HashMap<>();
	public static HashMap<DyeColor, Item> computer = new HashMap<>();
	public static HashMap<DyeColor, Item> floppy_disk_drive = new HashMap<>();
	public static HashMap<DyeColor, Item> compact_disc_drive = new HashMap<>();
	public static HashMap<DyeColor, Item> usb_drive = new HashMap<>();

	//Spreaders
	public static Item spreader_essence;

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
		Item i;

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
		all.add(ruby_sword = new SwordItem(LCCItemTier.RUBY, 3, -2.4F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_sword"));
		all.add(ruby_pickaxe = new PickaxeItem(LCCItemTier.RUBY, 1, -2.8F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_pickaxe"));
		all.add(ruby_shovel = new ShovelItem(LCCItemTier.RUBY, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_shovel"));
		all.add(ruby_axe = new AxeItem(LCCItemTier.RUBY, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_axe"));
		all.add(ruby_hoe = new HoeItem(LCCItemTier.RUBY, 0.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_hoe"));
		all.add(ruby_helmet = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_helmet"));
		all.add(ruby_chestplate = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_chestplate"));
		all.add(ruby_leggings = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_leggings"));
		all.add(ruby_boots = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby_boots"));

		all.add(topaz_sword = new SwordItem(LCCItemTier.TOPAZ, 3, -2.4F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_sword"));
		all.add(topaz_pickaxe = new PickaxeItem(LCCItemTier.TOPAZ, 1, -2.8F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_pickaxe"));
		all.add(topaz_shovel = new ShovelItem(LCCItemTier.TOPAZ, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_shovel"));
		all.add(topaz_axe = new AxeItem(LCCItemTier.TOPAZ, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_axe"));
		all.add(topaz_hoe = new HoeItem(LCCItemTier.TOPAZ, 0.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_hoe"));
		all.add(topaz_helmet = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_helmet"));
		all.add(topaz_chestplate = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_chestplate"));
		all.add(topaz_leggings = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_leggings"));
		all.add(topaz_boots = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz_boots"));

		all.add(emerald_sword = new SwordItem(LCCItemTier.EMERALD, 3, -2.4F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_sword"));
		all.add(emerald_pickaxe = new PickaxeItem(LCCItemTier.EMERALD, 1, -2.8F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_pickaxe"));
		all.add(emerald_shovel = new ShovelItem(LCCItemTier.EMERALD, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_shovel"));
		all.add(emerald_axe = new AxeItem(LCCItemTier.EMERALD, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_axe"));
		all.add(emerald_hoe = new HoeItem(LCCItemTier.EMERALD, 0.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_hoe"));
		all.add(emerald_helmet = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_helmet"));
		all.add(emerald_chestplate = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_chestplate"));
		all.add(emerald_leggings = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_leggings"));
		all.add(emerald_boots = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "emerald_boots"));

		all.add(sapphire_sword = new SwordItem(LCCItemTier.SAPPHIRE, 3, -2.4F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_sword"));
		all.add(sapphire_pickaxe = new PickaxeItem(LCCItemTier.SAPPHIRE, 1, -2.8F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_pickaxe"));
		all.add(sapphire_shovel = new ShovelItem(LCCItemTier.SAPPHIRE, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_shovel"));
		all.add(sapphire_axe = new AxeItem(LCCItemTier.SAPPHIRE, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_axe"));
		all.add(sapphire_hoe = new HoeItem(LCCItemTier.SAPPHIRE, 0.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_hoe"));
		all.add(sapphire_helmet = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_helmet"));
		all.add(sapphire_chestplate = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_chestplate"));
		all.add(sapphire_leggings = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_leggings"));
		all.add(sapphire_boots = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire_boots"));

		all.add(amethyst_sword = new SwordItem(LCCItemTier.AMETHYST, 3, -2.4F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_sword"));
		all.add(amethyst_pickaxe = new PickaxeItem(LCCItemTier.AMETHYST, 1, -2.8F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_pickaxe"));
		all.add(amethyst_shovel = new ShovelItem(LCCItemTier.AMETHYST, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_shovel"));
		all.add(amethyst_axe = new AxeItem(LCCItemTier.AMETHYST, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_axe"));
		all.add(amethyst_hoe = new HoeItem(LCCItemTier.AMETHYST, 0.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_hoe"));
		all.add(amethyst_helmet = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_helmet"));
		all.add(amethyst_chestplate = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_chestplate"));
		all.add(amethyst_leggings = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_leggings"));
		all.add(amethyst_boots = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst_boots"));

		//Rainbow
		all.add(chromatic_core = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "chromatic_core"));

		//Spreaders
		all.add(spreader_essence = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "spreader_essence"));

		//Computing
		for (DyeColor color : DyeColor.values()) {
			all.add(i = new ComputingBlockItem(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "computer_casing_" + color.getName()));
			computer_casing.put(color, i);
		}

		//Power
		//TODO: new recipe for gauntlet as 'elemental gems' will be added
		all.add(gauntlet = new GauntletItem(new Item.Properties().group(LCC.itemGroup).setTEISR(() -> GauntletRenderer::new)).setRegistryName(LCC.MODID, "gauntlet"));

		//Health
		all.add(red_heart_half = new HeartItem(HeartsFunctionality.HeartType.RED, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "red_heart_half"));
		all.add(red_heart = new HeartItem(HeartsFunctionality.HeartType.RED, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "red_heart"));
		all.add(red_heart_container = new HeartContainerItem(HeartsFunctionality.HeartType.RED, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "red_heart_container"));
		all.add(iron_heart_half = new HeartItem(HeartsFunctionality.HeartType.IRON, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "iron_heart_half"));
		all.add(iron_heart = new HeartItem(HeartsFunctionality.HeartType.IRON, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "iron_heart"));
		all.add(iron_heart_container = new HeartContainerItem(HeartsFunctionality.HeartType.IRON, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "iron_heart_container"));
		all.add(crystal_heart_half = new HeartItem(HeartsFunctionality.HeartType.CRYSTAL, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "crystal_heart_half"));
		all.add(crystal_heart = new HeartItem(HeartsFunctionality.HeartType.CRYSTAL, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "crystal_heart"));
		all.add(crystal_heart_container = new HeartContainerItem(HeartsFunctionality.HeartType.CRYSTAL, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "crystal_heart_container"));
		all.add(temporary_heart_half = new HeartItem(HeartsFunctionality.HeartType.TEMPORARY, 1.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "temporary_heart_half"));
		all.add(temporary_heart = new HeartItem(HeartsFunctionality.HeartType.TEMPORARY, 2.0F, new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "temporary_heart"));
	}
	
}
