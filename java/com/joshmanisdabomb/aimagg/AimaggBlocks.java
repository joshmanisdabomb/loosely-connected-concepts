package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHorizontal;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockFireNuclear;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockLaunchPad;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockOre;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSoft;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreader;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreaderInterface;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockStorage;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlocks {
	
	//Test Blocks, Sort Values 2^31-1 - 2^31-3
	public static Block testBlock;
	public static Block testBlock2;
	public static Block testBlock3;
	
	//Ores, Ingots and Storage Blocks, Sort Values 300-400
	public static Block ore;
	public static Block storage;
	
	//Spreaders, Sort Values 1000-1100
	public static Block[] spreaders = new Block[16];
	public static Block spreaderInterface;
	
	//Missiles, Sort Values 2000-2100
	public static Block launchPad;
	
	//Misc, Sort Values 100000-101000
	//TODO Give nuclear it's own category and add nukes with nice model.
	public static Block soft;
	public static Block nuclearFire;

	public static ArrayList<Block> registry = new ArrayList<Block>();
	public static ArrayList<Item> ibRegistry = new ArrayList<Item>();
	
	public static void init() {
		testBlock = new AimaggBlockBasic("test_block", Integer.MAX_VALUE-2, Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock).setSoundType(SoundType.CLOTH);
		testBlock2 = new AimaggBlockBasicHorizontal("test_block_2", Integer.MAX_VALUE-1, Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock2).setSoundType(SoundType.CLOTH);
		((AimaggBlockBasic)testBlock2).alwaysDropWithDamage(0);
		//testBlock3 = new AimaggBlockBasic("test_block_3", Integer.MAX_VALUE, Material.GROUND, MapColor.YELLOW);
		//((AimaggBlockBasic)testBlock3).setSoundType(SoundType.CLOTH);
		//((AimaggBlockBasic)testBlock3).alwaysDropWithDamage(0);
		
		ore = new AimaggBlockOre("ore", 310, Material.ROCK);
		storage = new AimaggBlockStorage("storage", 312, Material.IRON);
		
		for (int i = 0; i < 16; i++) {
			spreaders[i] = new AimaggBlockSpreader(EnumDyeColor.byMetadata(i), "spreader_" + EnumDyeColor.byMetadata(i).getName(), 1040, Material.IRON).setHardness(0.2F).setResistance(0.05F);
			((AimaggBlockBasic)spreaders[i]).setDrops(spreaders[i], 1, 1, 0.1F, 0, 0, 0.0F);
			((AimaggBlockBasic)spreaders[i]).alwaysDropWithDamage(0);
			((AimaggBlockBasic)spreaders[i]).setSilkTouchDrops(spreaders[i], 1);
			((AimaggBlockBasic)spreaders[i]).setSoundType(SoundType.CLOTH);
		}
		spreaderInterface = new AimaggBlockSpreaderInterface("spreader_interface", 1060, Material.IRON, MapColor.IRON).setHardness(7.0F);
		((AimaggBlockBasic)spreaderInterface).setSoundType(SoundType.METAL);
		spreaderInterface.setHarvestLevel("pickaxe", 2);

		launchPad = new AimaggBlockLaunchPad("launch_pad", 2049, Material.IRON, MapColor.IRON).setHardness(10.0F); //has soundtype, hardness and resistance
		((AimaggBlockBasic)launchPad).setSoundType(SoundType.METAL);
		launchPad.setHarvestLevel("pickaxe", 2);
		
		soft = new AimaggBlockSoft("soft", 100150, Material.GROUND);
		nuclearFire = new AimaggBlockFireNuclear("nuclear_fire", 0, Material.FIRE, MapColor.LIME);	
	}	

	public static void registerRenders(ModelRegistryEvent event) {
		for (Block b : registry) {
			if (b instanceof AimaggBlockBasic) {
				((AimaggBlockBasic)b).registerInventoryRender();
			} else {
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(AimaggTELaunchPad.class, new com.joshmanisdabomb.aimagg.te.tesr.AimaggTESRLaunchPad());
	}
	
}
