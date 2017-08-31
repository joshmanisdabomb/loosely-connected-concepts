package com.joshmanisdabomb.aimagg.items;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.Hearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemHeart extends AimaggItemConsumable {

	//TODO Custom sound on consume.
	
	public AimaggItemHeart(String internalName) {
		super(internalName);
		this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public void registerRender() {
		for (HeartType ht : HeartType.values()) {
			ModelLoader.setCustomModelResourceLocation(this, ht.getMetadata(), ht.getModel());
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + HeartType.getFromMetadata(stack.getMetadata()).name().toLowerCase();
    }
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (HeartType ht : HeartType.values()) {
	            items.add(new ItemStack(this, 1, ht.getMetadata()));
	        }
		}
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		ItemStack ret = super.onItemUseFinish(stack, worldIn, entityLiving);
		AimaggCapabilityHearts.sendHeartsPacket(entityLiving);
		return ret;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
        return 15;
    }

	@Override
	public int getFoodAmount(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return 0;
	}

	@Override
	public float getSaturationAmount(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return 0.0F;
	}
	
	@Override
	public void addEffects(World worldIn, ItemStack stack, EntityPlayer entityplayer) {
		HeartType ht = HeartType.getFromMetadata(stack.getMetadata());
		IHearts hearts = entityplayer.getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
		switch (ht) {
			case RED_FULL:
			case RED_HALF:
				entityplayer.setHealth(Math.min(entityplayer.getHealth() + ht.getAmount(), entityplayer.getMaxHealth()));
				break;
			case IRON_FULL:
			case IRON_HALF:
				hearts.addIronHealth(ht.getAmount());
				break;
			case CRYSTAL_FULL:
			case CRYSTAL_HALF:
				hearts.addCrystalHealth(ht.getAmount());
				break;
			case CRYSTAL_EMPTY:
				hearts.addCrystalMaxHealth(ht.getAmount());
				break;
			default:
				break;
		}
	}
	
	@Override
	public boolean canUse(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemStack itemstack) {
		HeartType ht = HeartType.getFromMetadata(itemstack.getMetadata());
		IHearts hearts = playerIn.getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
		switch (ht) {
			case RED_FULL:
			case RED_HALF:
				return playerIn.getHealth() < playerIn.getMaxHealth();
			case IRON_FULL:
			case IRON_HALF:
				return hearts.getIronHealth() < hearts.getIronMaxHealth();
			case CRYSTAL_FULL:
			case CRYSTAL_HALF:
				return hearts.getCrystalHealth() < hearts.getCrystalMaxHealth();
			case CRYSTAL_EMPTY:
				return hearts.getCrystalMaxHealth() < Hearts.MAX_CRYSTAL_HEARTS;
			default:
				return false;
		}
	}
	
	public static enum HeartType {
		
		RED_FULL(),
		RED_HALF(RED_FULL),
		IRON_FULL(),
		IRON_HALF(IRON_FULL),
		CRYSTAL_FULL(),
		CRYSTAL_HALF(CRYSTAL_FULL),
		CRYSTAL_EMPTY();

		private final HeartType halfOf;
		private final ModelResourceLocation mrl;

		HeartType() {
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":heart/" + this.name().toLowerCase());
			this.halfOf = null;
		}
		
		HeartType(HeartType halfOf) {
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":heart/" + this.name().toLowerCase());
			this.halfOf = halfOf;
		}
		
		public int getMetadata() {
			return this.ordinal();
		}

		public static HeartType getFromMetadata(int metadata) {
			return HeartType.values()[metadata];
		}

		public ModelResourceLocation getModel() {
			return mrl;
		}
		
		public float getAmount() {
			return this.halfOf != null ? 1.0F : 2.0F;
		}

		public HeartType getFullHeart() {
			return halfOf != null ? halfOf : this;
		}
	
	}

}
