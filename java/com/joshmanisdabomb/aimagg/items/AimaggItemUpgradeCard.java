package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard.UpgradeCardType;
import com.joshmanisdabomb.aimagg.util.MissileType;

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

	public AimaggItemUpgradeCard(String internalName) {
		super(internalName);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
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
				     I18n.format("tooltip.upgrade_card.use_in", new Object[] {TextFormatting.LIGHT_PURPLE, I18n.format("tooltip.upgrade_card.use_in." + uc.getUsedBy().name().toLowerCase(), new Object[0])})
				   );
		if (uc.hasEffect()) {
			tooltip.add(
						 TextFormatting.WHITE + 
					     I18n.format("tooltip.upgrade_card.max", new Object[] {TextFormatting.YELLOW, uc.getStackAmountInUpgradeSlot()})
					   );
			tooltip.add(
					 	TextFormatting.WHITE + 
					     I18n.format("tooltip.upgrade_card.effect", new Object[] {TextFormatting.GREEN, I18n.format("tooltip.upgrade_card.effect." + uc.getInternalName(), new Object[0])})
					   );
		}
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
		//Spreader Interface
		SI_BASE(UsedBy.SPREADER_INTERFACE,64,false,"base"),
		SI_SPEED(UsedBy.SPREADER_INTERFACE,20,true,"speed"),      //Base speed is 42 ticks, using 20 cards will reduce tick rate to 2.
		SI_DAMAGE(UsedBy.SPREADER_INTERFACE,20,true,"damage"),    //Add half a heart to damage.
		SI_RANGE(UsedBy.SPREADER_INTERFACE,64,true,"range"),      //Base range is 5, Range increase by 5 blocks for every card.
		SI_INFINITE_RANGE(UsedBy.SPREADER_INTERFACE,1,true,"infinite_range"), //Range can be infinite.
		SI_SPREAD(UsedBy.SPREADER_INTERFACE,4,true,"spread"),     //Base spread is 1 block away, using 4 cards will increase spread to 5 blocks away.
		SI_EATING(UsedBy.SPREADER_INTERFACE,1,true,"eating");     //With this card, the spreader will remove itself when infecting new blocks.

		/**
		 **  0 is Spreader Constructor
		 **/
		private final UsedBy usedBy;
		private final int stackNumberInUpgradeSlot;
		private final boolean hasEffect;

		private final String internalName;
		private final ModelResourceLocation mrl;

		UpgradeCardType(UsedBy usedBy, int stackNumberInUpgradeSlot, boolean hasEffect, String internalName) {
			this.usedBy = usedBy;
			this.stackNumberInUpgradeSlot = stackNumberInUpgradeSlot;
			this.hasEffect = hasEffect;
			
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":upgrade_card/" + usedBy.subfolder + "/" + internalName, "inventory");
			this.internalName = internalName;
		}
		
		public String getInternalName() {
			return this.internalName;
		}
		
		public int getMetadata() {
			return this.ordinal();
		}
		
		public UsedBy getUsedBy() {
			return this.usedBy;
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
			return UpgradeCardType.values()[metadata];
		}
		
		public static enum UsedBy {
			
			SPREADER_INTERFACE("spreader_interface");
			
			private final String subfolder;
			
			UsedBy(String subfolder) {
				this.subfolder = subfolder;
			}
			
		}
	}

}
