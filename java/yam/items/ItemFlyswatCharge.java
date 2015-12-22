package yam.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import yam.entity.EntityFly;

public class ItemFlyswatCharge extends ItemFlyswat {

	private int chargeTime;

	public ItemFlyswatCharge(String texture, int charges, int chargeTime) {
		super(texture);
		this.chargeTime = chargeTime;
		this.setMaxDamage(charges*chargeTime);
		this.setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote) {
			boolean flag = false;
			AxisAlignedBB aabb = par3EntityPlayer.boundingBox;
			if (aabb != null) {
				List entities = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, aabb.expand(8.0D, 8.0D, 8.0D));
				for (Object e : entities) {
					if (e instanceof EntityFly) {
						if (par1ItemStack.getItemDamage() >= par1ItemStack.getMaxDamage() - chargeTime) {break;}
						flag = true;
				        par1ItemStack.damageItem(chargeTime, par3EntityPlayer);
						((EntityFly)e).attackEntityFrom(DamageSource.causePlayerDamage(par3EntityPlayer), 10.0F);
					}
				}
			}
			if (flag) {
		        par2World.playSoundAtEntity(par3EntityPlayer, "yam:items.blaster", 0.5F, (par3EntityPlayer.getRNG().nextFloat()*0.2F)+0.9F);
			}
		}

		return par1ItemStack;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Targets: " + EnumChatFormatting.WHITE + "Flies");
		list.add(EnumChatFormatting.GRAY + "Effect: " + EnumChatFormatting.RED + "Kill");
		list.add(EnumChatFormatting.GRAY + "Radius: " + EnumChatFormatting.DARK_AQUA + "6.0 blocks");
		list.add(" ");
		list.add(EnumChatFormatting.GRAY + "Charge: " + EnumChatFormatting.DARK_PURPLE + (double)(this.getMaxDamage()-this.getDamage(itemstack)) + " charge");
		list.add(EnumChatFormatting.GRAY + "Max Charge: " + EnumChatFormatting.DARK_PURPLE + (double)(this.getMaxDamage()) + " charge");
		list.add(EnumChatFormatting.GRAY + "Cost per Fly: " + EnumChatFormatting.DARK_PURPLE + (double)(chargeTime) + " charge");
	}
	
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par1ItemStack.getItemDamage() > 0) {par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);}
	}
	
}
