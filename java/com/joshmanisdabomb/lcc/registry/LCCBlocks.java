package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;

public abstract class LCCBlocks {
	
	public static final ArrayList<Block> all = new ArrayList<>();
	public static final ArrayList<BlockItem> allItem = new ArrayList<>();

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
	public static Block enriched_uranium_storage;

	//Nuclear
	public static Block nuclear_waste;
	public static Block nuclear_fire;

	public static void init(Register<Block> e) {
		//Test Blocks
		all.add(test_block = new Block(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)).setRegistryName(LCC.MODID, "test_block"));
		createDefaultBlockItem(test_block);
		all.add(test_block_2 = new TestHorizontalBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)).setRegistryName(LCC.MODID, "test_block_2"));
		createDefaultBlockItem(test_block_2);
		all.add(test_block_3 = new TestDirectionalBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)).setRegistryName(LCC.MODID, "test_block_3"));
		createDefaultBlockItem(test_block_3);
		all.add(test_block_4 = new TestPillarBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)).setRegistryName(LCC.MODID, "test_block_4"));
		createDefaultBlockItem(test_block_4);
		all.add(test_block_5 = new Block(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)).setRegistryName(LCC.MODID, "test_block_5"));
		createDefaultBlockItem(test_block_5);

		//Resources
		all.add(ruby_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)).setRegistryName(LCC.MODID, "ruby_ore"));
		createDefaultBlockItem(ruby_ore);
		all.add(ruby_storage = new Block(Block.Properties.create(Material.IRON, MaterialColor.TNT).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(LCC.MODID, "ruby_storage"));
		createDefaultBlockItem(ruby_storage);
		all.add(topaz_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)).setRegistryName(LCC.MODID, "topaz_ore"));
		createDefaultBlockItem(topaz_ore);
		all.add(topaz_storage = new Block(Block.Properties.create(Material.IRON, MaterialColor.WHITE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(LCC.MODID, "topaz_storage"));
		createDefaultBlockItem(topaz_storage);
		all.add(sapphire_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)).setRegistryName(LCC.MODID, "sapphire_ore"));
		createDefaultBlockItem(sapphire_ore);
		all.add(sapphire_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(LCC.MODID, "sapphire_storage"));
		createDefaultBlockItem(sapphire_storage);
		all.add(amethyst_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)).setRegistryName(LCC.MODID, "amethyst_ore"));
		createDefaultBlockItem(amethyst_ore);
		all.add(amethyst_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(LCC.MODID, "amethyst_storage"));
		createDefaultBlockItem(amethyst_storage);
		all.add(uranium_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(3.0F)).setRegistryName(LCC.MODID, "uranium_ore"));
		createDefaultBlockItem(uranium_ore);
		all.add(uranium_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.LIME).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(LCC.MODID, "uranium_storage"));
		createDefaultBlockItem(uranium_storage);
		all.add(enriched_uranium_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.LIME).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(LCC.MODID, "enriched_uranium_storage"));
		createDefaultBlockItem(enriched_uranium_storage);

		//Nuclear
		all.add(nuclear_waste = new NuclearWasteBlock(Block.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.CORAL)).setRegistryName(LCC.MODID, "nuclear_waste"));
		createDefaultBlockItem(nuclear_waste);
		all.add(nuclear_fire = new NuclearFireBlock(Block.Properties.create(Material.FIRE, DyeColor.LIME).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH).noDrops()).setRegistryName(LCC.MODID, "nuclear_fire"));
	}

	private static void createDefaultBlockItem(Block b) {
		allItem.add((BlockItem)new BlockItem(b, new Item.Properties().group(LCC.itemGroup)).setRegistryName(b.getRegistryName()));
	}
	
}