package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockGeneric;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggBlocks {

	//Added following a tutorial.
	public static Block testBlock;
	
	public static void init() {
		testBlock = new AimaggBlockGeneric("testBlock", Material.GROUND, MapColor.ADOBE);
	}
	
	public static void register() {
		registerBlock(testBlock);
	}
	
	private static void registerBlock(Block b) {
		GameRegistry.register(b);
		ItemBlock ib = new ItemBlock(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
	}
	
	public static void registerRenders() {
		registerRender(testBlock);
	}
	
	private static void registerRender(Block b) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
	}
	
}
