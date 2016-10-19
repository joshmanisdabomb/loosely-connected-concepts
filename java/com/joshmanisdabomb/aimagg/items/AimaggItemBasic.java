package com.joshmanisdabomb.aimagg.items;

import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;

import net.minecraft.item.Item;

public class AimaggItemBasic extends Item {

	private String internalName;
	
	private final int sortValue;

	public AimaggItemBasic(String internalName, int sortVal) {
		this.setUnlocalizedName(this.internalName = internalName);
		this.setRegistryName(this.internalName);
		this.setCreativeTab(AimlessAgglomeration.tab);
		this.sortValue = sortVal;
		
		AimaggItems.registry.add(this);
	}

	public int getSortValue() {
		return sortValue;
	}
	
}
