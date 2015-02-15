package yam.items.tools;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.CustomDamage;
import yam.YetAnotherMod;

public class ItemFallingBoots extends ItemCustomArmor {
	
	public ItemFallingBoots(ArmorMaterial am, double d, String string, int id) {
		super(am, d, string, id, 3, false);
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Negates Fall Damage: \247aYes");
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		player.fallDistance = 0.0F;
	}
	
}
