package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.ChocolateBlock;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.item.*;
import com.joshmanisdabomb.lcc.item.render.GauntletRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class LCCItems {

	public static final ArrayList<Item> all = new ArrayList<>();

	//Test Items
	public static Item test_item;

	//Gizmos
	public static Item plastic;

	//Resources
	public static Item ruby;
	public static Item topaz;
	public static Item sapphire;
	public static Item amethyst;
	public static Item uranium_nugget;
	public static Item uranium;
	public static Item enriched_uranium_nugget;
	public static Item enriched_uranium;

	public static Item neon;
	public static Item neon_nugget;

	//TODO: activated neon from refiner
	//TODO: candy cane (can be used as sticks)
	//TODO: chocolate -> 4 chocolate squares (edible)

	//Tools
	public static SwordItem ruby_sword;
	public static PickaxeItem ruby_pickaxe;
	public static ShovelItem ruby_shovel;
	public static AxeItem ruby_axe;
	public static HoeItem ruby_hoe;
	public static CustomArmorItem ruby_helmet;
	public static CustomArmorItem ruby_chestplate;
	public static CustomArmorItem ruby_leggings;
	public static CustomArmorItem ruby_boots;

	public static SwordItem topaz_sword;
	public static PickaxeItem topaz_pickaxe;
	public static ShovelItem topaz_shovel;
	public static AxeItem topaz_axe;
	public static HoeItem topaz_hoe;
	public static CustomArmorItem topaz_helmet;
	public static CustomArmorItem topaz_chestplate;
	public static CustomArmorItem topaz_leggings;
	public static CustomArmorItem topaz_boots;

	public static SwordItem emerald_sword;
	public static PickaxeItem emerald_pickaxe;
	public static ShovelItem emerald_shovel;
	public static AxeItem emerald_axe;
	public static HoeItem emerald_hoe;
	public static CustomArmorItem emerald_helmet;
	public static CustomArmorItem emerald_chestplate;
	public static CustomArmorItem emerald_leggings;
	public static CustomArmorItem emerald_boots;

	public static SwordItem sapphire_sword;
	public static PickaxeItem sapphire_pickaxe;
	public static ShovelItem sapphire_shovel;
	public static AxeItem sapphire_axe;
	public static HoeItem sapphire_hoe;
	public static CustomArmorItem sapphire_helmet;
	public static CustomArmorItem sapphire_chestplate;
	public static CustomArmorItem sapphire_leggings;
	public static CustomArmorItem sapphire_boots;

	public static SwordItem amethyst_sword;
	public static PickaxeItem amethyst_pickaxe;
	public static ShovelItem amethyst_shovel;
	public static AxeItem amethyst_axe;
	public static HoeItem amethyst_hoe;
	public static CustomArmorItem amethyst_helmet;
	public static CustomArmorItem amethyst_chestplate;
	public static CustomArmorItem amethyst_leggings;
	public static CustomArmorItem amethyst_boots;

	public static SwordItem red_candy_cane_sword;
	public static PickaxeItem red_candy_cane_pickaxe;
	public static ShovelItem red_candy_cane_shovel;
	public static AxeItem red_candy_cane_axe;
	public static HoeItem red_candy_cane_hoe;

	public static SwordItem green_candy_cane_sword;
	public static PickaxeItem green_candy_cane_pickaxe;
	public static ShovelItem green_candy_cane_shovel;
	public static AxeItem green_candy_cane_axe;
	public static HoeItem green_candy_cane_hoe;

	public static SwordItem blue_candy_cane_sword;
	public static PickaxeItem blue_candy_cane_pickaxe;
	public static ShovelItem blue_candy_cane_shovel;
	public static AxeItem blue_candy_cane_axe;
	public static HoeItem blue_candy_cane_hoe;

	public static SwordItem vivid_sword;
	public static PickaxeItem vivid_pickaxe;
	public static ShovelItem vivid_shovel;
	public static AxeItem vivid_axe;
	public static HoeItem vivid_hoe;

	public static SwordItem twilight_sword;
	public static PickaxeItem twilight_pickaxe;
	public static ShovelItem twilight_shovel;
	public static AxeItem twilight_axe;
	public static HoeItem twilight_hoe;

	public static SwordItem neon_sword;
	public static PickaxeItem neon_pickaxe;
	public static ShovelItem neon_shovel;
	public static AxeItem neon_axe;
	public static HoeItem neon_hoe;
	public static CustomArmorItem neon_helmet;
	public static CustomArmorItem neon_chestplate;
	public static CustomArmorItem neon_leggings;
	public static CustomArmorItem neon_boots;

	//Wasteland
	public static BucketItem oil_bucket;

	//Rainbow
	public static Item chromatic_core;

	public static ChocolateBlockItem chocolate_dark;
	public static ChocolateBlockItem chocolate_milk;
	public static ChocolateBlockItem chocolate_white;
	public static ChocolateBlockItem chocolate_pink;

	//Computing
	public static VisualStackItem microchip;
	public static Item chipset;
	public static LinkedHashMap<DyeColor, ComputingBlockItem> computer_casings = new LinkedHashMap<>();
	public static LinkedHashMap<DyeColor, ComputingBlockItem> computers = new LinkedHashMap<>();
	public static LinkedHashMap<DyeColor, ComputingBlockItem> floppy_drives = new LinkedHashMap<>();
	public static LinkedHashMap<DyeColor, ComputingBlockItem> cd_drives = new LinkedHashMap<>();
	public static LinkedHashMap<DyeColor, ComputingBlockItem> card_readers = new LinkedHashMap<>();
	public static LinkedHashMap<DyeColor, ComputingBlockItem> stick_readers = new LinkedHashMap<>();
	public static LinkedHashMap<DyeColor, ComputingBlockItem> drive_bays = new LinkedHashMap<>();
	public static ComputingItem cpu;
	public static ComputingItem ram;
	public static ComputingItem gpu;
	public static StorageItem floppy_disk;
	public static StorageItem compact_disc;
	public static StorageItem memory_card;
	public static StorageItem memory_stick;
	public static StorageItem hard_disk_drive;
	public static StorageItem solid_state_drive;
	public static StorageItem m2;

	//Spreaders
	public static Item spreader_essence;

	//Power
	public static GauntletItem gauntlet;

	//Health
	public static HeartItem red_heart_half;
	public static HeartItem red_heart;
	public static HeartContainerItem red_heart_container;

	public static HeartItem iron_heart_half;
	public static HeartItem iron_heart;
	public static HeartContainerItem iron_heart_container;

	public static HeartItem crystal_heart_half;
	public static HeartItem crystal_heart;
	public static HeartContainerItem crystal_heart_container;

	public static Item temporary_heart_half;
	public static Item temporary_heart;

    public static void init(Register<Item> e) {
		//Test Items
		all.add((test_item = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "test_item"));

		//Gizmos
		 /*
		 	TODO:
			oil in universal refiner = rocket fuel
			oil + slimeball in universal refiner = asphalt
			oil + coal in universal refiner = plastic
		 */
		all.add((plastic = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "plastic"));

		//Resources
		all.add((ruby = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby"));
		all.add((topaz = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz"));
		all.add((sapphire = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire"));
		all.add((amethyst = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst"));
		all.add((uranium_nugget = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "uranium_nugget"));
		all.add((uranium = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "uranium"));
		all.add((enriched_uranium_nugget = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "enriched_uranium_nugget"));
		all.add((enriched_uranium = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "enriched_uranium"));

		all.add((neon_nugget = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_nugget"));
		all.add((neon = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon"));

		//Tools
		all.add((ruby_sword = new SwordItem(LCCItemTier.RUBY, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_sword"));
		all.add((ruby_pickaxe = new PickaxeItem(LCCItemTier.RUBY, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_pickaxe"));
		all.add((ruby_shovel = new ShovelItem(LCCItemTier.RUBY, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_shovel"));
		all.add((ruby_axe = new AxeItem(LCCItemTier.RUBY, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_axe"));
		all.add((ruby_hoe = new HoeItem(LCCItemTier.RUBY, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_hoe"));
		all.add((ruby_helmet = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_helmet"));
		all.add((ruby_chestplate = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_chestplate"));
		all.add((ruby_leggings = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_leggings"));
		all.add((ruby_boots = new CustomArmorItem(LCCArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ruby_boots"));

		all.add((topaz_sword = new SwordItem(LCCItemTier.TOPAZ, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_sword"));
		all.add((topaz_pickaxe = new PickaxeItem(LCCItemTier.TOPAZ, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_pickaxe"));
		all.add((topaz_shovel = new ShovelItem(LCCItemTier.TOPAZ, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_shovel"));
		all.add((topaz_axe = new AxeItem(LCCItemTier.TOPAZ, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_axe"));
		all.add((topaz_hoe = new HoeItem(LCCItemTier.TOPAZ, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_hoe"));
		all.add((topaz_helmet = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_helmet"));
		all.add((topaz_chestplate = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_chestplate"));
		all.add((topaz_leggings = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_leggings"));
		all.add((topaz_boots = new CustomArmorItem(LCCArmorMaterial.TOPAZ, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "topaz_boots"));

		all.add((emerald_sword = new SwordItem(LCCItemTier.EMERALD, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_sword"));
		all.add((emerald_pickaxe = new PickaxeItem(LCCItemTier.EMERALD, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_pickaxe"));
		all.add((emerald_shovel = new ShovelItem(LCCItemTier.EMERALD, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_shovel"));
		all.add((emerald_axe = new AxeItem(LCCItemTier.EMERALD, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_axe"));
		all.add((emerald_hoe = new HoeItem(LCCItemTier.EMERALD, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_hoe"));
		all.add((emerald_helmet = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_helmet"));
		all.add((emerald_chestplate = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_chestplate"));
		all.add((emerald_leggings = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_leggings"));
		all.add((emerald_boots = new CustomArmorItem(LCCArmorMaterial.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "emerald_boots"));

		all.add((sapphire_sword = new SwordItem(LCCItemTier.SAPPHIRE, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_sword"));
		all.add((sapphire_pickaxe = new PickaxeItem(LCCItemTier.SAPPHIRE, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_pickaxe"));
		all.add((sapphire_shovel = new ShovelItem(LCCItemTier.SAPPHIRE, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_shovel"));
		all.add((sapphire_axe = new AxeItem(LCCItemTier.SAPPHIRE, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_axe"));
		all.add((sapphire_hoe = new HoeItem(LCCItemTier.SAPPHIRE, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_hoe"));
		all.add((sapphire_helmet = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_helmet"));
		all.add((sapphire_chestplate = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_chestplate"));
		all.add((sapphire_leggings = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_leggings"));
		all.add((sapphire_boots = new CustomArmorItem(LCCArmorMaterial.SAPPHIRE, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "sapphire_boots"));

		all.add((amethyst_sword = new SwordItem(LCCItemTier.AMETHYST, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_sword"));
		all.add((amethyst_pickaxe = new PickaxeItem(LCCItemTier.AMETHYST, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_pickaxe"));
		all.add((amethyst_shovel = new ShovelItem(LCCItemTier.AMETHYST, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_shovel"));
		all.add((amethyst_axe = new AxeItem(LCCItemTier.AMETHYST, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_axe"));
		all.add((amethyst_hoe = new HoeItem(LCCItemTier.AMETHYST, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_hoe"));
		all.add((amethyst_helmet = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_helmet"));
		all.add((amethyst_chestplate = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_chestplate"));
		all.add((amethyst_leggings = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_leggings"));
		all.add((amethyst_boots = new CustomArmorItem(LCCArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "amethyst_boots"));

		all.add((red_candy_cane_sword = new SwordItem(LCCItemTier.RED_CANDY_CANE, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_candy_cane_sword"));
		all.add((red_candy_cane_pickaxe = new PickaxeItem(LCCItemTier.RED_CANDY_CANE, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_candy_cane_pickaxe"));
		all.add((red_candy_cane_shovel = new ShovelItem(LCCItemTier.RED_CANDY_CANE, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_candy_cane_shovel"));
		all.add((red_candy_cane_axe = new AxeItem(LCCItemTier.RED_CANDY_CANE, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_candy_cane_axe"));
		all.add((red_candy_cane_hoe = new HoeItem(LCCItemTier.RED_CANDY_CANE, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_candy_cane_hoe"));

		all.add((green_candy_cane_sword = new SwordItem(LCCItemTier.GREEN_CANDY_CANE, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "green_candy_cane_sword"));
		all.add((green_candy_cane_pickaxe = new PickaxeItem(LCCItemTier.GREEN_CANDY_CANE, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "green_candy_cane_pickaxe"));
		all.add((green_candy_cane_shovel = new ShovelItem(LCCItemTier.GREEN_CANDY_CANE, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "green_candy_cane_shovel"));
		all.add((green_candy_cane_axe = new AxeItem(LCCItemTier.GREEN_CANDY_CANE, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "green_candy_cane_axe"));
		all.add((green_candy_cane_hoe = new HoeItem(LCCItemTier.GREEN_CANDY_CANE, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "green_candy_cane_hoe"));

		all.add((blue_candy_cane_sword = new SwordItem(LCCItemTier.BLUE_CANDY_CANE, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "blue_candy_cane_sword"));
		all.add((blue_candy_cane_pickaxe = new PickaxeItem(LCCItemTier.BLUE_CANDY_CANE, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "blue_candy_cane_pickaxe"));
		all.add((blue_candy_cane_shovel = new ShovelItem(LCCItemTier.BLUE_CANDY_CANE, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "blue_candy_cane_shovel"));
		all.add((blue_candy_cane_axe = new AxeItem(LCCItemTier.BLUE_CANDY_CANE, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "blue_candy_cane_axe"));
		all.add((blue_candy_cane_hoe = new HoeItem(LCCItemTier.BLUE_CANDY_CANE, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "blue_candy_cane_hoe"));

		all.add((vivid_sword = new SwordItem(LCCItemTier.VIVID_WOOD, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "vivid_sword"));
		all.add((vivid_pickaxe = new PickaxeItem(LCCItemTier.VIVID_WOOD, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "vivid_pickaxe"));
		all.add((vivid_shovel = new ShovelItem(LCCItemTier.VIVID_WOOD, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "vivid_shovel"));
		all.add((vivid_axe = new AxeItem(LCCItemTier.VIVID_WOOD, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "vivid_axe"));
		all.add((vivid_hoe = new HoeItem(LCCItemTier.VIVID_WOOD, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "vivid_hoe"));

		all.add((twilight_sword = new SwordItem(LCCItemTier.TWILIGHT, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "twilight_sword"));
		all.add((twilight_pickaxe = new PickaxeItem(LCCItemTier.TWILIGHT, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "twilight_pickaxe"));
		all.add((twilight_shovel = new ShovelItem(LCCItemTier.TWILIGHT, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "twilight_shovel"));
		all.add((twilight_axe = new AxeItem(LCCItemTier.TWILIGHT, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "twilight_axe"));
		all.add((twilight_hoe = new HoeItem(LCCItemTier.TWILIGHT, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "twilight_hoe"));

		all.add((neon_sword = new SwordItem(LCCItemTier.NEON, 3, -2.4F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_sword"));
		all.add((neon_pickaxe = new PickaxeItem(LCCItemTier.NEON, 1, -2.8F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_pickaxe"));
		all.add((neon_shovel = new ShovelItem(LCCItemTier.NEON, 1.5F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_shovel"));
		all.add((neon_axe = new AxeItem(LCCItemTier.NEON, 5.0F, -3.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_axe"));
		all.add((neon_hoe = new HoeItem(LCCItemTier.NEON, 0.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_hoe"));
		all.add((neon_helmet = new CustomArmorItem(LCCArmorMaterial.NEON, EquipmentSlotType.HEAD, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_helmet"));
		all.add((neon_chestplate = new CustomArmorItem(LCCArmorMaterial.NEON, EquipmentSlotType.CHEST, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_chestplate"));
		all.add((neon_leggings = new CustomArmorItem(LCCArmorMaterial.NEON, EquipmentSlotType.LEGS, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_leggings"));
		all.add((neon_boots = new CustomArmorItem(LCCArmorMaterial.NEON, EquipmentSlotType.FEET, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "neon_boots"));

		//Wasteland
		all.add((oil_bucket = new BucketItem(() -> LCCFluids.oil, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(LCC.itemGroup))).setRegistryName(LCC.MODID, "oil_bucket"));

		//Rainbow
		all.add((chromatic_core = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "chromatic_core"));

		all.add((chocolate_dark = new ChocolateBlockItem(ChocolateBlock.Type.DARK, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "chocolate_dark"));
		all.add((chocolate_milk = new ChocolateBlockItem(ChocolateBlock.Type.MILK, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "chocolate_milk"));
		all.add((chocolate_white = new ChocolateBlockItem(ChocolateBlock.Type.WHITE, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "chocolate_white"));
		all.add((chocolate_pink = new ChocolateBlockItem(ChocolateBlock.Type.PINK, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "chocolate_pink"));

		//Spreaders
		all.add((spreader_essence = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "spreader_essence"));

		//Computing
		all.add((microchip = new VisualStackItem(9, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "microchip"));
		all.add((chipset = new Item(new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "chipset"));
		for (DyeColor color : DyeColor.values()) {
			ComputingBlockItem i;
			all.add((i = new ComputingBlockItem(ComputingModule.Type.CASING, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "computer_casing_" + color.getName()));
			computer_casings.put(color, i);
			all.add((i = new ComputingBlockItem(ComputingModule.Type.COMPUTER, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "computer_" + color.getName()));
			computers.put(color, i);
			all.add((i = new ComputingBlockItem(ComputingModule.Type.FLOPPY_DRIVE, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "floppy_drive_" + color.getName()));
			floppy_drives.put(color, i);
			all.add((i = new ComputingBlockItem(ComputingModule.Type.CD_DRIVE, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "cd_drive_" + color.getName()));
			cd_drives.put(color, i);
			all.add((i = new ComputingBlockItem(ComputingModule.Type.CARD_READER, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "card_reader_" + color.getName()));
			card_readers.put(color, i);
			all.add((i = new ComputingBlockItem(ComputingModule.Type.STICK_READER, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "stick_reader_" + color.getName()));
			stick_readers.put(color, i);
			all.add((i = new ComputingBlockItem(ComputingModule.Type.DRIVE_BAY, color, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "drive_bay_" + color.getName()));
			drive_bays.put(color, i);
		}
		all.add((cpu = new ComputingItem(16, 512, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "cpu"));
		all.add((ram = new ComputingItem(64, 1024, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "ram"));
		all.add((gpu = new ComputingItem(16, 1024, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "gpu"));
		all.add((floppy_disk = new StorageItem(new int[]{144}, 1, 0, 2, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "floppy_disk"));
		all.add((compact_disc = new StorageItem(new int[]{700}, 1, 32, 16, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "compact_disc"));
		all.add((memory_card = new StorageItem(new int[]{250, 500, 1000, 2000, 4000}, 1, 0, 8, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "memory_card"));
		all.add((memory_stick = new StorageItem(new int[]{500, 1000, 2000, 4000, 8000}, 1, 32, 0, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "memory_stick"));
		all.add((hard_disk_drive = new StorageItem(new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 100000}, 0, 250, 0, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "hard_disk_drive"));
		all.add((solid_state_drive = new StorageItem(new int[]{8000, 16000, 32000, 64000, 100000}, 8, 0, 0, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "solid_state_drive"));
		all.add((m2 = new StorageItem(new int[]{4000, 8000, 16000, 32000, 64000, 100000}, 1, 0, 64, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "m2"));

		//Power
		//TODO: new recipe for gauntlet as 'elemental gems' will be added
		all.add((gauntlet = new GauntletItem(new Item.Properties().maxStackSize(1).group(LCC.itemGroup).setISTER(() -> GauntletRenderer::new))).setRegistryName(LCC.MODID, "gauntlet"));

		//Health
		all.add((red_heart_half = new HeartItem(HeartsFunctionality.HeartType.RED, 1.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_heart_half"));
		all.add((red_heart = new HeartItem(HeartsFunctionality.HeartType.RED, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_heart"));
		all.add((red_heart_container = new HeartContainerItem(HeartsFunctionality.HeartType.RED, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "red_heart_container"));
		all.add((iron_heart_half = new HeartItem(HeartsFunctionality.HeartType.IRON, 1.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "iron_heart_half"));
		all.add((iron_heart = new HeartItem(HeartsFunctionality.HeartType.IRON, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "iron_heart"));
		all.add((iron_heart_container = new HeartContainerItem(HeartsFunctionality.HeartType.IRON, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "iron_heart_container"));
		all.add((crystal_heart_half = new HeartItem(HeartsFunctionality.HeartType.CRYSTAL, 1.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "crystal_heart_half"));
		all.add((crystal_heart = new HeartItem(HeartsFunctionality.HeartType.CRYSTAL, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "crystal_heart"));
		all.add((crystal_heart_container = new HeartContainerItem(HeartsFunctionality.HeartType.CRYSTAL, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "crystal_heart_container"));
		all.add((temporary_heart_half = new HeartItem(HeartsFunctionality.HeartType.TEMPORARY, 1.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "temporary_heart_half"));
		all.add((temporary_heart = new HeartItem(HeartsFunctionality.HeartType.TEMPORARY, 2.0F, new Item.Properties().group(LCC.itemGroup))).setRegistryName(LCC.MODID, "temporary_heart"));
	}
	
}
