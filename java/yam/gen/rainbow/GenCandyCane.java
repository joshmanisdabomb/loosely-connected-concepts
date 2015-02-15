package yam.gen.rainbow;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.BlockRainbowGrass;
import yam.entity.EntityRainbowGolem;
import yam.gen.WorldGen;

public class GenCandyCane {
	
	public boolean generate(World world, Random rand, int x, int y, int z, int height) {
		if (world.isRemote) {return false;}
		if (!(world.getBlock(x,y,z) instanceof BlockRainbowGrass)) {return false;}
		if (!WorldGen.isX2Y2RegionClear(world, x, y+1, z, x, y+height+1, z)) {return false;}
		
		this.generateActual(world, rand, x, y, z, height);
		return true;
	}
	
	public static void generateActual(World world, Random rand, int x, int y, int z, int height) {
		Block candyCane = (rand.nextInt(3) == 0) ? YetAnotherMod.candyCaneRedBlock : (rand.nextInt(2) == 0) ? YetAnotherMod.candyCaneGreenBlock : YetAnotherMod.candyCaneBlueBlock;
		
		world.setBlock(x, y, z, YetAnotherMod.rainbowDirt);
		for (int i = 1; i <= height; i++) {
			world.setBlock(x, y+i, z, candyCane);
		}
	}
	
}
