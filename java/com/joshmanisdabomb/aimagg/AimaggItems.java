package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.items.AimaggItemGeneric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggItems {
	
	//Added following a tutorial.
	public static Item test;
	
	public static void init() {
		test = new AimaggItemGeneric("test");
	}
	
	public static void register() {
		GameRegistry.register(test);
	}
	
	public static void registerRenders() {
		registerRender(test);
	}
	
	private static void registerRender(Item i) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}
	
}
