package com.joshmanisdabomb.lcc;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;

public class LCCItems {

	public static final ArrayList<Item> all = new ArrayList<Item>();
	
	public static Item test_item;

	public static void init(Register<Item> itemRegistryEvent) {
		all.add(test_item = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(LCC.modid, "test_item"));
	}
	
}
