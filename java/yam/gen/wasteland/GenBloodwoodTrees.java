package yam.gen.wasteland;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.BlockRainbowGrass;
import yam.entity.EntityRainbowGolem;
import yam.gen.WorldGen;

public class GenBloodwoodTrees {
	
	public boolean generate(World world, Random rand, int x, int y, int z, int height) {
		if (world.isRemote) {return false;}
		if (!(world.getBlock(x,y,z) == YetAnotherMod.crackedMud)) {return false;}
		if (!WorldGen.isX2Y2RegionClear(world, x, y+1, z, x, y+height+1, z)) {return false;}
		
		this.generateActual(world, rand, x, y, z, height);
		return true;
	}
	
	public static void generateActual(World world, Random rand, int x, int y, int z, int height) {
		world.setBlock(x, y, z, YetAnotherMod.crackedMud);
		for (int i = 1; i <= height; i++) {
			world.setBlock(x, y+i, z, YetAnotherMod.bloodwood);
		}
	}
	
}
