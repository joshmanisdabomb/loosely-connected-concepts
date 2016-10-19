package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggItems {
	
	//Added following a tutorial.
	public static Item testItem;
	
	public static ArrayList<Item> registry = new ArrayList<Item>();
	
	public static void init() {
		testItem = new AimaggItemBasic("testItem", Integer.MAX_VALUE-2);
	}
	
	public static void register() {
		for (Item i : registry) {
			GameRegistry.register(i);
		}
	}
	
	public static void registerRenders() {
		for (Item i : registry) {
			registerRender(i);
		}
	}
	
	private static void registerRender(Item i) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}
	
}
