package yam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCustomLeaves extends BlockGeneric {

	public BlockCustomLeaves(String string, Block sapling, Block log) {
		super(Material.leaves, string + "/fancy");
	}

}
