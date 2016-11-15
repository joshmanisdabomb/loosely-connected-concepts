package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.MissileType;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class AimaggItemUpgradeCard extends AimaggItemBasic {

	public AimaggItemUpgradeCard(String internalName, int sortVal) {
		super(internalName, sortVal);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + UpgradeCardType.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		UpgradeCardType uc = UpgradeCardType.getFromMetadata(stack.getMetadata());
		tooltip.add(
				TextFormatting.WHITE + 
			    I18n.format("tooltip.upgradecard.usein", new Object[] {TextFormatting.LIGHT_PURPLE, I18n.format("tooltip.upgradecard.usein." + uc.getUsableIn(), new Object[0])})
			   );
		if (uc.hasEffect()) {
			tooltip.add(
						TextFormatting.WHITE + 
					    I18n.format("tooltip.upgradecard.max", new Object[] {TextFormatting.YELLOW, uc.getStackAmountInUpgradeSlot()})
					   );
			tooltip.add(
						TextFormatting.WHITE + 
					    I18n.format("tooltip.upgradecard.effect", new Object[] {TextFormatting.GREEN, I18n.format("tooltip.upgradecard.effect." + stack.getMetadata(), new Object[0])})
					   );
		}
	}
	
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+is.getMetadata();
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (UpgradeCardType uc : UpgradeCardType.values()) {
			ItemStack is = new ItemStack(itemIn, 1, uc.getMetadata());
            subItems.add(is);
        }
	}
	
	@Override
	public boolean usesCustomModels() {
		return true;
	}
	
	public static enum UpgradeCardType {
		//Spreader Constructor
		SC_BASE(0,0,64,false,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/base", "inventory")),
		SC_SPEED(1,0,20,true,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/speed", "inventory")),   //Base speed is 42 ticks, using 20 cards will reduce tick rate to 2.
		SC_DAMAGE(2,0,20,true,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/damage", "inventory")),  //Add half a heart to damage.
		SC_RANGE(3,0,64,true,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/range", "inventory")),   //Range increase by 5 blocks for every card.
		SC_RANGEINF(4,0,1,true,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/rangeinf", "inventory")), //Range can be infinite.
		SC_SPREAD(5,0,4,true,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/spread", "inventory")),   //Base spread is 1 block away, using 4 cards will increase spread to 5 blocks away.
		SC_EATING(6,0,1,true,new ModelResourceLocation(Constants.MOD_ID + ":upgradecard/spreaderconstructor/eating", "inventory"));   //With this card, the spreader will remove itself when infecting new blocks.

		/**
		 **  0 is Spreader Constructor
		 **/
		private int usedIn;
		private int metadata;
		private int stackNumberInUpgradeSlot;
		private boolean hasEffect;
		
		private final ModelResourceLocation mrt;

		UpgradeCardType(int metadata, int usedIn, int stackNumberInUpgradeSlot, boolean hasEffect, ModelResourceLocation mrt) {
			this.metadata = metadata;
			this.usedIn = usedIn;
			this.stackNumberInUpgradeSlot = stackNumberInUpgradeSlot;
			this.hasEffect = hasEffect;
			
			this.mrt = mrt;
		}
		
		public int getMetadata() {
			return this.metadata;
		}
		
		public int getUsableIn() {
			return this.usedIn;
		}

		public int getStackAmountInUpgradeSlot() {
			return this.stackNumberInUpgradeSlot;
		}
		
		public boolean hasEffect() {
			return hasEffect;
		}
		
		public ModelResourceLocation getItemModel() {
			return mrt;
		}

		public static UpgradeCardType getFromMetadata(int metadata) {
			for (UpgradeCardType uc : values()) {
				if (metadata == uc.metadata) {
					return uc;
				}
			}
			return null;
		}
	}

}
