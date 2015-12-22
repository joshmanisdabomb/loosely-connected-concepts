package yam.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import yam.ChestLoot;
import yam.YetAnotherMod;
import yam.gen.rainbow.GenCandyCane;
import yam.gen.rainbow.GenRainbowTemple;
import yam.gen.rainbow.WorldGenCloud;
import yam.gen.sheol.WorldGenHotCoal;
import yam.gen.wasteland.GenBloodwoodTrees;
import yam.gen.wasteland.GenSpikeTrap;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == -1) {generateNether(world, random, chunkX * 16, chunkZ * 16);}
		if (world.provider.dimensionId == 0) {generateSurface(world, random, chunkX * 16, chunkZ * 16);}
		if (world.provider.dimensionId == 1) {generateEnd(world, random, chunkX * 16, chunkZ * 16);}
		if (world.provider.dimensionId == YetAnotherMod.rainbowDimID) {generateRainbow(world, random, chunkX * 16, chunkZ * 16);}
		if (world.provider.dimensionId == YetAnotherMod.sheolDimID) {generateSheol(world, random, chunkX * 16, chunkZ * 16);}
		if (world.provider.dimensionId == YetAnotherMod.moonDimID) {generateMoon(world, random, chunkX * 16, chunkZ * 16);}
	}
	
	public static boolean isX2Y2RegionClear(World world, int x, int y, int z, int x2, int y2, int z2) {
		boolean flag = false;
		for (int i = x; i <= x2; i++) {
			for (int j = y; j <= y2; j++) {
				for (int k = z; k <= z2; k++) {
					Block block = world.getBlock(i, j, k);
					if (block == null) {System.out.println("Block was null."); return false;}
					flag = flag || !block.isAir(world, i, j, k);
				}
			}
		}
		return !flag;
	}
	
	public static boolean isWHDRegionClear(World world, int x, int y, int z, int w, int h, int d) {
		return isX2Y2RegionClear(world, x, y, z, x+w-1, y+h-1, z+d-1);
	}
	
	public static void X2Y2RegionClear(World world, int x, int y, int z, int x2, int y2, int z2) {
		for (int i = x; i <= x2; i++) {
			for (int j = y; j <= y2; j++) {
				for (int k = z; k <= z2; k++) {
					world.setBlock(i,j,k,Blocks.air);
				}
			}
		}
	}
	
	public static void WHDRegionClear(World world, int x, int y, int z, int w, int h, int d) {
		X2Y2RegionClear(world, x, y, z, x+w-1, y+h-1, z+d-1);
	}

	public static void flattenGround(World world, int x, int y, int z, int w, int d, Block fillerBlock, Block topBlock) {
		for (int i = x; i <= x+w; i++) {
			for (int k = z; k <= z+d; k++) {
				for (int j = y - 1; !world.isBlockNormalCubeDefault(i, j, k, false); j--) {
					world.setBlock(i, j, k, fillerBlock);
				}
				world.setBlock(i, y, k, topBlock);
			}
		}
	}
	
	public static void roundedFlattenGround(World world, int x, int y, int z, int w, int d, Block fillerBlock, Block topBlock) {
		for (int i = x; i <= x+w; i++) {
			for (int k = z; k <= z+d; k++) {
				if (!((i == x && k == z) || (i == x+w && k == z) || (i == x && k == z+d) || (i == x+w && k == z+d))) {
					for (int j = y - 1; !world.isBlockNormalCubeDefault(i, j, k, false); j--) {
						world.setBlock(i, j, k, fillerBlock);
					}
					world.setBlock(i, y, k, topBlock);
				}
			}
		}
	}
	
	public static void clearAbove(World world, int x, int y, int z, int w, int d) {
		for (int i = x; i <= x+w; i++) {
			for (int k = z; k <= z+w; k++) {
				for (int j = y+1; !world.canBlockSeeTheSky(i,y+1,k); j++) {
					world.setBlock(i,j,k,Blocks.air);
				}
			}
		}
	}
	
	public static void setFurnace(World world, int x, int y, int z, int direction, String type) {
		TileEntityFurnace furnace = new TileEntityFurnace();
		world.setBlock(x,y,z,Blocks.furnace,direction+2,3);
		world.setTileEntity(x,y,z,furnace);
		if (type == "halfplayer:1") {ChestLoot.halfPlayerTier1(furnace); return;}
		if (type == "halfplayer:2") {ChestLoot.halfPlayerTier2(furnace); return;}
		if (type == "halfplayer:3") {ChestLoot.halfPlayerTier3(furnace); return;}
		if (type == "halfplayer:4") {ChestLoot.halfPlayerTier4(furnace); return;}
	}
	
	public static void setChest(World world, int x, int y, int z, int direction, String type, boolean trapped) {
		TileEntityChest chest = new TileEntityChest();
		world.setBlock(x,y,z,trapped ? Blocks.trapped_chest : Blocks.chest,direction+2,3);
		world.setTileEntity(x,y,z,chest);
		if (type == "diamonds") {ChestLoot.diamonds(chest); return;}
		if (type == "halfplayer:1") {ChestLoot.halfPlayerTier1(chest); return;}
		if (type == "halfplayer:2") {ChestLoot.halfPlayerTier2(chest); return;}
		if (type == "halfplayer:3") {ChestLoot.halfPlayerTier3(chest); return;}
		if (type == "halfplayer:4") {ChestLoot.halfPlayerTier4(chest); return;}
	}

	private void generateSurface(World world, Random random, int i, int j) {
		if (world.getBiomeGenForCoords(i,j) != YetAnotherMod.biomeWasteland) {
			int RandPosX; int RandPosY; int RandPosZ;
			//Crystal Ore
			for (int k = 0; k < 6; k++) {
				RandPosX = i + random.nextInt(16);
				RandPosY = random.nextInt(128);
				RandPosZ = j + random.nextInt(16);
				(new WorldGenMinable(YetAnotherMod.crystalOre, 24)).generate(world, random, RandPosX, RandPosY, RandPosZ);
			}
			
			//Ruby Ore
			for (int k = 0; k < 6; k++) {
				RandPosX = i + random.nextInt(16);
				RandPosY = random.nextInt(32) + 16;
				RandPosZ = j + random.nextInt(16);
	
				if (BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(RandPosX, RandPosZ),BiomeDictionary.Type.WATER)) {
					(new WorldGenMinable(YetAnotherMod.rubyOre, 8)).generate(world, random, RandPosX, RandPosY, RandPosZ);
				}
			}
			
			//Pearl Ore
			for (int k = 0; k < 16; k++) {
				RandPosX = i + random.nextInt(16);
				RandPosY = 48 + random.nextInt(16);
				RandPosZ = j + random.nextInt(16);
					
				(new WorldGenMinable(YetAnotherMod.pearlOre, 10, Blocks.clay)).generate(world, random, RandPosX, RandPosY, RandPosZ);
			}
			
			//Salt Ore
			RandPosX = i + random.nextInt(16);
			RandPosY = 64 + random.nextInt(64);
			RandPosZ = j + random.nextInt(16);
				
			(new WorldGenMinable(YetAnotherMod.saltOre, 8)).generate(world, random, RandPosX, RandPosY, RandPosZ);
	
			for (int k = 0; k < 3; k++) {
				//Quicksand
				RandPosX = i + random.nextInt(16);
				RandPosY = 48 + random.nextInt(48);
				RandPosZ = j + random.nextInt(16);
	
				if (BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(RandPosX, RandPosZ),BiomeDictionary.Type.DESERT)) {
					(new WorldGenMinable(YetAnotherMod.quicksand, 40, Blocks.sand)).generate(world, random, RandPosX, RandPosY, RandPosZ);
				}
			}
			
			//Mud
			for (int k = 0; k < 15; k++) {
				RandPosX = i + random.nextInt(16);
				RandPosY = 48 + random.nextInt(48);
				RandPosZ = j + random.nextInt(16);
					
				if (world.getBiomeGenForCoords(RandPosX, RandPosZ) == BiomeGenBase.swampland || world.getBiomeGenForCoords(RandPosX, RandPosZ) == BiomeGenBase.jungle || world.getBiomeGenForCoords(RandPosX, RandPosZ) == BiomeGenBase.jungleHills || world.getBiomeGenForCoords(RandPosX, RandPosZ) == BiomeGenBase.roofedForest) {
					(new WorldGenMinable(YetAnotherMod.mud, 40, Blocks.grass)).generate(world, random, RandPosX, RandPosY, RandPosZ);
					(new WorldGenMinable(YetAnotherMod.mud, 40, Blocks.dirt)).generate(world, random, RandPosX, RandPosY, RandPosZ);
				}
			}
		} else {
			int RandPosX; int RandPosY; int RandPosZ;

			for (int k = 0; k < 10; k++) {
				//Spike Trap
				RandPosX = i + random.nextInt(16);
				RandPosY = 16 + random.nextInt(64);
				RandPosZ = j + random.nextInt(16);
				(new GenSpikeTrap()).generate(world, random, RandPosX, RandPosY, RandPosZ);
			}
		
			//Uranium
			RandPosX = i + random.nextInt(16);
			RandPosY = random.nextInt(24);
			RandPosZ = j + random.nextInt(16);
				
			(new WorldGenMinable(YetAnotherMod.uraniumOre, 10)).generate(world, random, RandPosX, RandPosY, RandPosZ);
	
			//Rust
			RandPosX = i + random.nextInt(16);
			RandPosY = 16 + random.nextInt(64);
			RandPosZ = j + random.nextInt(16);
				
			(new WorldGenMinable(YetAnotherMod.rustOre, 20)).generate(world, random, RandPosX, RandPosY, RandPosZ);
			
			for (int k = 0; k < 8; k++) {
				//Reinforced Stone
				RandPosX = i + random.nextInt(16);
				RandPosY = 56 + random.nextInt(32);
				RandPosZ = j + random.nextInt(16);
	
				(new WorldGenMinable(YetAnotherMod.reinforcedStone, 32, YetAnotherMod.crackedMud)).generate(world, random, RandPosX, RandPosY, RandPosZ);
			}
			
			for (int k = 0; k < 25; k++) {
				//Poop
				RandPosX = i + random.nextInt(16);
				RandPosY = 56 + random.nextInt(32);
				RandPosZ = j + random.nextInt(16);
	
				if (world.getBlock(RandPosX, RandPosY - 1, RandPosZ) == YetAnotherMod.crackedMud && world.getBlock(RandPosX, RandPosY, RandPosZ) == Blocks.air) {
					world.setBlock(RandPosX, RandPosY, RandPosZ, YetAnotherMod.poop);
				}
			}
			
			//Bloodwood Trees
			for (int k = 0; k < 30; k++) {
				RandPosX = i + random.nextInt(16);
				RandPosY = 64 + random.nextInt(64);
				RandPosZ = j + random.nextInt(16);
				(new GenBloodwoodTrees()).generate(world, random, RandPosX, RandPosY, RandPosZ, random.nextInt(9) + 3);
			}
		}
	}
	
	private void generateNether(World world, Random random, int i, int j) {}

	private void generateEnd(World world, Random random, int i, int j) {}
	
	private void generateRainbow(World world, Random random, int i, int j) {
		int RandPosX; int RandPosY; int RandPosZ;

		//Energy Crystal Ore
		RandPosX = i + random.nextInt(16);
		RandPosY = random.nextInt(24);
		RandPosZ = j + random.nextInt(16);
			
		(new WorldGenMinable(YetAnotherMod.crystalEnergyOre, 6, YetAnotherMod.rainbowStone)).generate(world, random, RandPosX, RandPosY, RandPosZ);

		for (int k = 0; k < 3; k++) {
			//Neon Ore
			RandPosX = i + random.nextInt(16);
			RandPosY = random.nextInt(128);
			RandPosZ = j + random.nextInt(16);
				
			(new WorldGenMinable(YetAnotherMod.neonOre, 8, YetAnotherMod.rainbowStone)).generate(world, random, RandPosX, RandPosY, RandPosZ);
		}
		
		for (int k = 0; k < 3; k++) {
			//Aerstone Ore
			RandPosX = i + random.nextInt(16);
			RandPosY = random.nextInt(40);
			RandPosZ = j + random.nextInt(16);
				
			(new WorldGenMinable(YetAnotherMod.aerstoneRaw, 32, YetAnotherMod.rainbowStone)).generate(world, random, RandPosX, RandPosY, RandPosZ);
		}
		
		if (random.nextInt(3) == 0) {
			//Clouds
			RandPosX = i + random.nextInt(16);
			RandPosY = 128 + random.nextInt(100);
			RandPosZ = j + random.nextInt(16);
				
			(new WorldGenCloud(YetAnotherMod.cloud, 48)).generate(world, random, RandPosX, RandPosY, RandPosZ);
		}
		
		//Rainbow Temples
		if (random.nextInt(7) == 0) {
			RandPosX = i + random.nextInt(16);
			RandPosY = 64 + random.nextInt(64);
			RandPosZ = j + random.nextInt(16);
			(new GenRainbowTemple()).generate(world, random, RandPosX, RandPosY, RandPosZ);
		}
		
		//Candy Canes
		for (int k = 0; k < 30; k++) {
			RandPosX = i + random.nextInt(16);
			RandPosY = 64 + random.nextInt(64);
			RandPosZ = j + random.nextInt(16);
			(new GenCandyCane()).generate(world, random, RandPosX, RandPosY, RandPosZ, random.nextInt(6) + 3);
		}
	}
	
	private void generateSheol(World world, Random random, int i, int j) {
		int RandPosX; int RandPosY; int RandPosZ;
		
		//Hot Coal Blocks
		RandPosX = i + random.nextInt(16);
		RandPosY = random.nextInt(256);
		RandPosZ = j + random.nextInt(16);
		(new WorldGenHotCoal(YetAnotherMod.hotCoal, 40, YetAnotherMod.depthStone)).generate(world, random, RandPosX, RandPosY, RandPosZ);
	}
	
	private void generateMoon(World world, Random random, int i, int j) {
		/*int RandPosX; int RandPosY; int RandPosZ;
		
		//Moon Camps
		if (random.nextInt(10) == 0) {
			RandPosX = i + random.nextInt(16);
			RandPosY = 56 + random.nextInt(16);
			RandPosZ = j + random.nextInt(16);
			(new GenCamp()).generate(world, random, RandPosX, RandPosY, RandPosZ);
		}*/
	}

}
