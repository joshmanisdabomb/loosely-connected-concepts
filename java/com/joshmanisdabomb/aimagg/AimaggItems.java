package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemColored;
import com.joshmanisdabomb.aimagg.items.AimaggItemHeart;
import com.joshmanisdabomb.aimagg.items.AimaggItemIngot;
import com.joshmanisdabomb.aimagg.items.AimaggItemMaterial;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggItems {

	//Test Items, Sort Value 2^31-4
	public static Item testItem;

	//Ores, Ingots and Storage Blocks, Sort Values 300-400
	public static Item ingot;
	
	//Missiles, Sort Values 2000-2100
	public static Item missile;
	
	//Pills, Sort Values 8000-8100
	public static Item pill;
	//TODO drug test to find out modifiers applied on you
	
	//Health, Sort Values 8100-8200
	public static Item heart;
	
	//Misc, Sort Values 100000-101000
	public static Item materials;
	public static Item vectorPearl;
	
	//Upgrade Cards, Sort Values 101001+
	public static Item upgradeCard;
	
	public static final ArrayList<Item> registry = new ArrayList<Item>();
	public static final ArrayList<Item> colorRegistry = new ArrayList<Item>();
	
	public static void init() {
		testItem = new AimaggItemBasic("test_item");
		
		ingot = new AimaggItemIngot("ingot");
		
		missile = new AimaggItemMissile("missile");
		
		pill = new AimaggItemPill("pill");
		
		heart = new AimaggItemHeart("heart");
		
		materials = new AimaggItemMaterial("material");
		vectorPearl = new AimaggItemVectorPearl("vector_pearl");
		
		upgradeCard = new AimaggItemUpgradeCard("upgrade_card");
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders(ModelRegistryEvent event) {
		for (Item i : registry) {
			if (i instanceof AimaggItemBasic) {((AimaggItemBasic)i).registerRender();}
		}
	}

	@SideOnly(Side.CLIENT)
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
