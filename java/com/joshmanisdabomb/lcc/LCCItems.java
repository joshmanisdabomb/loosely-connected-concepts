package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.item.render.teisr.TEISRGauntlet;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import com.joshmanisdabomb.lcc.item.ItemGauntlet;

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

	//Power
	public static Item gauntlet;

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
		all.add(enriched_uranium_nugget = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "enriched_uranium_nugget"));
		all.add(enriched_uranium = new Item(new Item.Properties().group(LCC.itemGroup)).setRegistryName(LCC.MODID, "enriched_uranium"));

		//Power
		all.add(gauntlet = new ItemGauntlet(new Item.Properties().group(LCC.itemGroup).setTEISR(() -> TEISRGauntlet::new)).setRegistryName(LCC.MODID, "gauntlet"));
	}
	
}
