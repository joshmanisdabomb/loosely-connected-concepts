package yam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import yam.YetAnotherMod;

public class BlockReactor extends BlockGeneric {

	private Block burnOut;

	public BlockReactor(Block burnOut) {
		super(Material.iron, "nostalgia/reactor/off");
		this.setHardness(5.0F);
		this.setResistance(30.0F);
		this.setLightOpacity(0);
		this.setLightLevel(1.0F);
		this.setStepSound(YetAnotherMod.soundTypeClassicMetal);
		this.setHarvestLevel("pickaxe", 2);
		this.burnOut = burnOut;
	}

}
