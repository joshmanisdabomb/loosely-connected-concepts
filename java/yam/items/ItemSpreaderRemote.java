package yam.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.blocks.BlockGeneric;
import yam.blocks.BlockSpreading;

public class ItemSpreaderRemote extends ItemGeneric {

	private int power;

	public ItemSpreaderRemote(String texture, int power, int durability) {
		super("remote/spreader/" + texture);
		this.power = power;
		
		this.setMaxDamage(durability);
		this.setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote) {
			par1ItemStack.damageItem(1, par3EntityPlayer);
			int x = (int)Math.round(par3EntityPlayer.posX);
			int y = (int)Math.round(par3EntityPlayer.posY);
			int z = (int)Math.round(par3EntityPlayer.posZ);
			
			for (int i = -power/2; i <= power/2; i++) {
	        	for (int j = -power/2; j <= power/2; j++) {
	        		for (int k = -power/2; k <= power/2; k++) {
	        			Block block = par2World.getBlock(x + i, y + j, z + k);
	        	        if (block instanceof BlockSpreading) {
	        	        	par2World.setBlock(x + i, y + j, z + k, YetAnotherMod.antispreader);
	        	        }
	                }
	            }
	        }
		}
		par2World.playSoundAtEntity(par3EntityPlayer, YetAnotherMod.MODID + ":items.remote", 1, 1);
		return par1ItemStack;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Targets: " + EnumChatFormatting.LIGHT_PURPLE + "Any Spreader");
		list.add(EnumChatFormatting.GRAY + "Turns Into: " + EnumChatFormatting.DARK_GRAY + "Antispreader");
		list.add(EnumChatFormatting.GRAY + "Radius: " + EnumChatFormatting.DARK_AQUA + power + ".0 blocks");
	}

}
