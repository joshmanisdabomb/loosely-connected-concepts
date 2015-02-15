package yam.gen.moon;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.BlockRainbowGrass;
import yam.gen.WorldGen;

public class GenCamp {
	
	public static final int maxX = 7;
	public static final int maxY = 4;
	public static final int maxZ = 7;
	public static final int maxX2 = 7;
	public static final int maxY2 = 4;
	public static final int maxZ2 = 7;
	public static final int difference = 0;
	
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if (world.isRemote) {return false;}
		
		//Generate
		if (!WorldGen.isWHDRegionClear(world, x, y+1, z, maxX2, maxY2, maxZ2) || world.getBlock(x+maxX/2,y,z+maxZ/2) != YetAnotherMod.moonRock || !world.canBlockSeeTheSky(x+maxX/2,y+1,z+maxX/2)) {return false;}
		WorldGen.WHDRegionClear(world, x, y, z, maxX2, maxY2, maxZ2); //Safety precautions.
		WorldGen.roundedFlattenGround(world, x, y, z, maxX2, maxZ2, YetAnotherMod.moonRock, YetAnotherMod.moonRock);
		generateActual(world, rand, x+(difference/2), y+1, z+(difference/2));
		
		return true;
	}
	
	public static void generateActual(World world, Random rand, int x, int y, int z) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; i++) {
				world.setBlock((x + 4) - i, y + i, z + j, YetAnotherMod.reinforcedWool, 0, 3);
				world.setBlock((x + 4) + i, y + i, z + j, YetAnotherMod.reinforcedWool, 0, 3);
			}
		}
	}

}
