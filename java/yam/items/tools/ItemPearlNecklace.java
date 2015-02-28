package yam.items.tools;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import yam.CustomDamage;
import yam.YetAnotherMod;

public class ItemPearlNecklace extends ItemCosmeticArmor {

	public ItemPearlNecklace(int id) {
		super(YetAnotherMod.amCosmetic, "pearl", id, 0, false);
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Breathe Air: " + EnumChatFormatting.RED + "No");
		list.add("Breathe Water: " + EnumChatFormatting.GREEN + "Yes");
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		player.setAir(0);
		if (!player.isInsideOfMaterial(Material.water)) {
			player.attackEntityFrom(CustomDamage.breath, 1.0F);
		}
	}

}
