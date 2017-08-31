package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.util.MissileType;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemMissile extends AimaggItemBasic {
	
	public AimaggItemMissile(String internalName) {
		super(internalName);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public void registerRender() {
		for (MissileType mt : MissileType.values()) {
			ModelLoader.setCustomModelResourceLocation(this, mt.getMetadata(), mt.getItemModel());
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + MissileType.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		NBTTagCompound mNBT = stack.getSubCompound(Constants.MOD_ID + "_missile");
        if (mNBT != null) {
        	tooltip.add(MissileType.getFromMetadata(stack.getMetadata()).getStrengthUnits().getTooltip(mNBT.getInteger("strength")));
        }
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (MissileType m : MissileType.values()) {
				ItemStack is = new ItemStack(this, 1, m.getMetadata());
				is.getOrCreateSubCompound(Constants.MOD_ID + "_missile").setInteger("strength", 1);
				items.add(is);
	        }
		}
	}

}
