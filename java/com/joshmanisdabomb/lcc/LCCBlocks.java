package com.joshmanisdabomb.lcc;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;

public class LCCBlocks {
	
	public static final ArrayList<Block> all = new ArrayList<Block>();
	public static final ArrayList<ItemBlock> allItem = new ArrayList<ItemBlock>();
	
	public static Block test_block;

	public static void init(Register<Block> blockRegistryEvent) {
		all.add(test_block = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.YELLOW)).setRegistryName(LCC.modid, "test_block"));
		createDefaultItemBlock(test_block, ItemGroup.BUILDING_BLOCKS);
	}

	private static void createDefaultItemBlock(Block b, ItemGroup g) {
		allItem.add((ItemBlock)new ItemBlock(b, new Item.Properties().group(g)).setRegistryName(b.getRegistryName()));
	}
	
}