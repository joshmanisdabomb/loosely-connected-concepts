package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHorizontal;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreaderConstructor;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AimaggBlocks {

	//Added following a tutorial.
	public static Block testBlock;
	public static Block testBlock2;
	public static Block testBlock3;
	
	public static Block spreaderConstructor;
	
	public static ArrayList<Block> registry = new ArrayList<Block>();
	
	public static void init() {
		testBlock = new AimaggBlockBasic("testBlock", Integer.MAX_VALUE-1, Material.GROUND, MapColor.YELLOW);
		testBlock2 = new AimaggBlockBasicHorizontal("testBlock2", Integer.MAX_VALUE, Material.GROUND, MapColor.ADOBE);
		//testBlock3 = new AimaggBlockBasic("testBlock3", Material.GROUND, MapColor.ADOBE);
		spreaderConstructor = new AimaggBlockSpreaderConstructor("spreaderController", 0, Material.IRON, MapColor.SNOW);
	}
	
	public static void register() {
		for (Block b : registry) {
			registerBlock(b);
		}
	}
	
	private static void registerBlock(Block b) {
		GameRegistry.register(b);
		ItemBlock ib = new ItemBlock(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
	}
	
	public static void registerRenders() {
		for (Block b : registry) {
			registerRender(b);
		}
	}
	
	private static void registerRender(Block b) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
	}
	
}
