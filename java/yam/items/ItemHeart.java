package yam.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class ItemHeart extends ItemGeneric {

	private float heal;
	
	public ItemHeart(String texture, float heal) {
		super("hearts/" + texture);
		this.heal = heal;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		 par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		 return par1ItemStack;
    }
	
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par1ItemStack.stackSize -= 1;
		this.onHeartUse(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }
	
	public void onHeartUse(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setHealth(Math.min(par3EntityPlayer.getHealth()+heal, par3EntityPlayer.getMaxHealth()));
		par2World.playSoundAtEntity(par3EntityPlayer, YetAnotherMod.MODID + ":items.heart", 1.0F, 1.0F);
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 20;
    }
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }

}
