package yam.blocks;

import net.minecraft.block.material.Material;

public class BlockInternet extends BlockGeneric {

	private float credit;

	public BlockInternet(Material material, String texture, float credit) {
		super(material, "internet/" + texture);
		this.credit = credit;
	}

}
