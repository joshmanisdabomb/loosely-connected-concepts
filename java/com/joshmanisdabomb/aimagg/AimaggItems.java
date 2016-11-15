package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard.UpgradeCardType;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggItems {

	//Test Items, Sort Value 2^31-4
	public static Item testItem;
	
	//Missiles, Sort Values 21-36
	public static Item missile;
	
	//Misc, Sort Values 40
	public static Item vectorPearl;
	
	//Misc, Sort Values 2000-2100
	public static Item upgradeCard;
	
	public static ArrayList<Item> registry = new ArrayList<Item>();
	
	public static void init() {
		testItem = new AimaggItemBasic("testItem", Integer.MAX_VALUE-3);
		missile = new AimaggItemMissile("missile", 21);
		vectorPearl = new AimaggItemVectorPearl("vectorPearl", 40);
		
		upgradeCard = new AimaggItemUpgradeCard("upgradeCard", 2000);
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
		for (MissileType mt : MissileType.values()) {
			ModelLoader.setCustomModelResourceLocation(missile, mt.getMetadata(), mt.getItemModel());
		}
		
		for (UpgradeCardType uc : UpgradeCardType.values()) {
			ModelLoader.setCustomModelResourceLocation(upgradeCard, uc.getMetadata(), uc.getItemModel());
		}
	}
	
}
