package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.MissileType;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard.UpgradeCardType;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

public class AimaggItemUpgradeCard extends AimaggItemBasic {

	public AimaggItemUpgradeCard(String internalName, int sortVal) {
		super(internalName, sortVal);
	}
	
	@Override
	public void registerRender() {		
		for (UpgradeCardType uc : UpgradeCardType.values()) {
			ModelLoader.setCustomModelResourceLocation(this, uc.getMetadata(), uc.getItemModel());
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + UpgradeCardType.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		UpgradeCardType uc = UpgradeCardType.getFromMetadata(stack.getMetadata());
		tooltip.add(
				TextFormatting.WHITE + 
			    I18n.format("tooltip.upgrade_card.usein", new Object[] {TextFormatting.LIGHT_PURPLE, I18n.format("tooltip.upgrade_card.usein." + uc.getUsableIn(), new Object[0])})
			   );
		if (uc.hasEffect()) {
			tooltip.add(
						TextFormatting.WHITE + 
					    I18n.format("tooltip.upgrade_card.max", new Object[] {TextFormatting.YELLOW, uc.getStackAmountInUpgradeSlot()})
					   );
			tooltip.add(
						TextFormatting.WHITE + 
					    I18n.format("tooltip.upgrade_card.effect", new Object[] {TextFormatting.GREEN, I18n.format("tooltip.upgrade_card.effect." + stack.getMetadata(), new Object[0])})
					   );
		}
	}
	
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+is.getMetadata();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (UpgradeCardType uc : UpgradeCardType.values()) {
				ItemStack is = new ItemStack(this, 1, uc.getMetadata());
	            items.add(is);
	        }
		}
	}
	
	public static enum UpgradeCardType {
		//Spreader Constructor
		SC_BASE(0,0,64,false,"spreader_interface/base"),
		SC_SPEED(1,0,20,true,"spreader_interface/speed"),      //Base speed is 42 ticks, using 20 cards will reduce tick rate to 2.
		SC_DAMAGE(2,0,20,true,"spreader_interface/damage"),    //Add half a heart to damage.
		SC_RANGE(3,0,64,true,"spreader_interface/range"),      //Base range is 5, Range increase by 5 blocks for every card.
		SC_RANGEINF(4,0,1,true,"spreader_interface/rangeinf"), //Range can be infinite.
		SC_SPREAD(5,0,4,true,"spreader_interface/spread"),     //Base spread is 1 block away, using 4 cards will increase spread to 5 blocks away.
		SC_EATING(6,0,1,true,"spreader_interface/eating");     //With this card, the spreader will remove itself when infecting new blocks.

		/**
		 **  0 is Spreader Constructor
		 **/
		private int usedIn;
		private int metadata;
		private int stackNumberInUpgradeSlot;
		private boolean hasEffect;
		
		private final ModelResourceLocation mrl;

		UpgradeCardType(int metadata, int usedIn, int stackNumberInUpgradeSlot, boolean hasEffect, String mrl) {
			this.metadata = metadata;
			this.usedIn = usedIn;
			this.stackNumberInUpgradeSlot = stackNumberInUpgradeSlot;
			this.hasEffect = hasEffect;
			
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":upgrade_card/" + mrl, "inventory");
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
			return mrl;
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
