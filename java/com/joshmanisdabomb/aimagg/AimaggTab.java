package com.joshmanisdabomb.aimagg;

import java.util.Comparator;
import java.util.List;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasic;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AimaggTab extends CreativeTabs {

	private Item itemIcon;
	
	public final AimaggTabComparator compar;

	public AimaggTab(String label) {
		super(label);
		compar = new AimaggTabComparator();
	}

	public void setItemIcon(Item icon) {
		this.itemIcon = icon;
	}

	public void setItemIcon(Block icon) {
		this.itemIcon = Item.getItemFromBlock(icon);
	}
	
	@Override
	public Item getTabIconItem() {
		return itemIcon;
	}
	
	@Override
	public void displayAllRelevantItems(List<ItemStack> l) {
		super.displayAllRelevantItems(l);
		l.sort(compar);
	}

	public class AimaggTabComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack is1, ItemStack is2) {
			int sortValue1;
			int sortValue2;
			if (is1.getItem() instanceof AimaggItemBasic) {
				sortValue1 = ((AimaggItemBasic)(is1.getItem())).getSortValue();
			} else if (Block.getBlockFromItem(is1.getItem()) instanceof AimaggBlockBasic) {
				sortValue1 = ((AimaggBlockBasic)(Block.getBlockFromItem(is1.getItem()))).getSortValue();
			} else {
				return 0;
			}
			if (is2.getItem() instanceof AimaggItemBasic) {
				sortValue2 = ((AimaggItemBasic)(is2.getItem())).getSortValue();
			} else if (Block.getBlockFromItem(is2.getItem()) instanceof AimaggBlockBasic) {
				sortValue2 = ((AimaggBlockBasic)(Block.getBlockFromItem(is2.getItem()))).getSortValue();
			} else {
				return 0;
			}
			return sortValue1 - sortValue2;
		}

	}

}
