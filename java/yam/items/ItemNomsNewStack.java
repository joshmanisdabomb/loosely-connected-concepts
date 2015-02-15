package yam.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNomsNewStack extends ItemNoms {

	private Item newStack;

	public ItemNomsNewStack(String string, Item newStack, int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(string, p_i45339_1_, p_i45339_2_, p_i45339_3_);
		this.newStack = newStack;
	}
	
	public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		ItemStack stack = new ItemStack(this.newStack, 1);
		if (!par2World.isRemote) { 
			if (!par3EntityPlayer.inventory.addItemStackToInventory(stack)) {
	            par3EntityPlayer.dropPlayerItemWithRandomChoice(stack, false);
	        }
		}
    }

}
