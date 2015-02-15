package yam.items.tools;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.CustomDamage;

public class ItemGlasses extends ItemGlassesCosmetic {

	private String sees;

	public ItemGlasses(String texture, String sees, int id) {
		super(texture, id);
		this.sees = sees;
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Sees: \247" + sees);
	}

}
