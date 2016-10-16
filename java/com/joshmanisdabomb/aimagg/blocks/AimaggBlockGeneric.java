package com.joshmanisdabomb.aimagg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class AimaggBlockGeneric extends Block {

	private String internalName;
	private String newUnlocalizedName;
	
	public AimaggBlockGeneric(String internalName, Material material, MapColor mcolor) {
		super(material, mcolor);
		this.setUnlocalizedName(this.internalName = internalName);
		this.setRegistryName(this.internalName);
	}

}
