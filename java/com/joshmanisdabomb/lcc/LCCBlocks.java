package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.block.BlockResourceOre;
import com.joshmanisdabomb.lcc.block.BlockTestDirectional;
import com.joshmanisdabomb.lcc.block.BlockTestHorizontal;
import com.joshmanisdabomb.lcc.block.BlockTestPillar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;

public class LCCBlocks {
	
	public static final ArrayList<Block> all = new ArrayList<>();
	public static final ArrayList<ItemBlock> allItem = new ArrayList<>();

	//Test Blocks
	public static Block test_block;
	public static Block test_block_2;
	public static Block test_block_3;
	public static Block test_block_4;
	public static Block test_block_5;

	//Resources
	public static Block ruby_ore;
	public static Block ruby_storage;
	public static Block topaz_ore;
	public static Block topaz_storage;
	public static Block sapphire_ore;
	public static Block sapphire_storage;
	public static Block amethyst_ore;
	public static Block amethyst_storage;
	public static Block uranium_ore;
	public static Block uranium_storage;

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
		all.add(ruby_ore = new BlockResourceOre(Block.Properties.create(Material.ROCK)).setRegistryName(LCC.MODID, "ruby_ore"));
		createDefaultItemBlock(ruby_ore);
		all.add(ruby_storage = new Block(Block.Properties.create(Material.ROCK, MaterialColor.TNT)).setRegistryName(LCC.MODID, "ruby_storage"));
		createDefaultItemBlock(ruby_storage);
		all.add(topaz_ore = new BlockResourceOre(Block.Properties.create(Material.ROCK)).setRegistryName(LCC.MODID, "topaz_ore"));
		createDefaultItemBlock(topaz_ore);
		all.add(topaz_storage = new Block(Block.Properties.create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA)).setRegistryName(LCC.MODID, "topaz_storage"));
		createDefaultItemBlock(topaz_storage);
		all.add(sapphire_ore = new BlockResourceOre(Block.Properties.create(Material.ROCK)).setRegistryName(LCC.MODID, "sapphire_ore"));
		createDefaultItemBlock(sapphire_ore);
		all.add(sapphire_storage = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.BLUE)).setRegistryName(LCC.MODID, "sapphire_storage"));
		createDefaultItemBlock(sapphire_storage);
		all.add(amethyst_ore = new BlockResourceOre(Block.Properties.create(Material.ROCK)).setRegistryName(LCC.MODID, "amethyst_ore"));
		createDefaultItemBlock(amethyst_ore);
		all.add(amethyst_storage = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.PURPLE)).setRegistryName(LCC.MODID, "amethyst_storage"));
		createDefaultItemBlock(amethyst_storage);
		all.add(uranium_ore = new BlockResourceOre(Block.Properties.create(Material.ROCK)).setRegistryName(LCC.MODID, "uranium_ore"));
		createDefaultItemBlock(uranium_ore);
		all.add(uranium_storage = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.LIME)).setRegistryName(LCC.MODID, "uranium_storage"));
		createDefaultItemBlock(uranium_storage);
	}

	private static void createDefaultItemBlock(Block b) {
		allItem.add((ItemBlock)new ItemBlock(b, new Item.Properties().group(LCC.itemGroup)).setRegistryName(b.getRegistryName()));
	}
	
}