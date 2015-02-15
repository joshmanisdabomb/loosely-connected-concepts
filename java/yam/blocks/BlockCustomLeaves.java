package yam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCustomLeaves extends BlockGeneric {

	public BlockCustomLeaves(String string, Block nostalgiaSapling) {
		super(Material.leaves, string + "/fancy");
	}

}
