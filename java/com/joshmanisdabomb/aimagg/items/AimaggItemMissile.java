package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class AimaggItemMissile extends AimaggItemBasic {

	public static enum MissileType {
		EXPLOSIVE(0,true),
		FIRE(1,true),
		NUCLEAR(2,false);
		
		private int metadata;
		public boolean showStrength;

		MissileType(int metadata, boolean showStrength) {
			this.metadata = metadata;
			this.showStrength = showStrength;
		}
		
		public static MissileType getFromMetadata(int metadata) {
			for (MissileType m : values()) {
				if (metadata == m.metadata) {
					return m;
				}
			}
			return null;
		}

		public int getMetadata() {
			return metadata;
		}

		public boolean showStrength() {
			return showStrength;
		}
	}
	
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

		tooltip.add(this.getUnlocalizedName(stack));
		
        if (MissileType.getFromMetadata(stack.getMetadata()).showStrength()) {
        	tooltip.add(TextFormatting.WHITE + 
        			    "Strength: " + 
        			    TextFormatting.RED +
        			    String.format(stack.getTagCompound().getInteger("strength") + new TextComponentTranslation("tooltip.missile.strength.suffix", new Object[0]).getUnformattedText()));
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
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("strength", 1);
            subItems.add(is);
        }
	}
	
	@Override
	public boolean usesCustomModels() {
		return true;
	}

}
