package yam.items;

import java.util.List;

import yam.YetAnotherMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemGeneric extends Item {

	private String extraInfo;

	public ItemGeneric(String texture) {
		super();

		this.setTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setCreativeTab(YetAnotherMod.global);
	}
	
	public void setExtraInformation(String line) {
		this.extraInfo = line;
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (extraInfo == null) {return;}
		list.add(extraInfo);
		if (this == YetAnotherMod.processor) {list.add(EnumChatFormatting.GRAY + "Heat: " + EnumChatFormatting.RED + "300");}
		if (this == YetAnotherMod.graphicscard) {list.add(EnumChatFormatting.GRAY + "Heat: " + EnumChatFormatting.RED + "250");}
		if (this == YetAnotherMod.memory) {list.add(EnumChatFormatting.GRAY + "Heat: " + EnumChatFormatting.RED + "50");}
	}
	
}
