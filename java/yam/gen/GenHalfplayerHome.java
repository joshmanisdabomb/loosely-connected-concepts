package yam.gen;

import java.util.Random;

import yam.NameGenerator;
import yam.YetAnotherMod;
import yam.entity.EntityHalfPlayer;
import yam.gen.house.GenHalfplayerHome1;
import yam.gen.house.GenHalfplayerHome2;
import yam.gen.house.GenHalfplayerHome3;
import yam.gen.house.GenHalfplayerHome4;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenHalfplayerHome {
	
	public GenHalfplayerHome() {}

	public boolean validFoundationBlock(World world, int x, int y, int z) {
		if (world.getBlockMetadata(x,y,z) != 0) {return false;}
		else if (world.getBlock(x,y,z) == Blocks.grass) {return true;}
		else if (world.getBlock(x,y,z) == Blocks.dirt) {return true;}
		else if (world.getBlock(x,y,z) == Blocks.stone) {return true;}
		else if (world.getBlock(x,y,z) == Blocks.sand) {return true;}
		else if (world.getBlock(x,y,z) == Blocks.mycelium) {return true;}
		return false;
	}
	
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if (world.isRemote) {return false;}
		int tier = rand.nextInt(4);
		
		switch(tier) {
			default: {
				//Generate
				if (!WorldGen.isWHDRegionClear(world, x, y+1, z, GenHalfplayerHome1.maxX, GenHalfplayerHome1.maxY, GenHalfplayerHome1.maxZ) || !validFoundationBlock(world,x+GenHalfplayerHome1.maxX/2,y,z+GenHalfplayerHome1.maxZ/2) || !world.canBlockSeeTheSky(x+GenHalfplayerHome1.maxX/2,y+1,z+GenHalfplayerHome1.maxZ/2)) {return false;}
				Block block = world.getBlock(x+GenHalfplayerHome1.maxX/2,y,z+GenHalfplayerHome1.maxZ/2); if (block == Blocks.grass || block == Blocks.dirt) {block = YetAnotherMod.mud;}
				WorldGen.WHDRegionClear(world, x, y, z, GenHalfplayerHome1.maxX, GenHalfplayerHome1.maxY, GenHalfplayerHome1.maxZ); //Safety precautions.
				WorldGen.roundedFlattenGround(world, x-2, y, z-2, GenHalfplayerHome1.maxX+3, GenHalfplayerHome1.maxZ+3, (block == Blocks.sand) ? Blocks.sand : Blocks.dirt, block);
				GenHalfplayerHome1.generate(world, rand, x, y+1, z);
				
				//Spawn Humans
				int size = rand.nextInt(3) + 1;
				String lastname = EntityHalfPlayer.namegen.compose(3);
				for (int i = 0; i < size; i++) {
					EntityHalfPlayer newHuman = new EntityHalfPlayer(world, lastname, 0);
					newHuman.setLocationAndAngles(x+2, y+1, z+2, 0F, 0F);
					
					newHuman.setHome(x,y+1,z,x+GenHalfplayerHome1.maxX,z+GenHalfplayerHome1.maxZ);
					
					world.spawnEntityInWorld(newHuman);
				}
				System.out.println("Generated tier 0 halfplayer village at " + x + "," + y + "," + z + " of size " + size + ".");
				return true;
			}
			case 1: {
				//Generate
				if (!WorldGen.isWHDRegionClear(world, x, y+1, z, GenHalfplayerHome2.maxX, GenHalfplayerHome2.maxY, GenHalfplayerHome2.maxZ) || !validFoundationBlock(world,x+GenHalfplayerHome2.maxX/2,y,z+GenHalfplayerHome2.maxZ/2) || !world.canBlockSeeTheSky(x+GenHalfplayerHome2.maxX/2,y+1,z+GenHalfplayerHome2.maxZ/2)) {return false;}
				Block block = world.getBlock(x+GenHalfplayerHome2.maxX/2,y,z+GenHalfplayerHome2.maxZ/2);
				WorldGen.WHDRegionClear(world, x, y, z, GenHalfplayerHome2.maxX, GenHalfplayerHome2.maxY, GenHalfplayerHome2.maxZ); //Safety precautions.
				WorldGen.roundedFlattenGround(world, x-1, y, z-1, GenHalfplayerHome2.maxX+2, GenHalfplayerHome2.maxZ+2, (block == Blocks.sand) ? Blocks.sand : Blocks.dirt, block);
				GenHalfplayerHome2.generate(world, rand, x, y, z);
				
				//Spawn Humans
				int size = rand.nextInt(5) + 1;
				String lastname = EntityHalfPlayer.namegen.compose(3);
				for (int i = 0; i < size; i++) {
					EntityHalfPlayer newHuman = new EntityHalfPlayer(world, lastname, 1);
					newHuman.setLocationAndAngles(x+(GenHalfplayerHome2.maxX/2), y+2, z+(GenHalfplayerHome2.maxZ/2), 0F, 0F);
					
					newHuman.setHome(x,y+1,z,x+GenHalfplayerHome2.maxX,z+GenHalfplayerHome2.maxZ);
					
					world.spawnEntityInWorld(newHuman);
				}
				System.out.println("Generated tier 1 halfplayer village at " + x + "," + y + "," + z + " of size " + size + ".");
				return true;
			}
			case 2: {
				//Generate
				if (!WorldGen.isWHDRegionClear(world, x, y+1, z, GenHalfplayerHome3.maxX, GenHalfplayerHome3.maxY, GenHalfplayerHome3.maxZ) || !validFoundationBlock(world,x+GenHalfplayerHome3.maxX/2,y,z+GenHalfplayerHome3.maxZ/2) || !world.canBlockSeeTheSky(x+GenHalfplayerHome3.maxX/2,y+1,z+GenHalfplayerHome3.maxZ/2)) {return false;}
				Block block = world.getBlock(x+GenHalfplayerHome3.maxX/2,y,z+GenHalfplayerHome3.maxZ/2);
				WorldGen.WHDRegionClear(world, x, y, z, GenHalfplayerHome3.maxX, GenHalfplayerHome3.maxY, GenHalfplayerHome3.maxZ); //Safety precautions.
				WorldGen.roundedFlattenGround(world, x-1, y, z-1, GenHalfplayerHome3.maxX+2, GenHalfplayerHome3.maxZ+2, (block == Blocks.sand) ? Blocks.sand : Blocks.dirt, block);
				GenHalfplayerHome3.generate(world, rand, x, y, z);
				
				//Spawn Humans
				int size = rand.nextInt(6) + 2;
				String lastname = EntityHalfPlayer.namegen.compose(3);
				for (int i = 0; i < size; i++) {
					EntityHalfPlayer newHuman = new EntityHalfPlayer(world, lastname, 2);
					newHuman.setLocationAndAngles(x+(GenHalfplayerHome3.maxX/2), y+2, z+(GenHalfplayerHome3.maxZ/2), 0F, 0F);
					
					newHuman.setHome(x,y+1,z,x+GenHalfplayerHome3.maxX,z+GenHalfplayerHome3.maxZ);
					
					world.spawnEntityInWorld(newHuman);
				}
				System.out.println("Generated tier 2 halfplayer village at " + x + "," + y + "," + z + " of size " + size + ".");
				return true;
			}
			case 3: {
				//Generate
				if (!WorldGen.isWHDRegionClear(world, x, y+1, z, GenHalfplayerHome4.maxX, GenHalfplayerHome4.maxY, GenHalfplayerHome4.maxZ) || !validFoundationBlock(world,x+GenHalfplayerHome4.maxX/2,y,z+GenHalfplayerHome4.maxZ/2) || !world.canBlockSeeTheSky(x+GenHalfplayerHome4.maxX/2,y+1,z+GenHalfplayerHome4.maxX/2)) {return false;}
				Block block = world.getBlock(x+GenHalfplayerHome4.maxX/2,y,z+GenHalfplayerHome4.maxZ/2);
				WorldGen.WHDRegionClear(world, x, y, z, GenHalfplayerHome4.maxX, GenHalfplayerHome4.maxY, GenHalfplayerHome4.maxZ); //Safety precautions.
				WorldGen.roundedFlattenGround(world, x-1, y, z-1, GenHalfplayerHome4.maxX+2, GenHalfplayerHome4.maxZ+2, (block == Blocks.sand) ? Blocks.sand : Blocks.dirt, block);
				GenHalfplayerHome4.generate(world, rand, x, y+1, z);
				
				//Spawn Humans
				int size = rand.nextInt(10) + 2;
				String lastname = EntityHalfPlayer.namegen.compose(3);
				for (int i = 0; i < size; i++) {
					EntityHalfPlayer newHuman = new EntityHalfPlayer(world, lastname, 3);
					newHuman.setLocationAndAngles(x+(GenHalfplayerHome4.maxX/2), y+3, z+(GenHalfplayerHome4.maxZ/2), 0F, 0F);
					
					newHuman.setHome(x,y+2,z,x+GenHalfplayerHome4.maxX,z+GenHalfplayerHome4.maxZ);
					
					world.spawnEntityInWorld(newHuman);
				}
				System.out.println("Generated tier 3 halfplayer village at " + x + "," + y + "," + z + " of size " + size + ".");
				return true;
			}
		}
	}

}
