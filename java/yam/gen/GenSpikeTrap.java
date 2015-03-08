package yam.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class GenSpikeTrap {
	
	public GenSpikeTrap() {}
	
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if (world.isRemote) {return false;}
		Block spikes = (rand.nextInt(2) == 0 ? YetAnotherMod.spikes : YetAnotherMod.bloodSpikes);
		int size = 2 + rand.nextInt(2);
		int smallDepth = 2 + rand.nextInt(4);
		Block foundation = world.getBlock(x,y-smallDepth-1,z);
		
		if (WorldGen.isWHDRegionClear(world, x, y, z, x+size, y, z+size)) {return false;}
		if (foundation != YetAnotherMod.crackedMud && foundation != YetAnotherMod.reinforcedStone && foundation != Blocks.stone) {return false;}
		
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size; k++) {
				for (int j = 0; j < smallDepth; j++) {
					world.setBlock(x+i, y-j, z+k, Blocks.air);
				}
				world.setBlock(x+i, y-smallDepth, z+k, spikes);
				world.setBlock(x+i, y-smallDepth-1, z+k, foundation);
			}
		}
		
		return true;
	}
	
}
