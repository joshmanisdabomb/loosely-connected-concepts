package yam.auraconv;

import java.util.Random;

import yam.YetAnotherMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ConvCrackedMud implements Conv {
	
	@Override
	public void convertBlockWithAura(int aura, World w, Random r, int x, int y, int z) {
		if (w.canBlockSeeTheSky(x, y+1, z)) {
			w.setBlock(x, y, z, YetAnotherMod.lightGrass);
		} else {
			w.setBlock(x, y, z, YetAnotherMod.lightDirt);
		}
	}

	@Override
	public boolean isBlockApplicable(Block b) {
		return b == YetAnotherMod.crackedMud;
	}

}
