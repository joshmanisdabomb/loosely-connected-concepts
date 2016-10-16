package com.joshmanisdabomb.aimagg.items;

import net.minecraft.item.Item;

public class AimaggItemGeneric extends Item {

	private String internalName;
	private String newUnlocalizedName;

	public AimaggItemGeneric(String internalName) {
		this.setUnlocalizedName(this.internalName = internalName);
		this.setRegistryName(this.internalName);
	}
	
}
