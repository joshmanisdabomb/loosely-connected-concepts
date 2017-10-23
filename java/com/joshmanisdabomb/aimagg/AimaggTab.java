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
			setPosition(AimaggBlocks.testBlock, AimaggCategory.TESTING, 0);
			setPosition(AimaggBlocks.testBlock2, AimaggCategory.TESTING, 1);
			setPosition(AimaggBlocks.testBlock3, AimaggCategory.TESTING, 2);
			setPosition(AimaggBlocks.testBlock4, AimaggCategory.TESTING, 3);
			setPosition(AimaggBlocks.testBlock5, AimaggCategory.TESTING, 4);
			setPosition(AimaggItems.testItem, AimaggCategory.TESTING, 7);

			for (AimaggBlockSoft.SoftBlockType sb : AimaggBlockSoft.SoftBlockType.values()) {setPosition(AimaggBlocks.soft, sb.getCategoryOverride(), sb.getUpperSortValue(), sb.getMetadata());}
			
			setPosition(AimaggBlocks.ore, AimaggCategory.OIS, 0);
			setPosition(AimaggItems.ingot, AimaggCategory.OIS, 0);
			setPosition(AimaggBlocks.storage, AimaggCategory.OIS, 0);
			
			setPosition(AimaggBlocks.bouncePad, AimaggCategory.MOVEMENT, 0);
			
			for (AimaggItemMaterial.Material m : AimaggItemMaterial.Material.values()) {setPosition(AimaggItems.materials, m.getCategoryOverride(), m.getUpperSortValue(), m.getMetadata());}

			for (int i = 0; i < AimaggItems.rubyEquipment.getAllItems().length; i++) {
				if (AimaggItems.rubyEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.rubyEquipment.getAllItems()[i], AimaggCategory.EQUIPMENT, i+20);
				}
			}
			for (int i = 0; i < AimaggItems.topazEquipment.getAllItems().length; i++) {
				if (AimaggItems.topazEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.topazEquipment.getAllItems()[i], AimaggCategory.EQUIPMENT, i+40);
				}
			}
			for (int i = 0; i < AimaggItems.emeraldEquipment.getAllItems().length; i++) {
				if (AimaggItems.emeraldEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.emeraldEquipment.getAllItems()[i], AimaggCategory.EQUIPMENT, i+60);
				}
			}
			for (int i = 0; i < AimaggItems.sapphireEquipment.getAllItems().length; i++) {
				if (AimaggItems.sapphireEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.sapphireEquipment.getAllItems()[i], AimaggCategory.EQUIPMENT, i+80);
				}
			}
			for (int i = 0; i < AimaggItems.amethystEquipment.getAllItems().length; i++) {
				if (AimaggItems.amethystEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.amethystEquipment.getAllItems()[i], AimaggCategory.EQUIPMENT, i+100);
				}
			}
			for (int i = 0; i < AimaggItems.neonEquipment.getAllItems().length; i++) {
				if (AimaggItems.neonEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.neonEquipment.getAllItems()[i], AimaggCategory.EQUIPMENT, i+120);
				}
			}
			
			setPosition(AimaggBlocks.spreaderInterface, AimaggCategory.SPREADERS, 0);
			for (int i = 0; i < 16; i++) {setPosition(AimaggBlocks.spreaders[i], AimaggCategory.SPREADERS, i+1);}

			setPosition(AimaggBlocks.launchPad, AimaggCategory.MISSILES, 0);
			setPosition(AimaggItems.missile, AimaggCategory.MISSILES, 1);
			
			setPosition(AimaggItems.pill, AimaggCategory.PILLS, 0);
			
			setPosition(AimaggItems.heart, AimaggCategory.HEARTS, 0);

			setPosition(AimaggBlocks.rainbowGemBlock, AimaggCategory.RAINBOW, 0);
			setPosition(AimaggBlocks.rainbowPad, AimaggCategory.RAINBOW, 1);
			setPosition(AimaggBlocks.rainbowGrass, AimaggCategory.RAINBOW, 2);
			setPosition(AimaggBlocks.rainbowWorld, AimaggCategory.RAINBOW, 3);
			setPosition(AimaggBlocks.candyCane, AimaggCategory.RAINBOW, 4);
			setPosition(AimaggBlocks.candyCaneRefined, AimaggCategory.RAINBOW, 5);
			setPosition(AimaggBlocks.jelly, AimaggCategory.RAINBOW, 6);
			setPosition(AimaggBlocks.chocolate, AimaggCategory.RAINBOW, 10);

			setPosition(AimaggBlocks.wastelandWorld, AimaggCategory.WASTELAND, 0);
			setPosition(AimaggBlocks.fortstone, AimaggCategory.WASTELAND, 1);
			setPosition(AimaggBlocks.spikes, AimaggCategory.WASTELAND, 2);
			
			setPosition(AimaggBlocks.computerCase, AimaggCategory.COMPUTING, 0);
			setPosition(AimaggBlocks.computerCable, AimaggCategory.COMPUTING, 3);
			
			for (int i = 0; i < AimaggItems.classicLeatherEquipment.getAllItems().length; i++) {
				if (AimaggItems.classicLeatherEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.classicLeatherEquipment.getAllItems()[i], AimaggCategory.VERSION, i-40);
				}
			}
			for (int i = 0; i < AimaggItems.classicStuddedEquipment.getAllItems().length; i++) {
				if (AimaggItems.classicStuddedEquipment.getAllItems()[i] != null) {
					setPosition(AimaggItems.classicStuddedEquipment.getAllItems()[i], AimaggCategory.VERSION, i-20);
				}
			}
			setPosition(AimaggBlocks.classicGrass, AimaggCategory.VERSION, 0);
			setPosition(AimaggBlocks.classicSapling, AimaggCategory.VERSION, 1);
			setPosition(AimaggBlocks.classicLeaves, AimaggCategory.VERSION, 2);
			setPosition(AimaggBlocks.classicWorld, AimaggCategory.VERSION, 3);
			setPosition(AimaggBlocks.classicWool, AimaggCategory.VERSION, 4);
			setPosition(AimaggBlocks.desaturatedWool, AimaggCategory.VERSION, 5);
			setPosition(AimaggItems.classicPorkchop, AimaggCategory.VERSION, 6);

			setPosition(AimaggBlocks.scaffolding, AimaggCategory.MISC, 0);
			setPosition(AimaggBlocks.illuminantTile, AimaggCategory.MISC, 1);
			setPosition(AimaggItems.vectorPearl, AimaggCategory.MISC, 2);
			
			setPosition(AimaggItems.upgradeCard, AimaggCategory.UPGRADECARDS, 0);
		}

		private static void setPosition(Object o, AimaggCategory cat, int upperSortVal) {
			setPosition(o, cat, upperSortVal, -1);
		}
		
		private static void setPosition(Object o, AimaggCategory cat, int upperSortVal, int metadata) {
			Item i = null;
			if (o instanceof Block) {
				i = Item.getItemFromBlock((Block)o);
			} else if (o instanceof Item) {
				i = (Item)o;
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
		MOVEMENT,
		OIS,
		MATERIALS,
		EQUIPMENT,
		SPREADERS,
		MISSILES,
		PILLS,
		HEARTS,
		NUCLEAR,
		RAINBOW,
		WASTELAND,
		COMPUTING,
		VERSION, //TODO working name please change
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
