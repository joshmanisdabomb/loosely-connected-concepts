package com.joshmanisdabomb.aimagg.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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

	public AimaggItemVectorPearl(String internalName) {
		super(internalName);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) { return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand)); }
		
		NBTTagCompound vpNBT = player.getHeldItem(hand).getOrCreateSubCompound(Constants.MOD_ID + "_vector_pearl");
		if (vpNBT.getBoolean("used")) {
			vpNBT.setBoolean("used", false);
			vpNBT.setInteger("xcoord", 0);
			vpNBT.setInteger("ycoord", 0);
			vpNBT.setInteger("zcoord", 0);
			player.sendMessage(new TextComponentTranslation("item.aimagg:vector_pearl.cleared", new Object[0]));
			
			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		} else {
			vpNBT.setBoolean("used", true);
			vpNBT.setInteger("xcoord", (int)player.posX);
			vpNBT.setInteger("ycoord", (int)player.posY);
			vpNBT.setInteger("zcoord", (int)player.posZ);
			player.sendMessage(new TextComponentTranslation("item.aimagg:vector_pearl.saved.player", new Object[0]));
			
			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) { return EnumActionResult.SUCCESS; }

		NBTTagCompound vpNBT = player.getHeldItem(hand).getOrCreateSubCompound(Constants.MOD_ID + "_vector_pearl");
		if (vpNBT.getBoolean("used")) {
			vpNBT.setBoolean("used", false);
			vpNBT.setInteger("xcoord", 0);
			vpNBT.setInteger("ycoord", 0);
			vpNBT.setInteger("zcoord", 0);
			player.sendMessage(new TextComponentTranslation("item.aimagg:vector_pearl.cleared", new Object[0]));
			
			return EnumActionResult.SUCCESS;
		} else {
			vpNBT.setBoolean("used", true);
			vpNBT.setInteger("xcoord", pos.getX());
			vpNBT.setInteger("ycoord", pos.getY());
			vpNBT.setInteger("zcoord", pos.getZ());
			player.sendMessage(new TextComponentTranslation("item.aimagg:vector_pearl.saved.block", new Object[0]));
			
			return EnumActionResult.SUCCESS;
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if (stack.hasTagCompound()) {
			NBTTagCompound vpNBT = stack.getSubCompound(Constants.MOD_ID + "_vector_pearl");
			
			if (vpNBT != null && vpNBT.getBoolean("used")) {
	        	tooltip.add(
		    				TextFormatting.WHITE + 
		    				I18n.format("tooltip.aimagg:vector_pearl.coordinates", new Object[] {TextFormatting.YELLOW, vpNBT.getInteger("xcoord") + "," + vpNBT.getInteger("ycoord") + "," + vpNBT.getInteger("zcoord")})
		    			   );
			} else {
	        	tooltip.add(
		    				TextFormatting.DARK_AQUA + 
		    				I18n.format("tooltip.aimagg:vector_pearl.blank", new Object[0])
		    			   );
			}
		} else {
        	tooltip.add(
	    				TextFormatting.DARK_AQUA + 
	    				I18n.format("tooltip.aimagg:vector_pearl.blank", new Object[0])
	    			   );
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
        	tooltip.add(
	    				TextFormatting.GRAY + 
	    				I18n.format("tooltip.aimagg:vector_pearl.more_info", new Object[] {"\n", "\n"})
	    			   );
		} else {
        	tooltip.add(
	    				TextFormatting.DARK_GRAY + 
	    				I18n.format("tooltip.aimagg:press_shift_for_more", new Object[0])
	    			   );
		}
	}

}
