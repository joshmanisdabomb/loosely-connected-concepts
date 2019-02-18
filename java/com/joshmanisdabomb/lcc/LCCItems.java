package com.joshmanisdabomb.lcc;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;

public class LCCItems {

	public static final ArrayList<Item> all = new ArrayList<Item>();

	//Test Items
	public static Item test_item;

	public static void init(Register<Item> itemRegistryEvent) {
		//Test Items
		all.add(test_item = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "test_item"));

		//Resources
		for (LCCResources r : LCCResources.values()) {
			if (r.nugget != null) {
				all.add(r.nugget);
			}
			if (r.ingot != null) {
				all.add(r.ingot);
			}
		}
	}
	
}
