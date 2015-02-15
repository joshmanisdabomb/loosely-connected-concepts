package yam.items;

import java.util.List;

import yam.YetAnotherMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGeneric extends Item {

	public ItemGeneric(String texture) {
		super();

		this.setTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setCreativeTab(YetAnotherMod.global);
	}
	
}
