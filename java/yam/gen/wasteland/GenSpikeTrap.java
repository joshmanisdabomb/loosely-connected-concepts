package yam.gen.wasteland;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.gen.WorldGen;

public class GenSpikeTrap {
	
	public GenSpikeTrap() {}
	
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if (world.isRemote) {return false;}
		Block spikes = (rand.nextInt(3) == 0 ? YetAnotherMod.spikes : (rand.nextInt(2) == 0 ? YetAnotherMod.bloodSpikes : YetAnotherMod.poisonSpikes));
		int size = 2 + rand.nextInt(2);
		int smallDepth = 2 + rand.nextInt(4);
		
		for (int i = -1; i < size+1; i++) {
			for (int k = -1; k < size+1; k++) {
				if (world.getBlock(x+i,y,z+k) != YetAnotherMod.crackedMud && world.getBlock(x+i,y,z+k) != YetAnotherMod.reinforcedStone) {return false;}
				if (!world.isAirBlock(x+i,y+1,z+k)) {return false;}
			}
		}
		
		for (int i = -1; i < size+1; i++) {
			for (int k =  -1; k < size+1; k++) {
				for (int j = 1; j < smallDepth+1; j++) {
					world.setBlock(x+i, y-j, z+k, YetAnotherMod.reinforcedStone);
				}
			}
		}
		
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size; k++) {
				for (int j = 0; j < smallDepth; j++) {
					world.setBlock(x+i, y-j, z+k, Blocks.air);
				}
				world.setBlock(x+i, y-smallDepth, z+k, spikes);
				world.setBlock(x+i, y-smallDepth-1, z+k, YetAnotherMod.reinforcedStone);
			}
		}
		
		return true;
	}
	
}
