package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.*;
import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity;
import com.joshmanisdabomb.lcc.misc.ClassicDyeColor;
import com.joshmanisdabomb.lcc.tileentity.render.TimeRiftRenderer;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent.Register;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class LCCBlocks {
	
	public static final ArrayList<Block> all = new ArrayList<>();
	public static final ArrayList<BlockItem> allItem = new ArrayList<>();

	//Test Blocks
	public static Block test_block;
	public static TestHorizontalBlock test_block_2;
	public static TestDirectionalBlock test_block_3;
	public static PillarBlock test_block_4;
	public static TestConnectedTextureBlock test_block_5;

	//Gizmos
	public static RoadBlock road;
	public static HydratedSoulSandBlock hydrated_soul_sand;
	public static FunctionalBubbleColumnBlock hydrated_soul_sand_bubble_column;
	public static BouncePadBlock bounce_pad;

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

	//Wasteland
	public static Block cracked_mud;
	public static OilBlock oil;

	//Nuclear
	public static AtomicBombBlock atomic_bomb;
	public static NuclearWasteBlock nuclear_waste;
	public static NuclearFireBlock nuclear_fire;

	//Spreaders
	public static SpreaderInterfaceBlock spreader_interface;
    public static HashMap<DyeColor, SpreaderBlock> spreaders = new HashMap<>();

    //Computing
	public static ComputingBlock computing;
	public static CableBlock networking_cable;
	public static HashMap<DyeColor, TerminalBlock> terminals = new HashMap<>();
	public static CableBlock terminal_cable;
	public static Block printer2d;
	public static Block printer3d;
	public static Block remoteAccess;

	//Nostalgia
	public static TimeRiftBlock time_rift;
	public static ClassicGrassBlock classic_grass_block;
	public static Block classic_cobblestone;
	public static Block classic_planks;
	public static ClassicSaplingBlock classic_sapling;
	public static FlowerPotBlock potted_classic_sapling;
	public static FallingBlock classic_gravel;
	public static FunctionalLeavesBlock classic_leaves;
	public static ClassicSpongeBlock classic_sponge;
	public static GlassBlock classic_glass;
	public static HashMap<ClassicDyeColor, ShearableBlock> classic_cloth = new HashMap<>();
	public static PottableFlowerBlock classic_rose;
	public static FlowerPotBlock potted_classic_rose;
	public static PottableFlowerBlock classic_cyan_flower;
	public static FlowerPotBlock potted_classic_cyan_flower;
	public static Block classic_iron_block;
	public static Block classic_smooth_iron_block;
	public static Block classic_gold_block;
	public static Block classic_smooth_gold_block;
	public static Block classic_diamond_block;
	public static Block classic_smooth_diamond_block;
	public static Block classic_bricks;
	public static FunctionalTNTBlock classic_tnt;
	public static Block classic_mossy_cobblestone;
	public static ClassicChestBlock classic_chest;
	public static NetherReactorBlock nether_reactor;
	public static CryingObsidianBlock crying_obsidian;
	public static Block glowing_obsidian;
	public static Block classic_stonecutter;
	public static CogBlock cog;

	//Rainbow
	public static RainbowGateBlock rainbow_gate;
    public static RainbowPortalBlock rainbow_portal;

	public static FunctionalSnowlessGrassBlock rainbow_grass_block;
	public static FunctionalSnowlessGrassBlock sugar_grass_block;
	public static FunctionalSnowlessGrassBlock star_grass_block;
	public static HashMap<DyeColor, SparklingGrassBlock> sparkling_grass_block = new HashMap<>();
	public static Block sparkling_dirt;
	public static Block twilight_stone;
	public static Block twilight_cobblestone;
	public static CandyCaneBlock candy_cane_red;
	public static CandyCaneBlock candy_cane_green;
	public static CandyCaneBlock candy_cane_blue;
	public static PillarBlock stripped_candy_cane;
	public static CandyCaneBlock candy_cane_coating_red;
	public static CandyCaneBlock candy_cane_coating_green;
	public static CandyCaneBlock candy_cane_coating_blue;
	public static PillarBlock stripped_candy_cane_coating;
	public static RefinedCandyCaneBlock refined_candy_cane_red;
	public static RefinedCandyCaneBlock refined_candy_cane_blue;
	public static RefinedCandyCaneBlock refined_candy_cane_green;
	public static PillarBlock refined_stripped_candy_cane;
	public static RefinedCandyCaneBlock refined_candy_cane_coating_red;
	public static RefinedCandyCaneBlock refined_candy_cane_coating_blue;
	public static RefinedCandyCaneBlock refined_candy_cane_coating_green;
	public static PillarBlock refined_stripped_candy_cane_coating;
	public static Block candy_cane_block;

	public static final BlockTags.Wrapper CANDY_CANES = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane"));
	public static final BlockTags.Wrapper CANDY_CANES_COATING = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane_coating"));
	public static final BlockTags.Wrapper REFINED_CANDY_CANES = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane"));
	public static final BlockTags.Wrapper REFINED_CANDY_CANES_COATING = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane_coating"));

	public static void init(Register<Block> e) {
		//Test Blocks
		all.add((test_block = new Block(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING))).setRegistryName(LCC.MODID, "test_block"));
		createDefaultBlockItem(test_block);
		all.add((test_block_2 = new TestHorizontalBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING))).setRegistryName(LCC.MODID, "test_block_2"));
		createDefaultBlockItem(test_block_2);
		all.add((test_block_3 = new TestDirectionalBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING))).setRegistryName(LCC.MODID, "test_block_3"));
		createDefaultBlockItem(test_block_3);
		all.add((test_block_4 = new PillarBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING))).setRegistryName(LCC.MODID, "test_block_4"));
		createDefaultBlockItem(test_block_4);
		all.add((test_block_5 = new TestConnectedTextureBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING))).setRegistryName(LCC.MODID, "test_block_5"));
		createDefaultBlockItem(test_block_5);

		//Gizmos
		//TODO: new recipe for road from oil > petroleum > road
		all.add((road = new RoadBlock(Block.Properties.create(Material.ROCK, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).doesNotBlockMovement().sound(SoundType.STONE))).setRegistryName(LCC.MODID, "road"));
		createDefaultBlockItem(road);
		all.add((hydrated_soul_sand = new HydratedSoulSandBlock(Block.Properties.create(Material.SAND, MaterialColor.BROWN_TERRACOTTA).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.75F, 2.5F).sound(LCCSounds.hydrated_soul_sand))).setRegistryName(LCC.MODID, "hydrated_soul_sand"));
		createDefaultBlockItem(hydrated_soul_sand);
		all.add((hydrated_soul_sand_bubble_column = new FunctionalBubbleColumnBlock(state -> state.getBlock() == LCCBlocks.hydrated_soul_sand ? FunctionalBubbleColumnBlock.ColumnType.UPWARDS : FunctionalBubbleColumnBlock.ColumnType.NONE, Block.Properties.create(Material.BUBBLE_COLUMN).doesNotBlockMovement().noDrops())).setRegistryName(LCC.MODID, "hydrated_soul_sand_bubble_column"));
		all.add((bounce_pad = new BouncePadBlock(Block.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "bounce_pad"));
		createDefaultBlockItem(bounce_pad);

		//Resources
		all.add((ruby_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F))).setRegistryName(LCC.MODID, "ruby_ore"));
		createDefaultBlockItem(ruby_ore);
		all.add((ruby_storage = new Block(Block.Properties.create(Material.IRON, MaterialColor.TNT).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "ruby_storage"));
		createDefaultBlockItem(ruby_storage);
		all.add((topaz_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F))).setRegistryName(LCC.MODID, "topaz_ore"));
		createDefaultBlockItem(topaz_ore);
		all.add((topaz_storage = new Block(Block.Properties.create(Material.IRON, MaterialColor.WHITE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "topaz_storage"));
		createDefaultBlockItem(topaz_storage);
		all.add((sapphire_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F))).setRegistryName(LCC.MODID, "sapphire_ore"));
		createDefaultBlockItem(sapphire_ore);
		all.add((sapphire_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "sapphire_storage"));
		createDefaultBlockItem(sapphire_storage);
		all.add((amethyst_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F))).setRegistryName(LCC.MODID, "amethyst_ore"));
		createDefaultBlockItem(amethyst_ore);
		all.add((amethyst_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "amethyst_storage"));
		createDefaultBlockItem(amethyst_storage);
		all.add((uranium_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(3.0F))).setRegistryName(LCC.MODID, "uranium_ore"));
		createDefaultBlockItem(uranium_ore);
		all.add((uranium_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.LIME).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "uranium_storage"));
		createDefaultBlockItem(uranium_storage);
		all.add((enriched_uranium_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.LIME).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "enriched_uranium_storage"));
		createDefaultBlockItem(enriched_uranium_storage);

		//Wasteland
		all.add((cracked_mud = new Block(Block.Properties.create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.6F, 0.1F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "cracked_mud"));
		createDefaultBlockItem(cracked_mud);
		all.add((oil = new OilBlock(() -> LCCFluids.oil, Block.Properties.create(Material.WATER).hardnessAndResistance(100.0F).noDrops())).setRegistryName(LCC.MODID, "oil"));

		//Nuclear
		all.add((atomic_bomb = new AtomicBombBlock(Block.Properties.create(Material.ANVIL, MaterialColor.IRON).hardnessAndResistance(9.0F, 1200.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.ANVIL))).setRegistryName(LCC.MODID, "atomic_bomb"));
		createDefaultBlockItem(atomic_bomb);
		all.add((nuclear_waste = new NuclearWasteBlock(Block.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.CORAL))).setRegistryName(LCC.MODID, "nuclear_waste"));
		createDefaultBlockItem(nuclear_waste);
		all.add((nuclear_fire = new NuclearFireBlock(Block.Properties.create(Material.FIRE, DyeColor.LIME).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH).noDrops())).setRegistryName(LCC.MODID, "nuclear_fire"));

    	//Spreaders
		all.add((spreader_interface = new SpreaderInterfaceBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(7.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "spreader_interface"));
		createDefaultBlockItem(spreader_interface);
		for (DyeColor color : DyeColor.values()) {
			SpreaderBlock b;
			all.add((b = new SpreaderBlock(color, Block.Properties.create(Material.EARTH, color).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.15F, 0.0F).sound(SoundType.NETHER_WART))).setRegistryName(LCC.MODID, "spreader_" + color.getName()));
			spreaders.put(color, b);
			createDefaultBlockItem(b);
		}

		//Computing
		all.add((computing = new ComputingBlock(Block.Properties.create(Material.IRON, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 0.0F).variableOpacity().sound(SoundType.METAL))).setRegistryName(LCC.MODID, "computing_block"));
		all.add((networking_cable = new CableBlock(CableBlock.NETWORKING_CABLE, Block.Properties.create(Material.IRON, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(2.0F, 0.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "networking_cable"));
		createDefaultBlockItem(networking_cable);
		all.add((terminal_cable = new CableBlock(CableBlock.TERMINAL_CABLE, Block.Properties.create(Material.IRON, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(2.0F, 0.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "terminal_cable"));
		createDefaultBlockItem(terminal_cable);
		for (DyeColor color : DyeColor.values()) {
			TerminalBlock b;
			all.add((b = new TerminalBlock(color, Block.Properties.create(Material.IRON, color).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 0.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "terminal_" + color.getName()));
			terminals.put(color, b);
			createDefaultBlockItem(b);
		}

		//Nostalgia
		all.add((time_rift = new TimeRiftBlock(Block.Properties.create(Material.EARTH, DyeColor.BLACK).hardnessAndResistance(5.0F, 0.0F).sound(SoundType.SWEET_BERRY_BUSH))).setRegistryName(LCC.MODID, "time_rift"));
		allItem.add((BlockItem)new BlockItem(time_rift, new Item.Properties().group(LCC.itemGroup).setTEISR(() -> TimeRiftRenderer.Item::new)).setRegistryName(LCC.MODID, "time_rift"));
		all.add((classic_grass_block = new ClassicGrassBlock(Block.Properties.create(Material.ORGANIC, DyeColor.LIME).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.6F).tickRandomly().sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_grass_block"));
		createDefaultBlockItem(classic_grass_block);
		all.add((classic_cobblestone = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "classic_cobblestone"));
		createDefaultBlockItem(classic_cobblestone);
		all.add((classic_planks = new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).harvestLevel(0).hardnessAndResistance(2.0F, 5.0F).sound(SoundType.WOOD))).setRegistryName(LCC.MODID, "classic_planks"));
		createDefaultBlockItem(classic_planks);
		all.add((classic_leaves = new FunctionalLeavesBlock(state -> state.getBlock() == Blocks.OAK_LOG, Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_leaves"));
		createDefaultBlockItem(classic_leaves);
		all.add((classic_sapling = new ClassicSaplingBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_sapling"));
		createDefaultBlockItem(classic_sapling);
		//TODO: Upgrade flower pots to Forge version
		all.add((potted_classic_sapling = new FlowerPotBlock(LCCBlocks.classic_sapling, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F))).setRegistryName(LCC.MODID, "potted_classic_sapling"));
		all.add((classic_gravel = new FallingBlock(Block.Properties.create(Material.SAND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.6F).sound(SoundType.GROUND)) {
			@Override
			public int getDustColor(BlockState p_189876_1_) {
				return 0x9C9193;
			}
		}).setRegistryName(LCC.MODID, "classic_gravel"));
		createDefaultBlockItem(classic_gravel);
		all.add((classic_sponge = new ClassicSpongeBlock(Block.Properties.create(Material.SPONGE).hardnessAndResistance(0.6F).sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_sponge"));
		createDefaultBlockItem(classic_sponge);
		all.add((classic_glass = new GlassBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS))).setRegistryName(LCC.MODID, "classic_glass"));
		createDefaultBlockItem(classic_glass);
		for (ClassicDyeColor color : ClassicDyeColor.values()) {
			ShearableBlock b;
			all.add((b = new ShearableBlock(5.0F, Block.Properties.create(Material.WOOL, color.mapColor).hardnessAndResistance(0.8F).sound(SoundType.CLOTH))).setRegistryName(LCC.MODID, "classic_cloth_" + color.getName()));
			classic_cloth.put(color, b);
			createDefaultBlockItem(b);
		}
		all.add((classic_rose = new PottableFlowerBlock(() -> LCCBlocks.potted_classic_rose.getDefaultState(), Effects.ABSORPTION, 4, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_rose"));
		createDefaultBlockItem(classic_rose);
		all.add((potted_classic_rose = new FlowerPotBlock(LCCBlocks.classic_rose, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F))).setRegistryName(LCC.MODID, "potted_classic_rose"));
		all.add((classic_cyan_flower = new PottableFlowerBlock(() -> LCCBlocks.potted_classic_cyan_flower.getDefaultState(), Effects.LEVITATION, 5, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_cyan_flower"));
		createDefaultBlockItem(classic_cyan_flower);
		all.add((potted_classic_cyan_flower = new FlowerPotBlock(LCCBlocks.classic_cyan_flower, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F))).setRegistryName(LCC.MODID, "potted_classic_cyan_flower"));
		all.add((classic_iron_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "classic_iron_block"));
		createDefaultBlockItem(classic_iron_block);
		all.add((classic_smooth_iron_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "classic_smooth_iron_block"));
		createDefaultBlockItem(classic_smooth_iron_block);
		all.add((classic_gold_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "classic_gold_block"));
		createDefaultBlockItem(classic_gold_block);
		all.add((classic_smooth_gold_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "classic_smooth_gold_block"));
		createDefaultBlockItem(classic_smooth_gold_block);
		all.add((classic_diamond_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.DIAMOND).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "classic_diamond_block"));
		createDefaultBlockItem(classic_diamond_block);
		all.add((classic_smooth_diamond_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.DIAMOND).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "classic_smooth_diamond_block"));
		createDefaultBlockItem(classic_smooth_diamond_block);
		all.add((classic_bricks = new Block(Block.Properties.create(Material.ROCK, MaterialColor.RED).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "classic_bricks"));
		createDefaultBlockItem(classic_bricks);
		all.add((classic_tnt = new FunctionalTNTBlock(ClassicTNTEntity::new, true, Block.Properties.create(Material.TNT).hardnessAndResistance(0.0F).sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "classic_tnt"));
		createDefaultBlockItem(classic_tnt);
		all.add((classic_mossy_cobblestone = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "classic_mossy_cobblestone"));
		createDefaultBlockItem(classic_mossy_cobblestone);
		all.add((classic_chest = new ClassicChestBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD))).setRegistryName(LCC.MODID, "classic_chest"));
		createDefaultBlockItem(classic_chest);
		all.add((nether_reactor = new NetherReactorBlock(Block.Properties.create(Material.ROCK, MaterialColor.CYAN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(4.0F, 5.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "nether_reactor"));
		createDefaultBlockItem(nether_reactor);
		all.add((crying_obsidian = new CryingObsidianBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(50.0F, 1200.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "crying_obsidian"));
		createDefaultBlockItem(crying_obsidian);
		all.add((glowing_obsidian = new Block(Block.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(50.0F, 1200.0F).lightValue(12).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "glowing_obsidian"));
		createDefaultBlockItem(glowing_obsidian);
		all.add((cog = new CogBlock(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.IRON).hardnessAndResistance(0.0F).sound(SoundType.METAL))).setRegistryName(LCC.MODID, "cog"));
		createDefaultBlockItem(cog);

		//Rainbow
		all.add((rainbow_gate = new RainbowGateBlock(Block.Properties.create(Material.ROCK, MaterialColor.DIAMOND).hardnessAndResistance(1.5F, 6.0F))).setRegistryName(LCC.MODID, "rainbow_gate"));
		createDefaultBlockItem(rainbow_gate);
		all.add((rainbow_portal = new RainbowPortalBlock(Block.Properties.create(Material.PORTAL, MaterialColor.TNT).doesNotBlockMovement().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).lightValue(15).noDrops())).setRegistryName(LCC.MODID, "rainbow_portal"));
		all.add((rainbow_grass_block = new FunctionalSnowlessGrassBlock(state -> {
			if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
			else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.rainbow_grass_block.getDefaultState();
			else return null;
		}, Block.Properties.create(Material.ORGANIC, MaterialColor.TNT).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "rainbow_grass_block"));
		createDefaultBlockItem(rainbow_grass_block);
		all.add((sugar_grass_block = new FunctionalSnowlessGrassBlock(state -> {
			if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
			else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.sugar_grass_block.getDefaultState();
			else return null;
		}, Block.Properties.create(Material.ORGANIC, MaterialColor.PINK_TERRACOTTA).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "sugar_grass_block"));
		createDefaultBlockItem(sugar_grass_block);
		all.add((star_grass_block = new FunctionalSnowlessGrassBlock(state -> {
			if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
			else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.star_grass_block.getDefaultState();
			else return null;
		}, Block.Properties.create(Material.ORGANIC, MaterialColor.SAND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "star_grass_block"));
		createDefaultBlockItem(star_grass_block);
		for (DyeColor color : DyeColor.values()) {
			SparklingGrassBlock b;
			all.add((b = new SparklingGrassBlock(color, Block.Properties.create(Material.ORGANIC, color).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT))).setRegistryName(LCC.MODID, "sparkling_grass_block_" + color.getName()));
			sparkling_grass_block.put(color, b);
			createDefaultBlockItem(b);
		}
		all.add((sparkling_dirt = new Block(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(5F, 2.5F).sound(SoundType.GROUND))).setRegistryName(LCC.MODID, "sparkling_dirt"));
		createDefaultBlockItem(sparkling_dirt);
		all.add((twilight_stone = new Block(Block.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(15F, 6.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "twilight_stone"));
		createDefaultBlockItem(twilight_stone);
		all.add((twilight_cobblestone = new Block(Block.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 6.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "twilight_cobblestone"));
		createDefaultBlockItem(twilight_cobblestone);
		all.add((candy_cane_red = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_red"));
		createDefaultBlockItem(candy_cane_red);
		all.add((candy_cane_green = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_green"));
		createDefaultBlockItem(candy_cane_green);
		all.add((candy_cane_blue = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_blue"));
		createDefaultBlockItem(candy_cane_blue);
		all.add((stripped_candy_cane = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "stripped_candy_cane"));
		createDefaultBlockItem(stripped_candy_cane);
		all.add((candy_cane_coating_red = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_coating_red"));
		createDefaultBlockItem(candy_cane_coating_red);
		all.add((candy_cane_coating_green = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_coating_green"));
		createDefaultBlockItem(candy_cane_coating_green);
		all.add((candy_cane_coating_blue = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_coating_blue"));
		createDefaultBlockItem(candy_cane_coating_blue);
		all.add((stripped_candy_cane_coating = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "stripped_candy_cane_coating"));
		createDefaultBlockItem(stripped_candy_cane_coating);
		all.add((refined_candy_cane_red = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_candy_cane_red"));
		createDefaultBlockItem(refined_candy_cane_red);
		all.add((refined_candy_cane_green = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_candy_cane_green"));
		createDefaultBlockItem(refined_candy_cane_green);
		all.add((refined_candy_cane_blue = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_candy_cane_blue"));
		createDefaultBlockItem(refined_candy_cane_blue);
		all.add((refined_stripped_candy_cane = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_stripped_candy_cane"));
		createDefaultBlockItem(refined_stripped_candy_cane);
		all.add((refined_candy_cane_coating_red = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_candy_cane_coating_red"));
		createDefaultBlockItem(refined_candy_cane_coating_red);
		all.add((refined_candy_cane_coating_green = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_candy_cane_coating_green"));
		createDefaultBlockItem(refined_candy_cane_coating_green);
		all.add((refined_candy_cane_coating_blue = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_candy_cane_coating_blue"));
		createDefaultBlockItem(refined_candy_cane_coating_blue);
		all.add((refined_stripped_candy_cane_coating = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "refined_stripped_candy_cane_coating"));
		createDefaultBlockItem(refined_stripped_candy_cane_coating);
		all.add((candy_cane_block = new Block(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE))).setRegistryName(LCC.MODID, "candy_cane_block"));
		createDefaultBlockItem(candy_cane_block);
	}

	private static void createDefaultBlockItem(Block b) {
		allItem.add((BlockItem)new BlockItem(b, new Item.Properties().group(LCC.itemGroup)).setRegistryName(b.getRegistryName()));
	}
	
}