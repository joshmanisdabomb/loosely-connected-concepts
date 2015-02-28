package yam.blocks.itemblocks;

import java.util.List;

import yam.blocks.BlockBouncepad;
import yam.blocks.BlockGeneric;
import yam.blocks.BlockPlayerPlate;
import yam.blocks.BlockSpreading;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockExtraInfo extends ItemBlock {
	
	public Block block;
	
	public ItemBlockExtraInfo(Block block) {
		super(block);
		this.block = block;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (block instanceof BlockGeneric) {
			((BlockGeneric)block).getExtraInformation(itemstack, player, list, par4);
		} else if (block instanceof BlockPlayerPlate) {
			list.add(EnumChatFormatting.GRAY + "Activated by Items: " + EnumChatFormatting.RED + "No");
			list.add(EnumChatFormatting.GRAY + "Activated by Mobs: " + EnumChatFormatting.RED + "No");
			list.add(EnumChatFormatting.GRAY + "Activated by Players: " + EnumChatFormatting.GREEN + "Yes");
		}
	}

}
