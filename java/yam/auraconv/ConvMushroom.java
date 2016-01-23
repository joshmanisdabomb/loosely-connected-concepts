package yam.auraconv;

import java.util.Random;

import yam.YetAnotherMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ConvMushroom implements Conv {
	
	@Override
	public void convertBlockWithAura(int aura, World w, Random r, int x, int y, int z) {
		int data = w.getBlockMetadata(x, y, z);
		if (data == 10 || data == 15) {
			w.setBlock(x, y, z, YetAnotherMod.lightWood);
		} else {
			w.setBlock(x, y, z, YetAnotherMod.lightLeaves);
		}
	}

	@Override
	public boolean isBlockApplicable(Block b) {
		return b == Blocks.red_mushroom_block || b == Blocks.brown_mushroom_block;
	}

}
