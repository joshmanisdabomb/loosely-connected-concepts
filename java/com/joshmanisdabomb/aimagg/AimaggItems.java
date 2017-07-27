package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.data.MissileType;
import com.joshmanisdabomb.aimagg.data.OreIngotStorage;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemColored;
import com.joshmanisdabomb.aimagg.items.AimaggItemIngot;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard.UpgradeCardType;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggItems {

	//Test Items, Sort Value 2^31-4
	public static Item testItem;

	//Ores, Ingots and Storage Blocks, Sort Values 300-400
	public static Item ingot;
	
	//Missiles, Sort Values 2000-2100
	public static Item missile;

	//Spreaders, Sort Values 1000-1100
	public static Item spreaderCore;
	
	//Pills, Sort Values 8000-8100
	public static Item pill;
	
	//Misc, Sort Values 100000-101000
	public static Item vectorPearl;
	
	//Upgrade Cards, Sort Values 101001+
	public static Item upgradeCard;
	
	public static final ArrayList<Item> registry = new ArrayList<Item>();
	public static final ArrayList<Item> colorRegistry = new ArrayList<Item>();
	
	public static void init() {
		testItem = new AimaggItemBasic("test_item", Integer.MAX_VALUE-3);
		
		ingot = new AimaggItemIngot("ingot", 311);
		
		spreaderCore = new AimaggItemBasic("spreader_core", 1039);
		
		missile = new AimaggItemMissile("missile", 2050);
		
		pill = new AimaggItemPill("pill", 8000);
		
		vectorPearl = new AimaggItemVectorPearl("vector_pearl", 100010);
		
		upgradeCard = new AimaggItemUpgradeCard("upgrade_card", 101001);
	}

	public static void registerRenders(ModelRegistryEvent event) {
		for (Item i : registry) {
			if (i instanceof AimaggItemBasic) {((AimaggItemBasic)i).registerRender();}
		}
	}

	public static void registerColoring() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int getColorFromItemstack(ItemStack stack, int tintIndex)
            {
                return ((AimaggItemColored)stack.getItem()).getColorFromItemstack(stack, tintIndex);
            }
        }, (Item[])colorRegistry.toArray(new Item[colorRegistry.size()]));
	}
	
}
