package yam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockComputer extends BlockGeneric {

	public BlockComputer(Material material, String texture) {
		super(material, texture + "/front", texture + "/side", texture + "/top", texture + "/bottom");
		this.setHardness(7.5f);
		this.setResistance(13.0f);
		this.setStepSound(Block.soundTypeMetal);
		this.setHarvestLevel("pickaxe", 2);
	}

}
