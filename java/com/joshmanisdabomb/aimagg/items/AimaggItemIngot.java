package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.MissileType;
import com.joshmanisdabomb.aimagg.data.OreIngotStorage;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemIngot extends AimaggItemBasic {

	public static int nextid = 0;
	private int id;

	public AimaggItemIngot(String internalName, int sortVal) {
		super(internalName, sortVal);
		this.id = nextid++;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public void registerRender() {
		for (OreIngotStorage ois : OreIngotStorage.getAllWithIngotForm()) {
			ModelLoader.setCustomModelResourceLocation(this, ois.getIngotMetadata(), ois.getIngotModel());
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + OreIngotStorage.getFromID(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+(is.getMetadata()*3);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (OreIngotStorage ois : OreIngotStorage.getAllWithIngotForm()) {
	            items.add(new ItemStack(this, 1, ois.getIngotMetadata()));
	        }
		}
	}

	public int getOISid() {
		return this.id;
	}

}
