package com.joshmanisdabomb.aimagg.items;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimaggTab.AimaggCategory;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockAdvancedRendering;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockColored;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemBasic extends Item {

	private final String internalName;

	public AimaggItemBasic(String internalName) {
		this.setUnlocalizedName(Constants.MOD_ID + ":" + (this.internalName = internalName));
		this.setRegistryName(this.getInternalName());
		this.setCreativeTab(AimlessAgglomeration.tab);
		
		AimaggItems.registry.add(this);
		
		this.init();
	}

	public String getInternalName() {
		return internalName;
	}
	
	public AimaggCategory getCategoryOverride(ItemStack is) {
		return null;
	}

	public int getLowerSortValue(ItemStack is) {
		return is.getMetadata();
	}
	
	public final void init() {
		if (this instanceof AimaggItemColored) {
			AimaggItems.colorRegistry.add(this);
		}
		this.initialise();
	}
	
	public void initialise() {
		
	}

	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "inventory"));
	}
	
}
