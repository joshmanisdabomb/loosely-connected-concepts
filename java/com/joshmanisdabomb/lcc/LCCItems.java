package com.joshmanisdabomb.lcc;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;

public class LCCItems {

	public static final ArrayList<Item> all = new ArrayList<Item>();

	//Test Items
	public static Item test_item;

	//Resources
	public static Item ruby;
	public static Item topaz;
	public static Item sapphire;
	public static Item amethyst;
	public static Item uranium_nugget;
	public static Item uranium;

	public static void init(Register<Item> itemRegistryEvent) {
		//Test Items
		all.add(test_item = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "test_item"));

		//Resources
		all.add(ruby = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "ruby"));
		all.add(topaz = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "topaz"));
		all.add(sapphire = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "sapphire"));
		all.add(amethyst = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "amethyst"));
		all.add(uranium_nugget = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "uranium_nugget"));
		all.add(uranium = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "uranium"));
	}
	
}
