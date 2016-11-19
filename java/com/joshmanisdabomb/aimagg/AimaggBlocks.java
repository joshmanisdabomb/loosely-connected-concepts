package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHorizontal;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockFireNuclear;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockInstafall;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockLaunchPad;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreader;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreaderConstructor;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlocks {

	//Test Blocks, Sort Values 2^31-1 - 2^31-3
	public static Block testBlock;
	public static Block testBlock2;
	public static Block testBlock3;
	
	//Spreaders, Sort Values 1-100
	public static Block spreader;
	public static Block spreaderConstructor;
	
	//Missiles, Sort Values 101-200
	public static Block launchPad;
	
	//Misc, Sort Values 100000-101000
	public static Block nuclearWaste;
	public static Block nuclearFire;
	
	public static ArrayList<Block> registry = new ArrayList<Block>();
	
	public static void init() {
		testBlock = new AimaggBlockBasic("testBlock", Integer.MAX_VALUE-2, Material.GROUND, MapColor.YELLOW);
		testBlock2 = new AimaggBlockBasicHorizontal("testBlock2", Integer.MAX_VALUE-1, Material.GROUND, MapColor.ADOBE);
		//testBlock3 = new AimaggBlockBasic("testBlock3", Integer.MAX_VALUE, Material.GROUND, MapColor.ADOBE);
		//spreader = new AimaggBlockSpreader("spreader", 49, Material.IRON, MapColor.SNOW);
		//spreaderConstructor = new AimaggBlockSpreaderConstructor("spreaderConstructor", 50, Material.IRON, MapColor.SNOW);
		launchPad = new AimaggBlockLaunchPad("launchPad", 149, Material.IRON, MapColor.SNOW);
		nuclearWaste = new AimaggBlockInstafall("nuclearWaste", 100200, Material.ROCK, MapColor.GRAY).setBlockUnbreakable().setResistance(6000000.0F);
		nuclearFire = new AimaggBlockFireNuclear("nuclearFire", 0, Material.FIRE, MapColor.GREEN);
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
	
	@SideOnly(Side.CLIENT)
	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(AimaggTELaunchPad.class, new com.joshmanisdabomb.aimagg.te.tesr.AimaggTESRLaunchPad());
	}
	
}