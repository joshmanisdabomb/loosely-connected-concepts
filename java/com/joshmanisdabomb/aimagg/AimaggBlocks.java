package com.joshmanisdabomb.aimagg;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockAdvancedRendering;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicAxis;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicConnected;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicConnected.ConnectionType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicFacingAny;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicFacingHorizontal;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicGlass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHalf;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicSapling;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicStairs;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBillieTiles;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBouncePad;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockCandyCane;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockCandyCaneRefined;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockChocolate;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicChest;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicLeaves;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicWool;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicWorld;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockColored;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockComputerCable;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockComputerCase;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockFireNuclear;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockJelly;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockLaunchPad;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockOre;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockPoop;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowPad;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockScaffolding;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSoft;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpikes;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreader;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreaderInterface;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockStorage;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockWastelandWorld;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockWastelandWorld.WastelandWorldType;
import com.joshmanisdabomb.aimagg.event.AimaggModelHandler;
import com.joshmanisdabomb.aimagg.gen.classic.WorldGenBigClassicTree;
import com.joshmanisdabomb.aimagg.gen.classic.WorldGenClassicTree;
import com.joshmanisdabomb.aimagg.util.AimaggModelLoader;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlocks {
	
	public static Block testBlock;
	public static Block testBlock2;
	public static Block testBlock3;
	public static Block testBlock4;
	public static Block testBlock5;
	
	public static Block structureFoundation;
	
	public static Block soft;
	
	public static Block bouncePad;
	
	public static Block ore;
	public static Block storage;
	
	public static Block[] spreaders = new Block[16];
	public static Block spreaderInterface;
	
	public static Block computerCase;
	public static Block computerMonitor;
	public static Block computerComponent; //TODO mouse, keyboard
	public static Block computerCable;
	
	public static Block launchPad;

	//TODO uh oh another tile entity public static Block pillPrinter;
	
	public static Block nuclearFire; //TODO atom splitter or similar
	
	public static Block classicGrass;
	public static Block classicWorld;
	public static Block classicWool;
	public static Block classicFence;
	public static Block classicLeaves;
	public static Block classicSapling;
	public static Block classicChest;
	public static Block classicFlower;
	public static Block cryingObsidian;
	public static Block lockedChest;
	public static Block gear; //TODO vertical redstone
	public static Block desaturatedWool;
	public static Block reactor;
	public static Block stonecutter;
	
	public static Block rainbowGemBlock;
	public static Block rainbowPad;
	
	public static Block rainbowWorld;
	public static Block rainbowGrass;
	
	public static Block jelly; //TODO cherry, strawberry, grape, raspberry, apple, orange, blackberry, peach, pineapple, watermelon, lemon, chocolate, cola, bubblegum, cotton candy, fruit punch
							   //TODO american language file: jello, english langugae file: jelly
	public static Block candyCane;
	public static Block candyCaneRefined;
	public static Block cottonCandy; //TODO pink, purple, blue, cyan colors
	public static Block cottonCandyPole; //TODO
	public static Block cream; //TODO like snow layers
	public static Block chocolate; //TODO looks like lego bricks (white, milk, dark)
	public static Block cake; //TODO sponge, red velvet, chocolate, carrot
	
	//TODO custard liquid
	//TODO chocolate liquid
	//TODO milkshake (strawberry, banana, chocolate, vanilla) liquid

	public static Block wastelandWorld;
	public static Block brittlewood;
	public static Block brittlewoodStairs;
	public static Block brittlewoodSlab;
	public static Block brokenGlass;
	public static Block fortstone;
	public static Block poop;
	public static Block trap; //TODO bear trap that stuns you, activates redstone
	public static Block spikes;
	
	public static Block illuminantTile;
	public static Block scaffolding;

	public static final ArrayList<Block> registry = new ArrayList<Block>();
	public static final ArrayList<Item> ibRegistry = new ArrayList<Item>();
	
	public static final ArrayList<Block> colorRegistry = new ArrayList<Block>();
	public static final ArrayList<Item> ibColorRegistry = new ArrayList<Item>();
	
	public static final ArrayList<Block> advancedRenderRegistry = new ArrayList<Block>();
	
	public static void init() {
		testBlock = new AimaggBlockBasic("test_block", Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock).setSoundType(SoundType.CLOTH);
		testBlock2 = new AimaggBlockBasicFacingHorizontal("test_block_2", Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock2).setSoundType(SoundType.CLOTH);
		testBlock3 = new AimaggBlockBasicFacingAny("test_block_3", Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock3).setSoundType(SoundType.CLOTH);
		testBlock4 = new AimaggBlockBasicAxis("test_block_4", Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock4).setSoundType(SoundType.CLOTH);
		testBlock5 = new AimaggBlockBasicConnected(ConnectionType.INSIDE_ONE_TEXTURE, "test_block_5", "test/5", Material.GROUND, MapColor.YELLOW);
		((AimaggBlockBasic)testBlock5).setSoundType(SoundType.CLOTH);

		structureFoundation = new AimaggBlockBasic("structure_foundation", Material.GROUND, MapColor.LIME);
		structureFoundation.setCreativeTab(null);
		
		soft = new AimaggBlockSoft("soft", Material.GROUND);
		
		bouncePad = new AimaggBlockBouncePad("bounce_pad", Material.IRON, MapColor.GOLD);
		
		ore = new AimaggBlockOre("ore", Material.ROCK);
		storage = new AimaggBlockStorage("storage", Material.IRON);
		
		for (int i = 0; i < 16; i++) {
			spreaders[i] = new AimaggBlockSpreader(EnumDyeColor.byMetadata(i), "spreader_" + EnumDyeColor.byMetadata(i).getName(), Material.CLOTH).setHardness(0.2F).setResistance(0.05F);
			((AimaggBlockBasic)spreaders[i]).setHarvestLevel("shovel", 0);
			((AimaggBlockBasic)spreaders[i]).setDrops(spreaders[i], 1, 1, 0, 0.1F, 0, 0, 0.15F);
			((AimaggBlockBasic)spreaders[i]).setSilkTouchDrops(spreaders[i], 1, 1, 0, 1.0F);
			((AimaggBlockBasic)spreaders[i]).setSoundType(SoundType.CLOTH);
		}
		spreaderInterface = new AimaggBlockSpreaderInterface("spreader_interface", Material.IRON, MapColor.IRON).setHardness(7.0F);
		((AimaggBlockBasic)spreaderInterface).setSoundType(SoundType.METAL);
		spreaderInterface.setHarvestLevel("pickaxe", 2);
		
		computerCase = new AimaggBlockComputerCase("computer_case", Material.IRON);
		computerMonitor = null;
		computerComponent = null;
		computerCable = new AimaggBlockComputerCable("computer_cable", Material.IRON);
		
		launchPad = new AimaggBlockLaunchPad("launch_pad", Material.IRON, MapColor.IRON).setHardness(10.0F); //has soundtype, hardness and resistance
		((AimaggBlockBasic)launchPad).setSoundType(SoundType.METAL);
		launchPad.setHarvestLevel("pickaxe", 2);
		
		nuclearFire = new AimaggBlockFireNuclear("nuclear_fire", Material.FIRE, MapColor.LIME);
		
		classicGrass = new AimaggBlockBasicGrass("classic_grass", Material.GRASS, MapColor.GRASS, new Predicate<IBlockState>(){
			@Override
			public boolean apply(IBlockState state) {
				return state == Blocks.DIRT.getDefaultState();
			}
		}, Blocks.DIRT.getDefaultState());
		classicWorld = new AimaggBlockClassicWorld("classic_world", Material.GROUND);
		classicWool = new AimaggBlockClassicWool(true, "classic_wool", Material.CLOTH);
		classicFence = null;
		classicLeaves = new AimaggBlockClassicLeaves("classic_leaves", MapColor.LIME, new Predicate<IBlockState>(){
			@Override
			public boolean apply(IBlockState state) {
				return state.getBlock() instanceof BlockLog;
			}
		});
		classicSapling = new AimaggBlockBasicSapling("classic_sapling", MapColor.FOLIAGE, new WorldGenClassicTree(true), new WorldGenBigClassicTree(true));
		classicChest = new AimaggBlockClassicChest("classic_chest", Material.WOOD, MapColor.WOOD);
		classicFlower = null;
		cryingObsidian = null;
		lockedChest = null;
		gear = null;
		desaturatedWool = new AimaggBlockClassicWool(false, "desaturated_wool", Material.CLOTH);
		reactor = null;
		stonecutter = null;
		
		rainbowGemBlock = new AimaggBlockBasic("rainbow_gem_block", Material.IRON, MapColor.MAGENTA);
		rainbowPad = new AimaggBlockRainbowPad("rainbow_pad", Material.ROCK);
		((AimaggBlockBasic)rainbowPad).setDrops(rainbowPad, 1, 0);
		((AimaggBlockBasic)rainbowPad).setSilkTouchDrops(rainbowPad, 1, 1, 0, 1.0F);
		rainbowWorld = new AimaggBlockRainbowWorld("rainbow_world", Material.GROUND);
		rainbowGrass = new AimaggBlockRainbowGrass("rainbow_grass", Material.GRASS, new Predicate<IBlockState>(){
			@Override
			public boolean apply(IBlockState state) {
				return state == rainbowWorld.getDefaultState().withProperty(AimaggBlockRainbowWorld.TYPE, RainbowWorldType.DIRT);
			}
		}, rainbowWorld.getDefaultState().withProperty(AimaggBlockRainbowWorld.TYPE, RainbowWorldType.DIRT));
		((AimaggBlockBasic)rainbowGrass).setDrops(rainbowWorld, 1, RainbowWorldType.DIRT.getMetadata());
		((AimaggBlockBasic)rainbowGrass).setSilkTouchDrops(rainbowGrass, 1, 1, -1, 1.0F);
		candyCane = new AimaggBlockCandyCane("candy_cane", Material.ROCK);
		candyCaneRefined = new AimaggBlockCandyCaneRefined("refined_candy_cane", Material.ROCK);
		jelly = new AimaggBlockJelly("jelly", Material.SPONGE);
		chocolate = new AimaggBlockChocolate("chocolate", Material.ROCK);
		cream = null;
		cottonCandy = null;
		cottonCandyPole = null;
		cake = null;
		
		wastelandWorld = new AimaggBlockWastelandWorld("wasteland_world", Material.GROUND);
		brittlewood = new AimaggBlockBasicAxis("brittlewood", Material.WOOD, MapColor.WHITE_STAINED_HARDENED_CLAY);
		brittlewoodStairs = new AimaggBlockBasicStairs("brittlewood_stairs", wastelandWorld.getDefaultState().withProperty(AimaggBlockWastelandWorld.TYPE, WastelandWorldType.BRITTLEWOOD_PLANKS));
		brittlewoodSlab = new AimaggBlockBasicHalf("brittlewood_slab", wastelandWorld.getDefaultState().withProperty(AimaggBlockWastelandWorld.TYPE, WastelandWorldType.BRITTLEWOOD_PLANKS));
		brokenGlass = new AimaggBlockBasicGlass("broken_glass", Material.GLASS, MapColor.AIR);
		fortstone = new AimaggBlockBasicConnected(ConnectionType.INSIDE_ONE_TEXTURE, "fortstone", "wasteland/fortstone", Material.ROCK, MapColor.BLACK);
		poop = new AimaggBlockPoop("poop", Material.GROUND);
		spikes = new AimaggBlockSpikes("spikes", Material.IRON);

		illuminantTile = new AimaggBlockBillieTiles("illuminant_tile", Material.ROCK);
		scaffolding = new AimaggBlockScaffolding("scaffolding", Material.WOOD);
	}	

	@SideOnly(Side.CLIENT)
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
	public static void registerColoring() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return ((AimaggBlockColored)state.getBlock()).getColorFromBlock(state, worldIn, pos, tintIndex);
            }
        }, (Block[])colorRegistry.toArray(new Block[colorRegistry.size()]));
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return ((AimaggBlockColored)((ItemBlock)stack.getItem()).getBlock()).getColorFromItemstack(stack, tintIndex);
            }
        }, (Item[])ibColorRegistry.toArray(new Item[ibColorRegistry.size()]));
	}

	@SideOnly(Side.CLIENT)
	public static void registerAdvancedRenders() {
        ModelLoaderRegistry.registerLoader(new AimaggModelLoader());
		MinecraftForge.EVENT_BUS.register(new AimaggModelHandler());
		
		for (Block b : AimaggBlocks.advancedRenderRegistry) {
			ModelLoader.setCustomStateMapper(b, new StateMapperBase() {
				@Override
				protected ModelResourceLocation  getModelResourceLocation(IBlockState state) {
					System.out.println(((AimaggBlockAdvancedRendering)b).getInternalName());
					return ((AimaggBlockAdvancedRendering)b).getCustomModelLocation();
				}
			});
		}
	}
	
}
