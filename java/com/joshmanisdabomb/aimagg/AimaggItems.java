package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile.MissileType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggItems {
	
	//Added following a tutorial.
	public static Item testItem;
	public static Item missile;
	public static Item vectorPearl;
	
	public static ArrayList<Item> registry = new ArrayList<Item>();
	
	public static void init() {
		testItem = new AimaggItemBasic("testItem", Integer.MAX_VALUE-2);
		missile = new AimaggItemMissile("missile", 21);
		vectorPearl = new AimaggItemVectorPearl("vectorPearl", 40);
	}
	
	public static void register() {
		for (Item i : registry) {
			GameRegistry.register(i);
		}
	}
	
	public static void registerRenders() {
		for (Item i : registry) {
			if (!(i instanceof AimaggItemBasic) || !((AimaggItemBasic)i).usesCustomModels()) {
				registerRender(i);
			}
		}
	}
	
	private static void registerRender(Item i) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}

	public static void customModelResourceLocations() {
		ModelLoader.setCustomModelResourceLocation(missile, MissileType.EXPLOSIVE.getMetadata(), new ModelResourceLocation(Constants.MOD_ID + ":missile/explosive", "inventory"));
		ModelLoader.setCustomModelResourceLocation(missile, MissileType.FIRE.getMetadata(), new ModelResourceLocation(Constants.MOD_ID + ":missile/explosive", "inventory"));
		ModelLoader.setCustomModelResourceLocation(missile, MissileType.NUCLEAR.getMetadata(), new ModelResourceLocation(Constants.MOD_ID + ":missile/explosive", "inventory"));
	}
	
}
