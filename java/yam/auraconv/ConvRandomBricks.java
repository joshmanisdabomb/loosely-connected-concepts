package yam.auraconv;

import java.util.Random;

import yam.YetAnotherMod;
import yam.blocks.BlockGeneric;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ConvRandomBricks implements Conv {

	@Override
	public boolean isBlockApplicable(Block b) {
		return b == Blocks.stonebrick || (b instanceof BlockGeneric && b.getUnlocalizedName().toLowerCase().contains("brick"));
	}

	@Override
	public void convertBlockWithAura(int aura, World w, Random r, int x, int y, int z) {
		Block b = r.nextInt(3) == 0 ? YetAnotherMod.bricksWishstone : (r.nextInt(3) == 0 ? YetAnotherMod.bricksHopestone : YetAnotherMod.bricksDreamstone);
		w.setBlock(x, y, z, b);
	}

}
