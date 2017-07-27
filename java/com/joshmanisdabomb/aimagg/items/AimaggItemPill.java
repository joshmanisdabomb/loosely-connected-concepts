package com.joshmanisdabomb.aimagg.items;

import java.util.List;
import java.util.Random;

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

public class AimaggItemPill extends AimaggItemBasic implements AimaggItemColored {
	
	public static final Random creativePillRand = new Random();
	
	public static final int[] defaultPrimaryColors   = new int[]{0xFFFFFF,0x42B6F4,0xFFD4D4,0xE3FF44,0xFFFFFF,0x342CAA,0xE09100,0xFFE9C1,0xFFFFFF,0x272727,0xFFFFFF,0xFFFFFF};
	public static final int[] defaultSecondaryColors = new int[]{0xFFFFFF,0x42B6F4,0xC90000,0xE09100,0x009DE0,0x0AFF83,0xE09100,0xFFFFFF,0x272727,0xE3FF44,0x00FFFF,0xE3FF44};

	public AimaggItemPill(String internalName, int sortVal) {
		super(internalName, sortVal);
		this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		NBTTagCompound pNBT = stack.getSubCompound(Constants.MOD_ID + "_pill");
		return stack.getMetadata() == 1 ? creativePillRand.nextInt(16777216) : (pNBT == null ? (tintIndex == 0 ? 0x000000 : 0xFF00FF) : (tintIndex == 0 ? pNBT.getInteger("color1") : pNBT.getInteger("color2")));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (int i = 0; i < defaultPrimaryColors.length; i++) {
				ItemStack is = new ItemStack(this, 1);
				NBTTagCompound pNBT = is.getOrCreateSubCompound(Constants.MOD_ID + "_pill");
				pNBT.setInteger("color1", defaultPrimaryColors[i]);
				pNBT.setInteger("color2", defaultSecondaryColors[i]);
				items.add(is);
			}
			ItemStack is = new ItemStack(this, 1, 1);
			items.add(is);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if (stack.getMetadata() == 1) {
			tooltip.add(I18n.format("tooltip.pill.random", new Object[]{}));
		} else {
			NBTTagCompound pNBT = stack.getSubCompound(Constants.MOD_ID + "_pill");
			if (pNBT == null) {tooltip.add(I18n.format("tooltip.pill.invalid", new Object[]{})); return;}
		}
	}

	@Override
	public void registerRender() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "inventory"));
	}

}
