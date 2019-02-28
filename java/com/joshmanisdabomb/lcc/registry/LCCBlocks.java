package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;

public abstract class LCCBlocks {
	
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
	public static Block enriched_uranium_storage;

	//Nuclear
	public static Block nuclear_waste;
	public static Block nuclear_fire;

	public static void init(Register<Block> e) {
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
		all.add(enriched_uranium_storage = new Block(Block.Properties.create(Material.ROCK, EnumDyeColor.LIME)).setRegistryName(LCC.MODID, "enriched_uranium_storage"));
		createDefaultItemBlock(enriched_uranium_storage);

		//Nuclear
		all.add(nuclear_waste = new BlockNuclearWaste(Block.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName(LCC.MODID, "nuclear_waste"));
		createDefaultItemBlock(nuclear_waste);
		all.add(nuclear_fire = new BlockNuclearFire(Block.Properties.create(Material.FIRE, EnumDyeColor.LIME).doesNotBlockMovement().needsRandomTick().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH)).setRegistryName(LCC.MODID, "nuclear_fire"));
	}

	private static void createDefaultItemBlock(Block b) {
		allItem.add((ItemBlock)new ItemBlock(b, new Item.Properties().group(LCC.itemGroup)).setRegistryName(b.getRegistryName()));
	}
	
}