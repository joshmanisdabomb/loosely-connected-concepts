package com.joshmanisdabomb.aimagg.items;

import com.joshmanisdabomb.aimagg.AimaggTab.AimaggCategory;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemMaterial extends AimaggItemBasic {
	
	public AimaggItemMaterial(String string) {
		super(string);
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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (Material m : Material.values()) {
	            items.add(new ItemStack(this, 1, m.getMetadata()));
	        }
		}
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack is) {
        if (this.hasContainerItem(is)) {
        	return new ItemStack(is.getItem(), 1, is.getMetadata());
        } else {
        	return ItemStack.EMPTY;
        }
    }

	@Override
    public boolean hasContainerItem(ItemStack stack) {
        return Material.getFromMetadata(stack.getMetadata()).hasContainerItem(stack);
    }
	
	public static enum Material {
		
		SPREADER_ESSENCE(true, AimaggCategory.SPREADERS, -100),
		RAINBOW_CORE(false, AimaggCategory.RAINBOW, -100),
		NEON_PIXEL(false, AimaggCategory.RAINBOW, -99), //TODO should probably be in a computer category
		LOLLIPOP_STICK(false, AimaggCategory.RAINBOW, -98),
		SPRING(false, AimaggCategory.MOVEMENT, -100);
		
		private final ModelResourceLocation model;
		private final AimaggCategory category;
		private final int upperSortVal;
		
		private final boolean infiniteCrafting;

		Material(boolean infiniteCrafting, AimaggCategory cat, int upperSortVal) {
			this.infiniteCrafting = infiniteCrafting;
			this.model = new ModelResourceLocation(Constants.MOD_ID + ":" + this.name().toLowerCase());
			this.category = cat;
			this.upperSortVal = upperSortVal;
		}
		
		public boolean hasContainerItem(ItemStack stack) {
			return this.infiniteCrafting;
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

		public AimaggCategory getCategoryOverride() {
			return this.category;
		}

		public int getUpperSortValue() {
			return this.upperSortVal;
		}
		
	}

}
