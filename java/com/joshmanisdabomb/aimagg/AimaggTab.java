package com.joshmanisdabomb.aimagg;

import java.util.Comparator;
import java.util.HashMap;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBillieTiles.BillieTileType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSoft;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemMaterial;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AimaggTab extends CreativeTabs {

	private ItemStack itemIcon;
	
	public final AimaggTabSorting compar;

	public AimaggTab(String label) {
		super(label);
		compar = new AimaggTabSorting();
	}

	public void setStackIcon(ItemStack icon) {
		this.itemIcon = icon;
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return itemIcon;
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> l) {
		super.displayAllRelevantItems(l);
		l.sort(compar);
	}

	public static class AimaggTabSorting implements Comparator<ItemStack> {
		
		public static void sortItems() {
			setUpperSortValue(AimaggBlocks.testBlock, AimaggCategory.TESTING, 0);
			setUpperSortValue(AimaggBlocks.testBlock2, AimaggCategory.TESTING, 1);
			setUpperSortValue(AimaggBlocks.testBlock3, AimaggCategory.TESTING, 2);
			setUpperSortValue(AimaggBlocks.testBlock4, AimaggCategory.TESTING, 3);
			setUpperSortValue(AimaggBlocks.testBlock5, AimaggCategory.TESTING, 4);
			setUpperSortValue(AimaggItems.testItem, AimaggCategory.TESTING, 7);

			for (AimaggBlockSoft.SoftBlockType sb : AimaggBlockSoft.SoftBlockType.values()) {setUpperSortValue(AimaggBlocks.soft, sb.getCategoryOverride(), sb.getUpperSortValue(), sb.getMetadata());}
			
			setUpperSortValue(AimaggBlocks.ore, AimaggCategory.OIS, 0);
			setUpperSortValue(AimaggItems.ingot, AimaggCategory.OIS, 0);
			setUpperSortValue(AimaggBlocks.storage, AimaggCategory.OIS, 0);
			
			for (AimaggItemMaterial.Material m : AimaggItemMaterial.Material.values()) {setUpperSortValue(AimaggItems.materials, m.getCategoryOverride(), m.getUpperSortValue(), m.getMetadata());}
			
			setUpperSortValue(AimaggBlocks.spreaderInterface, AimaggCategory.SPREADERS, 0);
			for (int i = 0; i < 16; i++) {setUpperSortValue(AimaggBlocks.spreaders[i], AimaggCategory.SPREADERS, i+1);}

			setUpperSortValue(AimaggBlocks.launchPad, AimaggCategory.MISSILES, 0);
			setUpperSortValue(AimaggItems.missile, AimaggCategory.MISSILES, 1);
			
			setUpperSortValue(AimaggItems.pill, AimaggCategory.PILLS, 0);
			
			setUpperSortValue(AimaggItems.heart, AimaggCategory.HEARTS, 0);

			setUpperSortValue(AimaggBlocks.rainbowGemBlock, AimaggCategory.RAINBOW, 0);
			setUpperSortValue(AimaggBlocks.rainbowPad, AimaggCategory.RAINBOW, 1);
			setUpperSortValue(AimaggBlocks.rainbowGrass, AimaggCategory.RAINBOW, 2);
			setUpperSortValue(AimaggBlocks.rainbowWorld, AimaggCategory.RAINBOW, 3);
			setUpperSortValue(AimaggBlocks.candyCane, AimaggCategory.RAINBOW, 4);
			setUpperSortValue(AimaggBlocks.candyCaneRefined, AimaggCategory.RAINBOW, 5);
			setUpperSortValue(AimaggBlocks.jelly, AimaggCategory.RAINBOW, 6);
			setUpperSortValue(AimaggBlocks.chocolate, AimaggCategory.RAINBOW, 10);

			setUpperSortValue(AimaggBlocks.fortstone, AimaggCategory.WASTELAND, 1);
			setUpperSortValue(AimaggBlocks.spikes, AimaggCategory.WASTELAND, 1);

			setUpperSortValue(AimaggBlocks.scaffolding, AimaggCategory.MISC, 0);
			setUpperSortValue(AimaggBlocks.illuminantTile, AimaggCategory.MISC, 1);
			setUpperSortValue(AimaggItems.vectorPearl, AimaggCategory.MISC, 2);
			
			setUpperSortValue(AimaggItems.upgradeCard, AimaggCategory.UPGRADECARDS, 0);
		}

		private static void setUpperSortValue(Object o, AimaggCategory cat, int upperSortVal) {
			setUpperSortValue(o, cat, upperSortVal, -1);
		}
		
		private static void setUpperSortValue(Object o, AimaggCategory cat, int upperSortVal, int metadata) {
			Item i = null;
			if (o instanceof AimaggBlockBasic) {
				i = Item.getItemFromBlock((AimaggBlockBasic)o);
			} else if (o instanceof AimaggItemBasic) {
				i = (AimaggItemBasic)o;
			}
			cat.upperSortValueList.put(Item.REGISTRY.getNameForObject(i) + "|" + metadata, upperSortVal);
		}

		@Override
		public int compare(ItemStack is1, ItemStack is2) {
			AimaggCategory category1 = AimaggCategory.getCategoryFromList(Item.REGISTRY.getNameForObject(is1.getItem()).toString(), is1.getMetadata());
			AimaggCategory category2 = AimaggCategory.getCategoryFromList(Item.REGISTRY.getNameForObject(is2.getItem()).toString(), is2.getMetadata());
			if (category1.ordinal() != category2.ordinal()) {
				return category1.ordinal() - category2.ordinal();
			}
			
			int upperSortValue1 = AimaggCategory.getUpperSortValueFromList(Item.REGISTRY.getNameForObject(is1.getItem()).toString(), is1.getMetadata());
			int upperSortValue2 = AimaggCategory.getUpperSortValueFromList(Item.REGISTRY.getNameForObject(is2.getItem()).toString(), is2.getMetadata());
			if (upperSortValue1 != upperSortValue2) {
				return upperSortValue1 - upperSortValue2;
			}
			
			int lowerSortValue1; int lowerSortValue2;
			if (is1.getItem() instanceof AimaggItemBasic) {
				lowerSortValue1 = ((AimaggItemBasic)is1.getItem()).getLowerSortValue(is1);
			} else if (Block.getBlockFromItem(is1.getItem()) instanceof AimaggBlockBasic) {
				lowerSortValue1 = ((AimaggBlockBasic)Block.getBlockFromItem(is1.getItem())).getLowerSortValue(is1);
			} else {
				lowerSortValue1 = -1;
			}
			if (is2.getItem() instanceof AimaggItemBasic) {
				lowerSortValue2 = ((AimaggItemBasic)is2.getItem()).getLowerSortValue(is2);
			} else if (Block.getBlockFromItem(is2.getItem()) instanceof AimaggBlockBasic) {
				lowerSortValue2 = ((AimaggBlockBasic)Block.getBlockFromItem(is2.getItem())).getLowerSortValue(is2);
			} else {
				lowerSortValue2 = -1;
			}
			return lowerSortValue1 - lowerSortValue2;
		}

	}
	
	public static enum AimaggCategory {
		
		SOFT,
		OIS,
		MATERIALS,
		SPREADERS,
		MISSILES,
		PILLS,
		HEARTS,
		NUCLEAR,
		RAINBOW,
		WASTELAND,
		MISC,
		UPGRADECARDS,
		TESTING;
		
		private static final int sortSeparation = 10000;
		
		protected HashMap<String, Integer> upperSortValueList = new HashMap<String, Integer>();

		public static AimaggCategory getCategoryFromList(String itemId, int metadata) {
			for (AimaggCategory cat : AimaggCategory.values()) {
				if (cat.upperSortValueList.containsKey(itemId + "|" + -1) || cat.upperSortValueList.containsKey(itemId + "|" + metadata)) {
					return cat;
				}
			}
            throw new IllegalArgumentException("Something in the Aimless Agglomeration tab doesn't have a category.");
		}

		public static int getUpperSortValueFromList(String itemId, int metadata) {
			for (AimaggCategory cat : AimaggCategory.values()) {
				if (cat.upperSortValueList.containsKey(itemId + "|" + -1)) {
					return cat.upperSortValueList.get(itemId + "|" + -1);
				} else if (cat.upperSortValueList.containsKey(itemId + "|" + metadata)) {
					return cat.upperSortValueList.get(itemId + "|" + metadata);
				}
			}
            throw new IllegalArgumentException("Something in the Aimless Agglomeration tab doesn't have an upper sort value.");
		}

	}

}
