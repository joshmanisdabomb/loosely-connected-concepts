package yam.blocks;

import net.minecraft.block.material.Material;
import yam.YetAnotherMod;

public class BlockGlowingObsidian extends BlockGeneric {

	public BlockGlowingObsidian() {
		super(Material.rock, "nostalgia/obsidian/glowing");
		this.setHardness(50.0F);
		this.setResistance(6000.0F);
		this.setLightOpacity(0);
		this.setLightLevel(0.8F);
		this.setStepSound(YetAnotherMod.soundTypeClassicStone);
		this.setHarvestLevel("pickaxe", 3);
	}

}
