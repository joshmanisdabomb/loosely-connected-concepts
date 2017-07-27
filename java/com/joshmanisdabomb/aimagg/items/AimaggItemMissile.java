package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.MissileType;

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
	
	public AimaggItemMissile(String internalName, int sortVal) {
		super(internalName, sortVal);
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
        	if (MissileType.getFromMetadata(stack.getMetadata()).usingKilotons()) {
	        	tooltip.add(
	        				TextFormatting.WHITE + 
	        			    I18n.format("tooltip.missile.strength", new Object[] {TextFormatting.GREEN, mNBT.getInteger("strength")/20F, I18n.format("tooltip.missile.strength.kilotons", new Object[0])})
	        			   );
	        } else {
		        	tooltip.add(
		    				TextFormatting.WHITE + 
		    			    I18n.format("tooltip.missile.strength", new Object[] {TextFormatting.RED, mNBT.getInteger("strength"), I18n.format("tooltip.missile.strength.tnt", new Object[0])})
		    			   );
	        }
        }
	}
	
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+is.getMetadata();
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
