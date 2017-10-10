package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemColored;
import com.joshmanisdabomb.aimagg.items.AimaggItemHeart;
import com.joshmanisdabomb.aimagg.items.AimaggItemIngot;
import com.joshmanisdabomb.aimagg.items.AimaggItemMaterial;
import com.joshmanisdabomb.aimagg.items.AimaggItemMissile;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill;
import com.joshmanisdabomb.aimagg.items.AimaggItemPorkchop;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard;
import com.joshmanisdabomb.aimagg.items.AimaggItemVectorPearl;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment.ArmorType;
import com.joshmanisdabomb.aimagg.items.equipment.AimaggEquipment.ToolType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggItems {

	public static Item testItem;

	public static Item ingot;

	public static AimaggEquipment rubyEquipment;
	public static AimaggEquipment topazEquipment;
	public static AimaggEquipment emeraldEquipment;
	public static AimaggEquipment sapphireEquipment;
	public static AimaggEquipment amethystEquipment;
	public static AimaggEquipment neonEquipment;
	
	public static Item missile;
	
	public static Item pill;
	//TODO drug test to find out modifiers applied on you
	
	public static Item heart;
	
	public static AimaggEquipment classicLeatherEquipment;
	public static AimaggEquipment classicStuddedEquipment;
	public static Item classicPorkchop;
	
	public static Item materials;
	public static Item vectorPearl;
	
	public static Item upgradeCard;
	
	public static final ArrayList<Item> registry = new ArrayList<Item>();
	public static final ArrayList<Item> colorRegistry = new ArrayList<Item>();
	
	public static void init() {
		testItem = new AimaggItemBasic("test_item");
		
		ingot = new AimaggItemIngot("ingot");

		rubyEquipment = new AimaggEquipment(AimaggEquipment.RUBY_TM, AimaggEquipment.RUBY_AM, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		topazEquipment = new AimaggEquipment(AimaggEquipment.TOPAZ_TM, AimaggEquipment.TOPAZ_AM, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		emeraldEquipment = new AimaggEquipment(AimaggEquipment.EMERALD_TM, AimaggEquipment.EMERALD_AM, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		sapphireEquipment = new AimaggEquipment(AimaggEquipment.SAPPHIRE_TM, AimaggEquipment.SAPPHIRE_AM, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		amethystEquipment = new AimaggEquipment(AimaggEquipment.AMETHYST_TM, AimaggEquipment.AMETHYST_AM, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NORMAL, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		neonEquipment = new AimaggEquipment(AimaggEquipment.NEON_TM, AimaggEquipment.NEON_AM, ToolType.SPECIALISED, ToolType.SPECIALISED, ToolType.SPECIALISED, ToolType.SPECIALISED, ToolType.NORMAL, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		
		missile = new AimaggItemMissile("missile");
		
		pill = new AimaggItemPill("pill");
		
		heart = new AimaggItemHeart("heart");
		
		classicLeatherEquipment = new AimaggEquipment(null, AimaggEquipment.CLASSIC_LEATHER_AM, ToolType.NONE, ToolType.NONE, ToolType.NONE, ToolType.NONE, ToolType.NONE, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		classicStuddedEquipment = new AimaggEquipment(null, AimaggEquipment.CLASSIC_STUDDED_AM, ToolType.NONE, ToolType.NONE, ToolType.NONE, ToolType.NONE, ToolType.NONE, ToolType.NONE, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL, ArmorType.NORMAL);
		classicPorkchop = new AimaggItemPorkchop("classic_pork");
		
		materials = new AimaggItemMaterial("material");
		vectorPearl = new AimaggItemVectorPearl("vector_pearl");
		
		upgradeCard = new AimaggItemUpgradeCard("upgrade_card");
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders(ModelRegistryEvent event) {
		for (Item i : registry) {
			if (i instanceof AimaggItemBasic) {((AimaggItemBasic)i).registerRender();}
		}
		AimaggEquipment.registerRenders(event);
	}

	@SideOnly(Side.CLIENT)
	public static void registerColoring() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return ((AimaggItemColored)stack.getItem()).getColorFromItemstack(stack, tintIndex);
            }
        }, (Item[])colorRegistry.toArray(new Item[colorRegistry.size()]));
	}
	
}
