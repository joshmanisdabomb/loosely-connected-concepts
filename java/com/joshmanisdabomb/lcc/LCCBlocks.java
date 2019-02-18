package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.block.BlockTestDirectional;
import com.joshmanisdabomb.lcc.block.BlockTestHorizontal;
import com.joshmanisdabomb.lcc.block.BlockTestPillar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;

public class LCCBlocks {
	
	public static final ArrayList<Block> all = new ArrayList<Block>();
	public static final ArrayList<ItemBlock> allItem = new ArrayList<ItemBlock>();

	//Test Blocks
	public static Block test_block;
	public static Block test_block_2;
	public static Block test_block_3;
	public static Block test_block_4;
	public static Block test_block_5;

	public static void init(Register<Block> blockRegistryEvent) {
		//Test Blocks
		all.add(test_block = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.YELLOW)).setRegistryName(LCC.MODID, "test_block"));
		createDefaultItemBlock(test_block);
		all.add(test_block_2 = new BlockTestHorizontal(Block.Properties.create(Material.ROCK, EnumDyeColor.YELLOW)).setRegistryName(LCC.MODID, "test_block_2"));
		createDefaultItemBlock(test_block_2);
		all.add(test_block_3 = new BlockTestDirectional(Block.Properties.create(Material.ROCK, EnumDyeColor.YELLOW)).setRegistryName(LCC.MODID, "test_block_3"));
		createDefaultItemBlock(test_block_3);
		all.add(test_block_4 = new BlockTestPillar(Block.Properties.create(Material.ROCK, EnumDyeColor.YELLOW)).setRegistryName(LCC.MODID, "test_block_4"));
		createDefaultItemBlock(test_block_4);
		all.add(test_block_5 = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.YELLOW)).setRegistryName(LCC.MODID, "test_block_5"));
		createDefaultItemBlock(test_block_5);

		//Resources
		for (LCCResources r : LCCResources.values()) {
			if (r.ore != null) {
				all.add(r.ore);
				createDefaultItemBlock(r.ore);
			}
			if (r.storage != null) {
				all.add(r.storage);
				createDefaultItemBlock(r.storage);
			}
		}
	}

	private static void createDefaultItemBlock(Block b) {
		allItem.add((ItemBlock)new ItemBlock(b, new Item.Properties().group(LCC.itemGroup)).setRegistryName(b.getRegistryName()));
	}
	
}