package yam.blocks;

import yam.YetAnotherMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;

public class BlockPlayerPlate extends BlockPressurePlate {

	public BlockPlayerPlate() {
		super("diamond_block", Material.iron, Sensitivity.players);
		
		this.setBlockName("diamondPressurePlate");
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setStepSound(Block.soundTypeMetal);
		this.setLightOpacity(0);
		this.setCreativeTab(YetAnotherMod.global);
	}

}
