package yam.items;

import yam.YetAnotherMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHardDrive extends ItemGeneric {

	public ItemHardDrive(String texture, int damage) {
		super(texture);
        this.setMaxStackSize(1);
        this.setMaxDamage(65536);
	}
	
	/*public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (tileEntity == null || player.isSneaking()) {return false;}
	    par3EntityPlayer.openGui(YetAnotherMod.instance, 3, world, x, y, z);
        return par1ItemStack;
    }*/

}
