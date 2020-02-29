package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.DirectionalBlock;
import com.joshmanisdabomb.lcc.block.HorizontalBlock;
import com.joshmanisdabomb.lcc.block.*;
import com.joshmanisdabomb.lcc.entity.ClassicTNTEntity;
import com.joshmanisdabomb.lcc.misc.Colors.ClassicDyeColor;
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
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public abstract class LCCBlocks {
	
	public static final ArrayList<Block> all = new ArrayList<>();
	public static final ArrayList<BlockItem> allItem = new ArrayList<>();

	//Test Blocks
	public static Block test_block;
	public static HorizontalBlock test_block_2;
	public static DirectionalBlock test_block_3;
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
	public static HashMap<DyeColor, ChanneliteBlock> channelite = new HashMap<>();
	public static HashMap<DyeColor, ChanneliteSourceBlock> channelite_source = new HashMap<>();

	public static final BlockTags.Wrapper CANDY_CANES = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane"));
	public static final BlockTags.Wrapper CANDY_CANES_COATING = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "colored_candy_cane_coating"));
	public static final BlockTags.Wrapper REFINED_CANDY_CANES = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane"));
	public static final BlockTags.Wrapper REFINED_CANDY_CANES_COATING = new BlockTags.Wrapper(new ResourceLocation(LCC.MODID, "refined_colored_candy_cane_coating"));

	public static void init(Register<Block> e) {
		//Test Blocks
		addWithDefaultItem(test_block = new Block(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)), new ResourceLocation(LCC.MODID, "test_block"));
		addWithDefaultItem(test_block_2 = new HorizontalBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)), new ResourceLocation(LCC.MODID, "test_block_2"));
		addWithDefaultItem(test_block_3 = new DirectionalBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)), new ResourceLocation(LCC.MODID, "test_block_3"));
		addWithDefaultItem(test_block_4 = new PillarBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)), new ResourceLocation(LCC.MODID, "test_block_4"));
		addWithDefaultItem(test_block_5 = new TestConnectedTextureBlock(Block.Properties.create(Material.EARTH, DyeColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.SCAFFOLDING)), new ResourceLocation(LCC.MODID, "test_block_5"));
		
		//Gizmos
		//TODO: new recipe for road from oil > petroleum > road
		addWithDefaultItem(road = new RoadBlock(Block.Properties.create(Material.ROCK, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).doesNotBlockMovement().sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "road"));
		addWithDefaultItem(hydrated_soul_sand = new HydratedSoulSandBlock(Block.Properties.create(Material.SAND, MaterialColor.BROWN_TERRACOTTA).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.75F, 2.5F).sound(LCCSounds.hydrated_soul_sand)), new ResourceLocation(LCC.MODID, "hydrated_soul_sand"));
		add(hydrated_soul_sand_bubble_column = new FunctionalBubbleColumnBlock(state -> state.getBlock() == LCCBlocks.hydrated_soul_sand ? FunctionalBubbleColumnBlock.ColumnType.UPWARDS : FunctionalBubbleColumnBlock.ColumnType.NONE, Block.Properties.create(Material.BUBBLE_COLUMN).doesNotBlockMovement().noDrops()), new ResourceLocation(LCC.MODID, "hydrated_soul_sand_bubble_column"));
		addWithDefaultItem(bounce_pad = new BouncePadBlock(Block.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "bounce_pad"));
		
		//Resources
		addWithDefaultItem(ruby_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)), new ResourceLocation(LCC.MODID, "ruby_ore"));
		addWithDefaultItem(ruby_storage = new Block(Block.Properties.create(Material.IRON, MaterialColor.TNT).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "ruby_storage"));
		addWithDefaultItem(topaz_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)), new ResourceLocation(LCC.MODID, "topaz_ore"));
		addWithDefaultItem(topaz_storage = new Block(Block.Properties.create(Material.IRON, MaterialColor.WHITE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "topaz_storage"));
		addWithDefaultItem(sapphire_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)), new ResourceLocation(LCC.MODID, "sapphire_ore"));
		addWithDefaultItem(sapphire_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "sapphire_storage"));
		addWithDefaultItem(amethyst_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F)), new ResourceLocation(LCC.MODID, "amethyst_ore"));
		addWithDefaultItem(amethyst_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "amethyst_storage"));
		addWithDefaultItem(uranium_ore = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(3.0F)), new ResourceLocation(LCC.MODID, "uranium_ore"));
		addWithDefaultItem(uranium_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.LIME).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "uranium_storage"));
		addWithDefaultItem(enriched_uranium_storage = new Block(Block.Properties.create(Material.IRON, DyeColor.LIME).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "enriched_uranium_storage"));
		
		//Wasteland
		addWithDefaultItem(cracked_mud = new Block(Block.Properties.create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.6F, 0.1F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "cracked_mud"));
		add(oil = new OilBlock(() -> LCCFluids.oil, Block.Properties.create(Material.WATER).hardnessAndResistance(100.0F).noDrops()), new ResourceLocation(LCC.MODID, "oil"));

		//Nuclear
		addWithDefaultItem(atomic_bomb = new AtomicBombBlock(Block.Properties.create(Material.ANVIL, MaterialColor.IRON).hardnessAndResistance(9.0F, 1200.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.ANVIL)), new ResourceLocation(LCC.MODID, "atomic_bomb"));
		addWithDefaultItem(nuclear_waste = new NuclearWasteBlock(Block.Properties.create(Material.ROCK, MaterialColor.CYAN_TERRACOTTA).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.CORAL)), new ResourceLocation(LCC.MODID, "nuclear_waste"));
		add(nuclear_fire = new NuclearFireBlock(Block.Properties.create(Material.FIRE, DyeColor.LIME).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH).noDrops()), new ResourceLocation(LCC.MODID, "nuclear_fire"));

    	//Spreaders
		addWithDefaultItem(spreader_interface = new SpreaderInterfaceBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(7.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "spreader_interface"));
		factory(spreaders, color -> addWithDefaultItem(new SpreaderBlock(color, Block.Properties.create(Material.EARTH, color).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.15F, 0.0F).sound(SoundType.NETHER_WART)), new ResourceLocation(LCC.MODID, "spreader_" + color.getName())), DyeColor.values());

		//Computing
		add(computing = new ComputingBlock(Block.Properties.create(Material.IRON, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 0.0F).variableOpacity().sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "computing_block"));
		addWithDefaultItem(networking_cable = new CableBlock(CableBlock.NETWORKING_CABLE, Block.Properties.create(Material.IRON, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(2.0F, 0.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "networking_cable"));
		addWithDefaultItem(terminal_cable = new CableBlock(CableBlock.TERMINAL_CABLE, Block.Properties.create(Material.IRON, DyeColor.GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(2.0F, 0.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "terminal_cable"));
		factory(terminals, color -> addWithDefaultItem(new TerminalBlock(color, Block.Properties.create(Material.IRON, color).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 0.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "terminal_" + color.getName())), DyeColor.values());

		//Nostalgia
		add(time_rift = new TimeRiftBlock(Block.Properties.create(Material.EARTH, DyeColor.BLACK).hardnessAndResistance(5.0F, 0.0F).sound(SoundType.SWEET_BERRY_BUSH)), new ResourceLocation(LCC.MODID, "time_rift"));
		allItem.add((BlockItem)new BlockItem(time_rift, new Item.Properties().group(LCC.itemGroup).setTEISR(() -> TimeRiftRenderer.Item::new)).setRegistryName(LCC.MODID, "time_rift"));
		addWithDefaultItem(classic_grass_block = new ClassicGrassBlock(Block.Properties.create(Material.ORGANIC, DyeColor.LIME).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.6F).tickRandomly().sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_grass_block"));
		addWithDefaultItem(classic_cobblestone = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "classic_cobblestone"));
		addWithDefaultItem(classic_planks = new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).harvestLevel(0).hardnessAndResistance(2.0F, 5.0F).sound(SoundType.WOOD)), new ResourceLocation(LCC.MODID, "classic_planks"));
		addWithDefaultItem(classic_leaves = new FunctionalLeavesBlock(state -> state.getBlock() == Blocks.OAK_LOG, Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_leaves"));
		addWithDefaultItem(classic_sapling = new ClassicSaplingBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_sapling"));
		//TODO: Upgrade flower pots to Forge version
		add(potted_classic_sapling = new FlowerPotBlock(LCCBlocks.classic_sapling, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)), new ResourceLocation(LCC.MODID, "potted_classic_sapling"));
		addWithDefaultItem(classic_gravel = new FallingBlock(Block.Properties.create(Material.SAND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.6F).sound(SoundType.GROUND)) {
			@Override
			public int getDustColor(BlockState p_189876_1_) {
				return 0x9C9193;
			}
		}, new ResourceLocation(LCC.MODID, "classic_gravel"));
		addWithDefaultItem(classic_sponge = new ClassicSpongeBlock(Block.Properties.create(Material.SPONGE).hardnessAndResistance(0.6F).sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_sponge"));
		addWithDefaultItem(classic_glass = new GlassBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), new ResourceLocation(LCC.MODID, "classic_glass"));
		factory(classic_cloth, color -> addWithDefaultItem(new ShearableBlock(5.0F, Block.Properties.create(Material.WOOL, color.mapColor).hardnessAndResistance(0.8F).sound(SoundType.CLOTH)), new ResourceLocation(LCC.MODID, "classic_cloth_" + color.getName())), ClassicDyeColor.values());
		addWithDefaultItem(classic_rose = new PottableFlowerBlock(() -> LCCBlocks.potted_classic_rose.getDefaultState(), Effects.ABSORPTION, 4, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_rose"));
		add(potted_classic_rose = new FlowerPotBlock(LCCBlocks.classic_rose, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)), new ResourceLocation(LCC.MODID, "potted_classic_rose"));
		addWithDefaultItem(classic_cyan_flower = new PottableFlowerBlock(() -> LCCBlocks.potted_classic_cyan_flower.getDefaultState(), Effects.LEVITATION, 5, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_cyan_flower"));
		add(potted_classic_cyan_flower = new FlowerPotBlock(LCCBlocks.classic_cyan_flower, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)), new ResourceLocation(LCC.MODID, "potted_classic_cyan_flower"));
		addWithDefaultItem(classic_iron_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "classic_iron_block"));
		addWithDefaultItem(classic_smooth_iron_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "classic_smooth_iron_block"));
		addWithDefaultItem(classic_gold_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "classic_gold_block"));
		addWithDefaultItem(classic_smooth_gold_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "classic_smooth_gold_block"));
		addWithDefaultItem(classic_diamond_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.DIAMOND).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "classic_diamond_block"));
		addWithDefaultItem(classic_smooth_diamond_block = new Block(Block.Properties.create(Material.IRON, MaterialColor.DIAMOND).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "classic_smooth_diamond_block"));
		addWithDefaultItem(classic_bricks = new Block(Block.Properties.create(Material.ROCK, MaterialColor.RED).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "classic_bricks"));
		addWithDefaultItem(classic_tnt = new FunctionalTNTBlock(ClassicTNTEntity::new, true, Block.Properties.create(Material.TNT).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "classic_tnt"));
		addWithDefaultItem(classic_mossy_cobblestone = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(2.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "classic_mossy_cobblestone"));
		addWithDefaultItem(classic_chest = new ClassicChestBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD)), new ResourceLocation(LCC.MODID, "classic_chest"));
		addWithDefaultItem(nether_reactor = new NetherReactorBlock(Block.Properties.create(Material.ROCK, MaterialColor.CYAN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(4.0F, 5.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "nether_reactor"));
		addWithDefaultItem(crying_obsidian = new CryingObsidianBlock(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(50.0F, 1200.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "crying_obsidian"));
		addWithDefaultItem(glowing_obsidian = new Block(Block.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).harvestTool(ToolType.PICKAXE).harvestLevel(3).hardnessAndResistance(50.0F, 1200.0F).lightValue(12).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "glowing_obsidian"));
		addWithDefaultItem(cog = new CogBlock(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.IRON).hardnessAndResistance(0.0F).sound(SoundType.METAL)), new ResourceLocation(LCC.MODID, "cog"));
		
		//Rainbow
		addWithDefaultItem(rainbow_gate = new RainbowGateBlock(Block.Properties.create(Material.ROCK, MaterialColor.DIAMOND).hardnessAndResistance(1.5F, 6.0F)), new ResourceLocation(LCC.MODID, "rainbow_gate"));
		add(rainbow_portal = new RainbowPortalBlock(Block.Properties.create(Material.PORTAL, MaterialColor.TNT).doesNotBlockMovement().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).lightValue(15).noDrops()), new ResourceLocation(LCC.MODID, "rainbow_portal"));
		addWithDefaultItem(rainbow_grass_block = new FunctionalSnowlessGrassBlock(state -> {
			if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
			else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.rainbow_grass_block.getDefaultState();
			else return null;
		}, Block.Properties.create(Material.ORGANIC, MaterialColor.TNT).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "rainbow_grass_block"));
		addWithDefaultItem(sugar_grass_block = new FunctionalSnowlessGrassBlock(state -> {
			if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
			else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.sugar_grass_block.getDefaultState();
			else return null;
		}, Block.Properties.create(Material.ORGANIC, MaterialColor.PINK_TERRACOTTA).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "sugar_grass_block"));
		addWithDefaultItem(star_grass_block = new FunctionalSnowlessGrassBlock(state -> {
			if (state == null) return LCCBlocks.sparkling_dirt.getDefaultState();
			else if (state.getBlock() == LCCBlocks.sparkling_dirt) return LCCBlocks.star_grass_block.getDefaultState();
			else return null;
		}, Block.Properties.create(Material.ORGANIC, MaterialColor.SAND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "star_grass_block"));
		factory(sparkling_grass_block, color -> addWithDefaultItem(new SparklingGrassBlock(color, Block.Properties.create(Material.ORGANIC, color).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(6F, 3.0F).tickRandomly().sound(SoundType.PLANT)), new ResourceLocation(LCC.MODID, "sparkling_grass_block_" + color.getName())), DyeColor.values());
		addWithDefaultItem(sparkling_dirt = new Block(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(5F, 2.5F).sound(SoundType.GROUND)), new ResourceLocation(LCC.MODID, "sparkling_dirt"));
		addWithDefaultItem(twilight_stone = new Block(Block.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(15F, 6.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "twilight_stone"));
		addWithDefaultItem(twilight_cobblestone = new Block(Block.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 6.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "twilight_cobblestone"));
		addWithDefaultItem(candy_cane_red = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_red"));
		addWithDefaultItem(candy_cane_green = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_green"));
		addWithDefaultItem(candy_cane_blue = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_blue"));
		addWithDefaultItem(stripped_candy_cane = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "stripped_candy_cane"));
		addWithDefaultItem(candy_cane_coating_red = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_coating_red"));
		addWithDefaultItem(candy_cane_coating_green = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_coating_green"));
		addWithDefaultItem(candy_cane_coating_blue = new CandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_coating_blue"));
		addWithDefaultItem(stripped_candy_cane_coating = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "stripped_candy_cane_coating"));
		addWithDefaultItem(refined_candy_cane_red = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_candy_cane_red"));
		addWithDefaultItem(refined_candy_cane_green = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_candy_cane_green"));
		addWithDefaultItem(refined_candy_cane_blue = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_candy_cane_blue"));
		addWithDefaultItem(refined_stripped_candy_cane = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_stripped_candy_cane"));
		addWithDefaultItem(refined_candy_cane_coating_red = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_candy_cane_coating_red"));
		addWithDefaultItem(refined_candy_cane_coating_green = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_candy_cane_coating_green"));
		addWithDefaultItem(refined_candy_cane_coating_blue = new RefinedCandyCaneBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_candy_cane_coating_blue"));
		addWithDefaultItem(refined_stripped_candy_cane_coating = new PillarBlock(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "refined_stripped_candy_cane_coating"));
		addWithDefaultItem(candy_cane_block = new Block(Block.Properties.create(Material.ROCK, MaterialColor.LIGHT_GRAY).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(20F, 1.0F).sound(SoundType.STONE)), new ResourceLocation(LCC.MODID, "candy_cane_block"));
		factory(channelite, color -> add(new ChanneliteBlock(color, Block.Properties.create(Material.GLASS).lightValue(color != null ? 14 : 0).variableOpacity().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(7F, 2.0F).sound(SoundType.GLASS)), new ResourceLocation(LCC.MODID, "channelite_" + (color == null ? "empty" : color.getName()))), ArrayUtils.add(DyeColor.values(), null));
		createDefaultItem(channelite.get(null), new ResourceLocation(LCC.MODID, "channelite"));
		factory(channelite_source, color -> addWithDefaultItem(new ChanneliteSourceBlock(color, Block.Properties.create(Material.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(14F, 3.0F).sound(SoundType.GLASS)), new ResourceLocation(LCC.MODID, "channelite_source_" + color.getName())), DyeColor.values());
	}

	private static <B extends Block> B add(B b, ResourceLocation registry) {
		b.setRegistryName(registry);
		all.add(b);
		return b;
	}

	private static <B extends Block> B addWithDefaultItem(B b, ResourceLocation registry) {
		add(b, registry);
		createDefaultItem(b);
		return b;
	}

	private static BlockItem createDefaultItem(Block b) {
		return createDefaultItem(b, b.getRegistryName());
	}

	private static BlockItem createDefaultItem(Block b, ResourceLocation rl) {
		BlockItem bi = (BlockItem)new BlockItem(b, new Item.Properties().group(LCC.itemGroup)).setRegistryName(rl);
		allItem.add(bi);
		return bi;
	}

	private static <T, B extends Block> void factory(HashMap<T, B> map, Function<T, B> creator, T... values) {
		for (T value : values) {
			B b = creator.apply(value);
			map.put(value, b);
		}
	}
	
}