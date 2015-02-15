package yam.items;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class ItemHeartContainer extends ItemHeart {

	private float heal;
	private final static double max = 40.0D;
	
	public ItemHeartContainer(String texture, float heal) {
		super(texture, heal);
		this.heal = heal;
	}
	
	public void onHeartUse(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		IAttributeInstance a = getMaxHealth(par3EntityPlayer);
		if (a.getBaseValue() < max) {
			a.setBaseValue(a.getBaseValue() + heal);
			par2World.playSoundAtEntity(par3EntityPlayer, YetAnotherMod.MODID + ":items.heart", 1.0F, 1.0F);
		}
	}
	
	private IAttributeInstance getMaxHealth(EntityPlayer par3EntityPlayer) {
		return par3EntityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		IAttributeInstance a = getMaxHealth(par3EntityPlayer);
		if (a.getBaseValue() < max) {
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		return par1ItemStack;
    }

}
