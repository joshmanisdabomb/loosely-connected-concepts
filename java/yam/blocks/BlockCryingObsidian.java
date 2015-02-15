package yam.blocks;

import yam.YetAnotherMod;
import net.minecraft.block.material.Material;

public class BlockCryingObsidian extends BlockGeneric {

	public BlockCryingObsidian() {
		super(Material.rock, "nostalgia/obsidian/crying");
		this.setHardness(50.0F);
		this.setResistance(6000.0F);
		this.setStepSound(YetAnotherMod.soundTypeClassicStone);
		this.setHarvestLevel("pickaxe", 3);
	}

}
