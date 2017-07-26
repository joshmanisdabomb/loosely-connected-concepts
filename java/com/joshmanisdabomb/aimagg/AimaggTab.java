package com.joshmanisdabomb.aimagg;

import java.util.Comparator;
import java.util.List;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class AimaggTab extends CreativeTabs {

	private ItemStack itemIcon;
	
	public final AimaggTabComparator compar;

	public AimaggTab(String label) {
		super(label);
		compar = new AimaggTabComparator();
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

	public class AimaggTabComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack is1, ItemStack is2) {
			int sortValue1;
			int sortValue2;
			if (is1.getItem() instanceof AimaggItemBasic) {
				sortValue1 = ((AimaggItemBasic)(is1.getItem())).getSortValue(is1);
			} else if (Block.getBlockFromItem(is1.getItem()) instanceof AimaggBlockBasic) {
				sortValue1 = ((AimaggBlockBasic)(Block.getBlockFromItem(is1.getItem()))).getSortValue(is1);
			} else {
				return 0;
			}
			if (is2.getItem() instanceof AimaggItemBasic) {
				sortValue2 = ((AimaggItemBasic)(is2.getItem())).getSortValue(is2);
			} else if (Block.getBlockFromItem(is2.getItem()) instanceof AimaggBlockBasic) {
				sortValue2 = ((AimaggBlockBasic)(Block.getBlockFromItem(is2.getItem()))).getSortValue(is2);
			} else {
				return 0;
			}
			return sortValue1 - sortValue2;
		}

	}

}
