package yam.auraconv;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface Conv {

	public boolean isBlockApplicable(Block b);
	
	public void convertBlockWithAura(int aura, World w, Random r, int x, int y, int z);
	
}