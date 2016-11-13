package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.MissileType;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class AimaggItemMissile extends AimaggItemBasic {
	
	public AimaggItemMissile(String internalName, int sortVal) {
		super(internalName, sortVal);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + MissileType.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		NBTTagCompound mNBT = stack.getSubCompound(Constants.MOD_ID + ":missile", false);
        if (MissileType.getFromMetadata(stack.getMetadata()).showStrength() && mNBT != null) {
        	tooltip.add(
        				TextFormatting.WHITE + 
        			    I18n.format("tooltip.missile.strength", new Object[] {TextFormatting.RED, mNBT.getInteger("strength")})
        			   );
        }
	}
	
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+is.getMetadata();
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (MissileType m : MissileType.values()) {
			ItemStack is = new ItemStack(itemIn, 1, m.getMetadata());
			is.getSubCompound(Constants.MOD_ID + ":missile", true).setInteger("strength", 1);
            subItems.add(is);
        }
	}
	
	@Override
	public boolean usesCustomModels() {
		return true;
	}

}
