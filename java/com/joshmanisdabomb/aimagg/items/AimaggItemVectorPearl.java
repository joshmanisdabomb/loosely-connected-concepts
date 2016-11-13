package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class AimaggItemVectorPearl extends AimaggItemBasic {

	public AimaggItemVectorPearl(String internalName, int sortVal) {
		super(internalName, sortVal);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (worldIn.isRemote) { return new ActionResult(EnumActionResult.SUCCESS, itemStackIn); }
		
		NBTTagCompound vpNBT = itemStackIn.getSubCompound(Constants.MOD_ID + ":vectorpearl", true);
		if (vpNBT.getBoolean("used")) {
			vpNBT.setBoolean("used", false);
			vpNBT.setInteger("xcoord", 0);
			vpNBT.setInteger("ycoord", 0);
			vpNBT.setInteger("zcoord", 0);
			playerIn.addChatMessage(new TextComponentTranslation("item.vectorPearl.cleared", new Object[0]));
			
			return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		} else {
			vpNBT.setBoolean("used", true);
			vpNBT.setInteger("xcoord", (int)playerIn.posX);
			vpNBT.setInteger("ycoord", (int)playerIn.posY);
			vpNBT.setInteger("zcoord", (int)playerIn.posZ);
			playerIn.addChatMessage(new TextComponentTranslation("item.vectorPearl.saved.player", new Object[0]));
			
			return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack itemStackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) { return EnumActionResult.SUCCESS; }

		NBTTagCompound vpNBT = itemStackIn.getSubCompound(Constants.MOD_ID + ":vectorpearl", true);
		if (vpNBT.getBoolean("used")) {
			vpNBT.setBoolean("used", false);
			vpNBT.setInteger("xcoord", 0);
			vpNBT.setInteger("ycoord", 0);
			vpNBT.setInteger("zcoord", 0);
			playerIn.addChatMessage(new TextComponentTranslation("item.vectorPearl.cleared", new Object[0]));
			
			return EnumActionResult.SUCCESS;
		} else {
			vpNBT.setBoolean("used", true);
			vpNBT.setInteger("xcoord", pos.getX());
			vpNBT.setInteger("ycoord", pos.getY());
			vpNBT.setInteger("zcoord", pos.getZ());
			playerIn.addChatMessage(new TextComponentTranslation("item.vectorPearl.saved.block", new Object[0]));
			
			return EnumActionResult.SUCCESS;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		if (stack.hasTagCompound()) {
			NBTTagCompound vpNBT = stack.getSubCompound(Constants.MOD_ID + ":vectorpearl", false);
			
			if (vpNBT != null && vpNBT.getBoolean("used")) {
	        	tooltip.add(
		    				TextFormatting.WHITE + 
		    				new TextComponentTranslation("tooltip.vectorpearl.coordinates", new Object[0]).getUnformattedText() + 
		    			    TextFormatting.YELLOW +
		    			    vpNBT.getInteger("xcoord") + "," + vpNBT.getInteger("ycoord") + "," + vpNBT.getInteger("zcoord")
		    			   );
			} else {
	        	tooltip.add(
		    				TextFormatting.YELLOW + 
		    				new TextComponentTranslation("tooltip.vectorpearl.blank", new Object[0]).getUnformattedText()
		    			   );
			}
		} else {
        	tooltip.add(
	    				TextFormatting.YELLOW + 
	    				new TextComponentTranslation("tooltip.vectorpearl.blank", new Object[0]).getUnformattedText()
	    			   );
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
        	tooltip.add(
	    				TextFormatting.GRAY + 
	    				new TextComponentTranslation("tooltip.vectorpearl.moreinfo", new Object[0]).getUnformattedText()
	    			   );
		} else {
        	tooltip.add(
	    				TextFormatting.DARK_GRAY + 
	    				new TextComponentTranslation("tooltip.pressshiftformore", new Object[0]).getUnformattedText()
	    			   );
		}
	}

}
