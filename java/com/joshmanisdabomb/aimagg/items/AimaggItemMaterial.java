package com.joshmanisdabomb.aimagg.items;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.OreIngotStorage;
import com.joshmanisdabomb.aimagg.items.AimaggItemHeart.HeartType;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard.UpgradeCardType;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemMaterial extends AimaggItemBasic {

	private static int sortValueConstant;
	
	public AimaggItemMaterial(String string, int i) {
		super(string, i);
		sortValueConstant = i;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public void registerRender() {		
		for (Material m : Material.values()) {
			ModelLoader.setCustomModelResourceLocation(this, m.getMetadata(), m.getModel());
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + Material.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public int getSortValue(ItemStack is) {
		return Material.getFromMetadata(is.getMetadata()).getSortValue();
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (Material m : Material.values()) {
	            items.add(new ItemStack(this, 1, m.getMetadata()));
	        }
		}
	}
	
	public static enum Material {
		
		SPREADER_ESSENCE(null, 1030),
		RAINBOW_CORE(null);
		
		private final ModelResourceLocation model;
		private int sortVal;

		public int getSortValue() {
			return this.sortVal;
		}

		Material(ModelResourceLocation mrl) {
			this.model = mrl != null ? mrl : new ModelResourceLocation(Constants.MOD_ID + ":" + this.name().toLowerCase());
			this.sortVal = sortValueConstant + this.ordinal();
		}

		Material(ModelResourceLocation mrl, int sortVal) {
			this.model = mrl != null ? mrl : new ModelResourceLocation(Constants.MOD_ID + ":" + this.name().toLowerCase());
			this.sortVal = sortVal;
		}
		
		public int getMetadata() {
			return this.ordinal();
		}
		
		public ModelResourceLocation getModel() {
			return this.model;
		}
		
		public static Material getFromMetadata(int metadata) {
			return Material.values()[metadata];
		}
		
	}

}
